package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.SorteoServiceImpl;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EquipoInicialControlador {

        private final SorteoServiceImpl sorteoService;

        public EquipoInicialControlador() {
            JugadorLoader jugadorLoader = new JugadorLoader();
            this.sorteoService = new SorteoServiceImpl(jugadorLoader);
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

            equipo.setJugadores(this.sorteoService.sortearEquipoInicial());

            session.setAttribute("equipo", equipo);

            ModelAndView mav = new ModelAndView("sorteoEquipo");
            mav.addObject("equipo", equipo);
            return mav;
        }


    }
