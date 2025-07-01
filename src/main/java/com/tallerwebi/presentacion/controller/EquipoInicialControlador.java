package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.EquipoService;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EquipoInicialControlador {

        private final JugadorService jugadorService;
        private final UsuarioService usuarioService;
        private final EquipoService equipoService;

        public EquipoInicialControlador(JugadorService jugadorService, UsuarioService usuarioService, EquipoService equipoService) {
            this.jugadorService = jugadorService;
            this.usuarioService = usuarioService;
            this.equipoService = equipoService;
        }

        @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.GET)
        public ModelAndView nuevoEquipo() {
            return new ModelAndView("creacionEquipo").addObject("equipo", new EquipoDTO());
        }

        @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.POST)
        public String procesarNuevoEquipo(@ModelAttribute("equipo") EquipoDTO equipo, HttpSession session) {
            ModelAndView mav = new ModelAndView("home");
            mav.addObject("nombreEquipo", equipo.getNombre());
            mav.addObject("equipo", equipo);

            session.setAttribute("equipo", equipo);

            return "redirect:/sorteoEquipoInicial";
        }

        @RequestMapping(path = "/sorteoEquipoInicial", method = RequestMethod.GET)
        public ModelAndView sorteEquipoInicial(HttpSession session) {

            EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipo");

            if(equipo == null){
                return new ModelAndView("redirect:/nuevo-equipo");
            }

            List<Jugador> jugadores = this.jugadorService.sortearJugadoresIniciales(14);

            List<JugadorDTO> jugadoresDto = new ArrayList<>();

            for(Jugador jugador : jugadores){
                jugadoresDto.add(jugador.convertToDTO());
            }

            for(JugadorDTO jugadorDTO : jugadoresDto){
                jugadorDTO.setEquipo(equipo);
            }

            equipo.setJugadores(jugadoresDto);

            session.setAttribute("equipo", equipo);

            Long usuarioId = (Long) session.getAttribute("usuarioId");
            Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);

            Equipo equipoEntity = new Equipo();
            equipoEntity.setNombre(equipo.getNombre());
            equipoEntity.setUsuario(usuario);
            equipoEntity.setJugadores(jugadores);

            for (Jugador jugador : jugadores) {
                jugador.setEquipo(equipoEntity); // ← esto es clave para que persista la relación
            }


            this.equipoService.saveEntity(equipoEntity);

            ModelAndView mav = new ModelAndView("sorteoEquipo");
            mav.addObject("equipo", equipo);
            mav.addObject("nombreEquipo", equipo.getNombre());
            return mav;
        }




    }
