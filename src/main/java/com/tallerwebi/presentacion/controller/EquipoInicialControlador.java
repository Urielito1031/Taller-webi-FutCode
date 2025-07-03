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

import org.springframework.web.bind.annotation.GetMapping;
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

            this.jugadorService.cargarJugadoresAlEquipo(equipo);

            session.setAttribute("equipo", equipo);

            Long usuarioId = (Long) session.getAttribute("USUARIO_ID");
            if (usuarioId == null) {
                throw new IllegalStateException("No se encontró el usuarioId en la sesión.");
            }
            Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
            if (usuario == null) {
                throw new IllegalStateException("No se encontró el Usuario con ID: " + usuarioId);
            }

            equipo.setUsuarioId(usuario.getId());
            this.equipoService.saveBoth(equipo, usuario);

            ModelAndView mav = new ModelAndView("sorteoEquipo");
            mav.addObject("equipo", equipo);
            mav.addObject("nombreEquipo", equipo.getNombre());
            return mav;
        }

        @GetMapping("/mi-equipo")
        public ModelAndView mostrarEquipo(HttpSession session) {
            Long usuarioId = (Long) session.getAttribute("USUARIO_ID");

            if (usuarioId == null) {
                return new ModelAndView("redirect:/login");
            }

            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
            if (usuario == null || usuario.getEquipo() == null) {
                return new ModelAndView("redirect:/nuevo-equipo");
            }

            Equipo equipo = usuario.getEquipo();
            EquipoDTO equipoDTO = new EquipoDTO();
            equipoDTO.convertFromEntity(equipo);

            ModelAndView mav = new ModelAndView("miEquipoVista");
            mav.addObject("equipo", equipoDTO);
            return mav;
        }
}
