package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.SimularTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.infraestructura.repositoryImpl.EquipoTorneoRepositoryImpl;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/torneo")
public class TorneoController {

   private final TorneoService torneoService;
   private final EquipoTorneoService equipoTorneoService;
   private final UsuarioService usuarioService;
   private final SimularTorneoService simularTorneoService;
   private final TorneoRepository torneoRepository;
   private final EquipoTorneoRepository equipoTorneoRepository;
   private final NarracionRepository narracionRepository;
   private final PartidoRepository partidoRepository;
   private final FechaRepository fechaRepository;

   @Autowired
   public TorneoController(TorneoService torneoService, EquipoTorneoService equipoTorneoService, UsuarioService usuarioService,
                           SimularTorneoService simularTorneoService, TorneoRepository torneoRepository,
                           EquipoTorneoRepository equipoTorneoRepository, PartidoRepository partidoRepository, FechaRepository fechaRepository, NarracionRepository narracionRepository) {
      this.torneoService = torneoService;
      this.equipoTorneoService = equipoTorneoService;
      this.usuarioService = usuarioService;
      this.simularTorneoService = simularTorneoService;
      this.torneoRepository = torneoRepository;
      this.equipoTorneoRepository = equipoTorneoRepository;
      this.partidoRepository = partidoRepository;
      this.fechaRepository = fechaRepository;
      this.narracionRepository = narracionRepository;
   }


   @GetMapping(path = "/lista-torneos")
   public String verTorneos(Model model, HttpServletRequest request) {
   try {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");

      if (usuarioId == null) {
         return "redirect:/login";
      }

      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);

      if (usuario == null) {
         return "redirect:/login";
      }

      if (usuario.getEquipo() != null) {
         model.addAttribute("equipoNombre", usuario.getEquipo().getNombre());
      } else {
         model.addAttribute("equipoNombre", "Sin equipo");
      }


      List<TorneoDTO> torneos = torneoService.getAll();
      if (torneos.isEmpty()) {
         model.addAttribute("mensajeTorneo", "No hay torneos para mostrar");
      }

      List<TorneoDTO> torneosUnidos = new ArrayList<>();



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

      if(torneosUnidos.isEmpty()) {
         model.addAttribute("mensajeTorneo", "Usted no se ha unido a ningun torneo");
      }

      model.addAttribute("torneos", torneosUnidos);
      return "vista-mis-torneos";
   } catch (Exception e) {
      model.addAttribute("error", "Error al cargar torneos: " + e.getMessage());
      e.printStackTrace();
      return "error"; //
   }
   }

   @GetMapping("/detalle-torneo/{id}")
   public String detalleTorneo(@PathVariable Long id, Model model, HttpServletRequest request) {
      TorneoDTO torneo = torneoService.getById(id);
      List<EquipoTorneoDTO> torneoEquipos = equipoTorneoService.getAllByTorneoId(id);

      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      model.addAttribute("usuarioId", usuarioId);

      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", torneoEquipos);
      return "detalle-torneo";
   }

   @PostMapping("/{id}/unirse")
   public String unirseATorneo(@PathVariable("id") Long torneoId, RedirectAttributes redirectAttributes, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         redirectAttributes.addFlashAttribute("errorUnirse", "No estás autenticado. Por favor, inicia sesión.");
         return "redirect:/torneo/detalle-torneo/" + torneoId  ;
      }

      try {
         Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);

         if (usuario == null) {
            redirectAttributes.addFlashAttribute("errorUnirse", "Usuario no encontrado.");
            return "redirect:/torneo/detalle-torneo/" + torneoId;
         }

         Long equipoId = usuario.getEquipo() != null ? usuario.getEquipo().getId() : null;
         if (equipoId == null) {
            redirectAttributes.addFlashAttribute("errorUnirse", "No tienes un equipo asignado. Crea un equipo primero.");
            return "redirect:/torneo/nuevo-equipo";
         }

         equipoTorneoService.unirseTorneo(torneoId, equipoId);
         redirectAttributes.addFlashAttribute("mensajeTorneo", "¡Te uniste al torneo con éxito!");
      } catch (IllegalArgumentException e) {
         redirectAttributes.addFlashAttribute("errorUnirse", e.getMessage());
      }
      return "redirect:/torneo/detalle-torneo/" + torneoId;
   }


   @GetMapping("/fechas")
   public ModelAndView mostrarFechas(@RequestParam Long torneoId) {
      Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);
      ModelAndView mav = new ModelAndView("simular-fechas");
      mav.addObject("fechas", torneo.getFechas());
      mav.addObject("torneoId", torneoId);

      return mav;
   }

   @GetMapping("/generar-fixture")
   public String generarFixture(@RequestParam Long torneoId) {
      this.torneoService.crearFixtureConLasFechas(torneoId);
      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

   @PostMapping("/simular-fecha")
   public String simularFecha(@RequestParam Long torneoId, @RequestParam Long numeroFecha, HttpServletRequest request) {
      // 1. Simular toda la fecha
      simularTorneoService.simularFecha(torneoId, numeroFecha);

      // 2. Obtener usuario y su equipo
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         // No está logueado, redirigir
         return "redirect:/login";
      }

      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null || usuario.getEquipo() == null) {
         // No tiene equipo asignado, redirigir a donde corresponda
         return "redirect:/torneo/fechas?torneoId=" + torneoId;
      }

      Long equipoId = usuario.getEquipo().getId();

      // 3. Buscar el partido donde juega el equipo en esa fecha
//      Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroFecha);

         Fecha fecha = simularTorneoService.obtenerFechaConPartidos(torneoId, numeroFecha);

      if (fecha != null && fecha.getPartidos() != null) {
         for (Partido partido : fecha.getPartidos()) {
            if (partido.getEquipoLocal().getId().equals(equipoId) || partido.getEquipoVisitante().getId().equals(equipoId)) {
               // 4. Redirigir al simulador con ese partido
               return "redirect:/torneo/simular-partido?partidoId=" + partido.getId();
            }
         }
      }

      // Si no encontró partido del equipo, redirigir a fechas (o donde quieras)
      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

//
//    ESTO ANDA
//   @PostMapping("/simular-fecha")
//   public String simularFecha(@RequestParam Long torneoId, @RequestParam Long numeroFecha) {
//      simularTorneoService.simularFecha(torneoId, numeroFecha);
//
////      Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroFecha);
////      if (!fecha.getPartidos().isEmpty()) {
////         Long primerPartidoId = fecha.getPartidos().get(0).getId();
////         return "redirect:/torneo/simular-partido?partidoId=" + primerPartidoId;
////      }
//
//      Long partidoId = simularTorneoService.simularFechaYDevolverPrimerPartido(torneoId, numeroFecha);
//
//      if (partidoId != null) {
//         return "redirect:/torneo/simular-partido?partidoId=" + partidoId;
//      }
//
//      return "redirect:/torneo/fechas?torneoId=" + torneoId;
//   }

   @GetMapping("/simular-partido")
   public String mostrarSimuladorDePartido(@RequestParam Long partidoId, Model model) {
      Partido partido = simularTorneoService.obtenerPartidoSimulado(partidoId);
      if (partido == null) {
         return "redirect:/torneo/fechas"; // o página de error
      }

      model.addAttribute("equipoLocal", partido.getEquipoLocal().getNombre());
      model.addAttribute("equipoVisitante", partido.getEquipoVisitante().getNombre());
      model.addAttribute("golesLocal", partido.getGolesLocal());
      model.addAttribute("golesVisitante", partido.getGolesVisitante());

      String resultado;
      if (partido.getGolesLocal() > partido.getGolesVisitante()) {
         resultado = "Ganó " + partido.getEquipoLocal().getNombre();
      } else if (partido.getGolesVisitante() > partido.getGolesLocal()) {
         resultado = "Ganó " + partido.getEquipoVisitante().getNombre();
      } else {
         resultado = "Empate";
      }

      model.addAttribute("resultado", resultado);

      List<Narracion> narraciones = narracionRepository.obtenerPorPartidoId(partidoId);

      Collections.shuffle(narraciones);

      model.addAttribute("narraciones", narraciones);
      return "partido-Vista";
   }


   @GetMapping("/tabla-posiciones")
   public ModelAndView mostrarTabla(@RequestParam Long torneoId) {
      Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);
      List<Partido> partidos = torneo.getFechas().stream()
              .flatMap(f -> f.getPartidos().stream())
              .collect(Collectors.toList());

      List<EquipoTorneo> tablaAnterior = equipoTorneoRepository.getAllByTorneoId(torneoId);

      // Agrego este metodo porque me compara con la misma tabla
      for (EquipoTorneo eq : tablaAnterior) {
         eq.setPosicionAnterior(eq.getPosicion());
      }

      List<EquipoTorneo> tabla = torneoService.calcularTablaDePosiciones(partidos, tablaAnterior);

      ModelAndView mav = new ModelAndView("tabla-posiciones");
      mav.addObject("tabla", tabla);
      return mav;
   }

}