package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.EquipoService;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

    @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.POST)
    public String procesarNuevoEquipo(@Valid @ModelAttribute("equipo") EquipoDTO equipo,
                                      HttpSession session,
                                      HttpServletRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "creacionEquipo";
        }
        ModelAndView mav = new ModelAndView("home");

        mav.addObject("nombreEquipo", equipo.getNombre());
        mav.addObject("equipo", equipo);

        Long id = (Long) request.getSession().getAttribute("USUARIO_ID");
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
        mav.addObject("monedas", usuario.getMonedas());

        session.setAttribute("equipo", equipo);
        session.setAttribute("MONEDAS", usuario.getMonedas());

        return "redirect:/sorteoEquipoInicial";
    }


    @RequestMapping(path = "/sorteoEquipoInicial", method = RequestMethod.GET)
    public ModelAndView sorteEquipoInicial(HttpSession session) {

        EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipo");

        if (equipo == null) {
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

        session.setAttribute("MONEDAS", usuario.getMonedas());

        ModelAndView mav = new ModelAndView("sorteoEquipo");
        mav.addObject("equipo", equipo);
        mav.addObject("nombreEquipo", equipo.getNombre());
        mav.addObject("monedas", usuario.getMonedas());
        return mav;
    }
}
