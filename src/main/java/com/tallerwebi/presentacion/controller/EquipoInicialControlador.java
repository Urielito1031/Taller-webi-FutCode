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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EquipoInicialControlador {

    private final JugadorService jugadorService;
    private final UsuarioService usuarioService;
    private final EquipoService equipoService;

    public EquipoInicialControlador(JugadorService jugadorService, UsuarioService usuarioService,
            EquipoService equipoService) {
        this.jugadorService = jugadorService;
        this.usuarioService = usuarioService;
        this.equipoService = equipoService;
    }

    @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.GET)
    public ModelAndView nuevoEquipo() {
        return new ModelAndView("creacionEquipo").addObject("equipo", new EquipoDTO());
    }

    // @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.POST)
    // public String procesarNuevoEquipo(@ModelAttribute("equipo") EquipoDTO equipo,
    // HttpSession session) {
    // ModelAndView mav = new ModelAndView("home");
    // mav.addObject("nombreEquipo", equipo.getNombre());
    // mav.addObject("equipo", equipo);
    //
    // session.setAttribute("equipo", equipo);
    //
    // return "redirect:/sorteoEquipoInicial";
    // }

    @RequestMapping(path = "/nuevo-equipo", method = RequestMethod.POST)
    public String procesarNuevoEquipo(@Valid @ModelAttribute("equipo") EquipoDTO equipo,
            BindingResult result, HttpSession session,
            HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            return "creacionEquipo";
        }

        Long id = (Long) request.getSession().getAttribute("USUARIO_ID");
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);

        // Guardar el equipo en sesión para continuar con la selección de escudo
        session.setAttribute("equipoEnCreacion", equipo);
        session.setAttribute("MONEDAS", usuario.getMonedas());

        return "redirect:/seleccionar-escudo";
    }

    @RequestMapping(path = "/sorteoEquipoInicial", method = RequestMethod.GET)
    public ModelAndView sorteEquipoInicial(HttpSession session) {

        EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipo");

        if (equipo == null) {
            return new ModelAndView("redirect:/nuevo-equipo");
        }

        // Validar el equipo antes de procesar
        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty()) {
            ModelAndView mav = new ModelAndView("redirect:/nuevo-equipo");
            mav.addObject("error", "El nombre del equipo no puede estar vacío");
            return mav;
        }

        Long usuarioId = (Long) session.getAttribute("USUARIO_ID");
        if (usuarioId == null) {
            throw new IllegalStateException("No se encontró el usuarioId en la sesión.");
        }
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            throw new IllegalStateException("No se encontró el Usuario con ID: " + usuarioId);
        }

        try {
            // Los jugadores ya están cargados en el equipo guardado
            // Solo mostrar el sorteo
            session.setAttribute("equipo", equipo);
            session.setAttribute("MONEDAS", usuario.getMonedas());

            ModelAndView mav = new ModelAndView("sorteoEquipo");
            mav.addObject("equipo", equipo);
            mav.addObject("nombreEquipo", equipo.getNombre());
            mav.addObject("monedas", usuario.getMonedas());
            return mav;
        } catch (IllegalArgumentException e) {
            ModelAndView mav = new ModelAndView("redirect:/nuevo-equipo");
            mav.addObject("error", e.getMessage());
            return mav;
        }
    }

    @GetMapping("/seleccionar-escudo")
    public String mostrarSeleccionEscudo(Model model, HttpSession session) {
        EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipoEnCreacion");
        if (equipo == null) {
            return "redirect:/nuevo-equipo";
        }

        model.addAttribute("equipo", equipo);
        return "seleccionar-escudo";
    }

    @PostMapping("/guardar-escudo")
    public String guardarEscudo(@RequestParam String escudoSeleccionado,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            EquipoDTO equipo = (EquipoDTO) session.getAttribute("equipoEnCreacion");
            if (equipo == null) {
                redirectAttributes.addFlashAttribute("error", "No hay un equipo en creación");
                return "redirect:/nuevo-equipo";
            }

            // Guardar el escudo seleccionado en el equipo
            equipo.setImagen(escudoSeleccionado);

            // Cargar jugadores ANTES de guardar el equipo
            this.jugadorService.cargarJugadoresAlEquipo(equipo);

            // Obtener el usuario de la sesión
            Long usuarioId = (Long) session.getAttribute("USUARIO_ID");
            if (usuarioId == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario no autenticado");
                return "redirect:/login";
            }
            Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);

            equipoService.saveBoth(equipo, usuario);

            // Continuar con el flujo de sorteo de jugadores
            session.setAttribute("equipo", equipo);

            redirectAttributes.addFlashAttribute("mensaje", "Escudo seleccionado exitosamente");
            return "redirect:/sorteoEquipoInicial";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el escudo: " + e.getMessage());
            return "redirect:/nuevo-equipo";
        }
    }
}
