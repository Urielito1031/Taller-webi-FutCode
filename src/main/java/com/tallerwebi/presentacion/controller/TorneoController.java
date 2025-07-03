package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TorneoController {

   private final TorneoService torneoService;
   private final EquipoTorneoService equipoTorneoService;
   private final UsuarioService usuarioService;

   @Autowired
   public TorneoController(TorneoService torneoService, EquipoTorneoService equipoTorneoService, UsuarioService usuarioService) {
      this.torneoService = torneoService;
      this.equipoTorneoService = equipoTorneoService;
      this.usuarioService = usuarioService;
   }

   @GetMapping(path = "/home")
   public String irAHome(Model model) {
      List<TorneoDTO> torneos = torneoService.getAll();
      if (torneos.isEmpty()) {
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }
      model.addAttribute("torneos", torneos);
      return "home";
   }

   @GetMapping("/detalle-torneo/{id}")
   public String detalleTorneo(@PathVariable Long id, Model model) {
      TorneoDTO torneo = torneoService.getById(id);
      List<EquipoTorneoDTO> torneoEquipos = equipoTorneoService.getAllByTorneoId(id);
      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", torneoEquipos);
      return "detalle-torneo";
   }

   @PostMapping("/torneo/{id}/unirse")
   public String unirseATorneo(@PathVariable("id") Long torneoId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         redirectAttributes.addFlashAttribute("errorUnirse", "No estás autenticado. Por favor, inicia sesión.");
         return "redirect:/detalle-torneo/" + torneoId;
      }

      try {
         Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
         if (usuario == null) {
            redirectAttributes.addFlashAttribute("errorUnirse", "Usuario no encontrado.");
            return "redirect:/detalle-torneo/" + torneoId;
         }

         Long equipoId = usuario.getEquipo() != null ? usuario.getEquipo().getId() : null;
         if (equipoId == null) {
            redirectAttributes.addFlashAttribute("errorUnirse", "No tienes un equipo asignado. Crea un equipo primero.");
            return "redirect:/nuevo-equipo";
         }

         equipoTorneoService.unirseTorneo(torneoId, equipoId);
         redirectAttributes.addFlashAttribute("mensajeTorneo", "¡Te uniste al torneo con éxito!");
      } catch (IllegalArgumentException e) {
         redirectAttributes.addFlashAttribute("errorUnirse", e.getMessage());
      }
      return "redirect:/detalle-torneo/" + torneoId;
   }
}