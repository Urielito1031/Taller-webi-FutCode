package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Esquema;
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

        public EquipoInicialControlador(JugadorService jugadorService,UsuarioService usuarioService,EquipoService equipoService) {
            this.jugadorService = jugadorService;
            this.usuarioService = usuarioService;
            this.equipoService = equipoService;
        }

        @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.GET)
        public ModelAndView nuevoEquipo(HttpSession session){
           Long usuarioId = (Long) session.getAttribute("USUARIO_ID");
           if(usuarioId == null){
              System.out.println("nuevoEquipo: No se encontró el ID de usuario en la sesión, redirigiendo a /login");
              return new ModelAndView("redirect:/login");
           }
           return new ModelAndView("creacionEquipo").addObject("equipo",new EquipoDTO());
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
         System.out.println("sorteoEquipoInicial: No se encontró el equipo en la sesión.");
         return new ModelAndView("redirect:/nuevo-equipo");
      }
      System.out.println("sorteoEquipoInicial: Equipo encontrado en la sesión: " + equipo.getNombre());

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

      Long usuarioId = (Long) session.getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         System.out.println("sorteoEquipoInicial: No se encontró el ID de usuario en la sesión.");
         return new ModelAndView("redirect:/login");
      }
      System.out.println("sorteoEquipoInicial: ID de usuario encontrado en la sesión: " + usuarioId);
      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null) {
         return new ModelAndView("redirect:/login");
      }

      Equipo equipoEntity = Equipo.convertToEntity(equipo);
      equipoEntity.setUsuario(usuario);
      equipoEntity.setJugadores(jugadores);

      Esquema esquema = new Esquema();
      esquema.setId(1L);
      equipoEntity.setEsquema(esquema);

      // Asociar jugadores al equipo
      for (Jugador jugador : jugadores) {
         jugador.setEquipo(equipoEntity);
      }

      System.out.println("EquipoEntity antes de guardar, controlador: " + equipoEntity);


      equipo.setUsuarioId(usuarioId);
      this.equipoService.save(equipo);
      System.out.println("Equipo guardado con ID: " + equipo.getId());

      usuario.setEquipo(equipoEntity);
      this.usuarioService.modificar(usuario);
      System.out.println("Usuario actualizado con equipo ID: " + (usuario.getEquipo() != null ? usuario.getEquipo().getId() : "null"));

      ModelAndView mav = new ModelAndView("sorteoEquipo");
      mav.addObject("equipo", equipo);
      mav.addObject("nombreEquipo", equipo.getNombre());
      return mav;
   }



    }
