package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.PartidoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.PartidoHistorialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/partido")
public class PartidoCotroller{

   private final PartidoService partidoService;
   private final UsuarioService usuarioService;


   @Autowired
   public PartidoCotroller(PartidoService partidoService,UsuarioService usuarioService) {
      this.partidoService = partidoService;
      this.usuarioService = usuarioService;
   }

   @GetMapping(path = "/mi-historial")
   public String historialPartidosJugados(Model model, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");

      if (usuarioId == null) {
         return "redirect:/login";
      }

      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null) {
         return "redirect:/login";
      }

      Long idEquipo = usuario.getEquipo().getId();

      List<PartidoHistorialDTO> historialPartidosJugados = partidoService.obtenerPartidosJugadosPorEquipoId(idEquipo);

      if (historialPartidosJugados.isEmpty()) {
         model.addAttribute("mensaje", "Todavía no jugaste ningún partido.");
      } else {
         model.addAttribute("historialPartidosJugados", historialPartidosJugados);
      }

      return "vista-historial-partidos";
   }
}
