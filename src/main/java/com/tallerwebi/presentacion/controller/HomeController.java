package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ArrayList;

@Controller
public class HomeController {

   private final TorneoService torneoService;
   private final UsuarioService usuarioService;
   private final EquipoTorneoService equipoTorneoService;

   @Autowired
   public HomeController(TorneoService torneoService, UsuarioService usuarioService,
         EquipoTorneoService equipoTorneoService) {
      this.torneoService = torneoService;
      this.usuarioService = usuarioService;
      this.equipoTorneoService = equipoTorneoService;
   }

   @GetMapping(path = "/home")
   public String irAHome(Model model, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }

      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
      model.addAttribute("usuario", usuario);

      if (usuario.getEquipo() != null) {
         model.addAttribute("equipoNombre", usuario.getEquipo().getNombre());
      } else {
         model.addAttribute("equipoNombre", "Sin equipo");
      }

      // Obtener todos los torneos
      List<TorneoDTO> torneos = torneoService.getAll();
      if (torneos.isEmpty()) {
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }

      // Obtener torneos a los que está unido el usuario
      List<TorneoDTO> torneosUnidos = new ArrayList<>();
      if (usuario.getEquipo() != null) {
         for (TorneoDTO torneo : torneos) {
            List<EquipoTorneoDTO> equiposDelTorneo = this.equipoTorneoService.getAllByTorneoId(torneo.getId());

            for (EquipoTorneoDTO etdto : equiposDelTorneo) {
               EquipoDTO equipo = etdto.getEquipo();
               if (equipo != null && equipo.getUsuarioId() != null && equipo.getUsuarioId().equals(usuarioId)) {
                  torneosUnidos.add(torneo);
                  break;
               }
            }
         }
      }

      model.addAttribute("torneos", torneos);
      model.addAttribute("torneosUnidos", torneosUnidos);
      return "home";
   }

}