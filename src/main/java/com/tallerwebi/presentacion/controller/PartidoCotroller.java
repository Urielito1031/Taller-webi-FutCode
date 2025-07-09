package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.service.PartidoService;
import com.tallerwebi.presentacion.dto.PartidoDTO;
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


   @Autowired
   public PartidoCotroller(PartidoService partidoService) {
      this.partidoService = partidoService;
   }

   @GetMapping(path = "/tu-historial")
   public String historialPartidosJugados(Model model,HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }

      List<PartidoDTO> historialPartidosJugados = partidoService.getHistorialPartidosPorUsuario(usuarioId);
      return null;
   }
}
