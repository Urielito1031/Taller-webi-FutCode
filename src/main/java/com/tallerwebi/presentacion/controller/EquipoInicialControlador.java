package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.EquipoService;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EquipoInicialControlador {

        private final JugadorService jugadorService;
        private final UsuarioService usuarioService;
        private final EquipoService equipoService;

        private final RepositorioUsuario repositorioUsuario;
        public EquipoInicialControlador(JugadorService jugadorService,UsuarioService usuarioService,EquipoService equipoService,RepositorioUsuario repositorioUsuario) {
            this.jugadorService = jugadorService;
            this.usuarioService = usuarioService;
            this.equipoService = equipoService;
           this.repositorioUsuario = repositorioUsuario;
        }

        @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.GET)
        public ModelAndView nuevoEquipo() {
            return new ModelAndView("creacionEquipo").addObject("equipo", new EquipoDTO());
        }

   @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.POST)
   public String procesarNuevoEquipo(@Valid @ModelAttribute("equipo") EquipoDTO equipo,
                                     HttpSession session,
                                     BindingResult result,
                                     Model model) {
      if (result.hasErrors()) {
         result.getAllErrors().forEach(System.out::println);
         model.addAttribute("errors", result.getAllErrors()); // Pasar errores al modelo
         return "creacionEquipo"; // Volver a la vista con los errores
      }
      ModelAndView mav = new ModelAndView("home");
      mav.addObject("nombreEquipo", equipo.getNombre());
      mav.addObject("equipo", equipo);
      session.setAttribute("equipo", equipo);
      return "redirect:/sorteoEquipoInicial";
   }
   @RequestMapping(path = "/sorteoEquipoInicial", method = RequestMethod.GET)
   public ModelAndView sorteEquipoInicial(HttpSession session) {
      EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipo");
      if (equipo == null) {
         return new ModelAndView("redirect:/nuevo-equipo");
      }

      List<Jugador> jugadores = this.jugadorService.sortearJugadoresIniciales(14);
      List<JugadorDTO> jugadoresDto = new ArrayList<>();
      for (Jugador jugador : jugadores) {
         jugadoresDto.add(jugador.convertToDTO());
      }
      for (JugadorDTO jugadorDTO : jugadoresDto) {
         jugadorDTO.setEquipo(equipo);
      }
      equipo.setJugadores(jugadoresDto);

      session.setAttribute("equipo", equipo);

      Long usuarioId = (Long) session.getAttribute("USUARIO_ID"); // Corrección de "usuarioId"
      if (usuarioId == null) {
         return new ModelAndView("redirect:/login");
      }
      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null) {
         return new ModelAndView("redirect:/login");
      }

      // Convertir EquipoDTO a Equipo entity con esquema
      Equipo equipoEntity = Equipo.convertToEntity(equipo);
      equipoEntity.setUsuario(usuario);
      equipoEntity.setJugadores(jugadores);
      for (Jugador jugador : jugadores) {
         jugador.setEquipo(equipoEntity);
      }

      try {
         this.equipoService.save (equipoEntity.convertToDTO()); // Persistir el equipo entity
      } catch (ConstraintViolationException e) {
         System.out.println("Error de restricción: " + e.getMessage());
         return new ModelAndView("redirect:/nuevo-equipo");
      }

      usuario.setEquipo(equipoEntity);
      this.repositorioUsuario.modificar(usuario); // Actualizar el usuario con el equipo

      ModelAndView mav = new ModelAndView("sorteoEquipo");
      mav.addObject("equipo", equipo);
      mav.addObject("nombreEquipo", equipo.getNombre());
      return mav;
   }



    }
