package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.PartidoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.PartidoHistorialDTO;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.dominio.service.JugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/partido")
public class PartidoCotroller {

   private final PartidoService partidoService;
   private final UsuarioService usuarioService;
   private final PlantillaService plantillaService;
   private final JugadorService jugadorService;

   @Autowired
   public PartidoCotroller(PartidoService partidoService, UsuarioService usuarioService,
         PlantillaService plantillaService, JugadorService jugadorService) {
      this.partidoService = partidoService;
      this.usuarioService = usuarioService;
      this.plantillaService = plantillaService;
      this.jugadorService = jugadorService;
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

   @GetMapping("/{partidoId}/rival/{equipoId}/formacion")
   public String verFormacionRival(@PathVariable Long partidoId, @PathVariable Long equipoId, Model model,
         HttpServletRequest request) {
      Partido partido = partidoService.obtenerPartidoConRelaciones(partidoId);
      if (partido == null || partido.getFecha() == null || partido.getFecha().getTorneo() == null) {
         model.addAttribute("error", "No se pudo encontrar el partido o el torneo asociado.");
         return "detalle-formacion-rival";
      }
      Torneo torneo = partido.getFecha().getTorneo();
      if (torneo.getFormatoTorneo() == null || torneo.getFormatoTorneo().getTipo() != TipoFormato.PARTIDO_UNICO) {
         model.addAttribute("error", "Solo puedes ver la formación del rival en partidos de tipo PARTIDO_UNICO.");
         return "detalle-formacion-rival";
      }
      EsquemaDTO formacionRival = plantillaService.getFormacionPorEquipoId(equipoId);
      model.addAttribute("formacion", formacionRival);
      model.addAttribute("jugadores", formacionRival.getAlineacion());
      model.addAttribute("torneoId", torneo.getId());

      // IDs para el chat
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      model.addAttribute("usuarioId", usuarioId);
      // Buscar el usuarioId del rival
      Long rivalId = null;
      if (partido.getEquipoLocal() != null && partido.getEquipoLocal().getId().equals(equipoId)) {
         rivalId = partido.getEquipoLocal().getUsuario() != null ? partido.getEquipoLocal().getUsuario().getId() : null;
      } else if (partido.getEquipoVisitante() != null && partido.getEquipoVisitante().getId().equals(equipoId)) {
         rivalId = partido.getEquipoVisitante().getUsuario() != null ? partido.getEquipoVisitante().getUsuario().getId()
               : null;
      }
      model.addAttribute("rivalId", rivalId);

      return "detalle-formacion-rival";
   }
}
