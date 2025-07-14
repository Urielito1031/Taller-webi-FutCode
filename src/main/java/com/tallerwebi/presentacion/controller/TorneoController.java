package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.SimularTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.service.FrasePartidoService;
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
import com.tallerwebi.dominio.service.UsuarioServiceImpl;

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
   private final FrasePartidoService frasePartidoService;

   @Autowired
   public TorneoController(TorneoService torneoService, EquipoTorneoService equipoTorneoService,
         UsuarioService usuarioService,
         SimularTorneoService simularTorneoService, TorneoRepository torneoRepository,
         EquipoTorneoRepository equipoTorneoRepository, PartidoRepository partidoRepository,
         FechaRepository fechaRepository, NarracionRepository narracionRepository,
         FrasePartidoService frasePartidoService) {
      this.torneoService = torneoService;
      this.equipoTorneoService = equipoTorneoService;
      this.usuarioService = usuarioService;
      this.simularTorneoService = simularTorneoService;
      this.torneoRepository = torneoRepository;
      this.equipoTorneoRepository = equipoTorneoRepository;
      this.partidoRepository = partidoRepository;
      this.fechaRepository = fechaRepository;
      this.narracionRepository = narracionRepository;
      this.frasePartidoService = frasePartidoService;
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

         if (torneosUnidos.isEmpty()) {
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

      // Usar la misma lógica que tabla-posiciones para obtener datos actualizados
      Torneo torneoEntity = torneoRepository.obtenerTorneoConFechas(id);
      List<Partido> partidos = torneoEntity.getFechas().stream()
            .flatMap(f -> f.getPartidos().stream())
            .collect(Collectors.toList());

      List<EquipoTorneo> tablaAnterior = equipoTorneoRepository.getAllByTorneoId(id);

      // Agrego este metodo porque me compara con la misma tabla
      for (EquipoTorneo eq : tablaAnterior) {
         eq.setPosicionAnterior(eq.getPosicion());
      }

      List<EquipoTorneo> tabla = torneoService.calcularTablaDePosiciones(partidos, tablaAnterior);

      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      model.addAttribute("usuarioId", usuarioId);

      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", tabla);
      return "detalle-torneo";
   }

   @PostMapping("/{id}/unirse")
   public String unirseATorneo(@PathVariable("id") Long torneoId, RedirectAttributes redirectAttributes,
         HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         redirectAttributes.addFlashAttribute("errorUnirse", "No estás autenticado. Por favor, inicia sesión.");
         return "redirect:/torneo/detalle-torneo/" + torneoId;
      }

      try {
         Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);

         if (usuario == null) {
            redirectAttributes.addFlashAttribute("errorUnirse", "Usuario no encontrado.");
            return "redirect:/torneo/detalle-torneo/" + torneoId;
         }

         Long equipoId = usuario.getEquipo() != null ? usuario.getEquipo().getId() : null;
         if (equipoId == null) {
            redirectAttributes.addFlashAttribute("errorUnirse",
                  "No tienes un equipo asignado. Crea un equipo primero.");
            return "redirect:/torneo/nuevo-equipo";
         }

         equipoTorneoService.unirseTorneo(torneoId, equipoId);

         Torneo torneo = torneoService.buscarPorId(torneoId);
         List<EquipoTorneo> equiposEnTorneo = equipoTorneoService.obtenerEquiposPorIdTorneo(torneoId);

         if (torneo.getEstado() == EstadoTorneoEnum.ABIERTO && !equiposEnTorneo.isEmpty()) {
            torneo.setEstado(EstadoTorneoEnum.EN_CURSO);
            torneoService.guardar(torneo);
         }

         redirectAttributes.addFlashAttribute("mensajeTorneo", "¡Te uniste al torneo con éxito!");

      } catch (IllegalArgumentException e) {
         redirectAttributes.addFlashAttribute("errorUnirse", e.getMessage());
      }
      return "redirect:/torneo/detalle-torneo/" + torneoId;
   }

   @GetMapping("/fechas")
   public ModelAndView mostrarFechas(@RequestParam Long torneoId, HttpServletRequest request) {
      Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);
      ModelAndView mav = new ModelAndView("simular-fechas");
      mav.addObject("fechas", torneo.getFechas());
      mav.addObject("torneoId", torneoId);

      // Obtener información del equipo del usuario
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      Long equipoUsuarioId = null;

      if (usuarioId != null) {
         Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
         if (usuario != null && usuario.getEquipo() != null) {
            equipoUsuarioId = usuario.getEquipo().getId();
         }
      }

      mav.addObject("equipoUsuarioId", equipoUsuarioId);

      return mav;
   }

   @PostMapping("/generar-fixture")
   public String generarFixture(@RequestParam Long torneoId) {
      this.torneoService.crearFixtureConLasFechas(torneoId);
      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

   @PostMapping("/simular-fecha")
   public String simularFecha(@RequestParam Long torneoId, @RequestParam Long numeroFecha, HttpServletRequest request) {
      // 1. Simular toda la fecha
      simularTorneoService.simularFecha(torneoId, numeroFecha);

      torneoService.verificarYFinalizarTorneo(torneoId);

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
      // Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId,
      // numeroFecha);

      Fecha fecha = simularTorneoService.obtenerFechaConPartidos(torneoId, numeroFecha);

      if (fecha != null && fecha.getPartidos() != null) {
         for (Partido partido : fecha.getPartidos()) {
            if (partido.getEquipoLocal().getId().equals(equipoId)
                  || partido.getEquipoVisitante().getId().equals(equipoId)) {
               // 4. Redirigir al simulador con ese partido
               return "redirect:/torneo/simular-partido?partidoId=" + partido.getId();
            }
         }
      }

      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

   @GetMapping("/simular-partido")
   public String mostrarSimuladorDePartido(@RequestParam Long partidoId, Model model, HttpServletRequest request) {
      Partido partido = simularTorneoService.obtenerPartidoSimulado(partidoId);
      if (partido == null) {
         return "redirect:/torneo/fechas"; // o página de error
      }

      // Obtener el equipo del usuario
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      String equipoUsuario = null;
      Long equipoUsuarioId = null;
      if (usuarioId != null) {
         Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
         if (usuario != null && usuario.getEquipo() != null) {
            equipoUsuario = usuario.getEquipo().getNombre();
            equipoUsuarioId = usuario.getEquipo().getId();
         }
      }

      model.addAttribute("equipoLocal", partido.getEquipoLocal());
      model.addAttribute("equipoVisitante", partido.getEquipoVisitante());
      model.addAttribute("equipoUsuario", equipoUsuario); // Agregar el equipo del usuario
      model.addAttribute("golesLocal", partido.getGolesLocal());
      model.addAttribute("golesVisitante", partido.getGolesVisitante());

      String resultado;
      int monedasGanadas = 0;
      if (equipoUsuarioId != null) {
         if (partido.getEquipoLocal().getId().equals(equipoUsuarioId)) {
            if (partido.getGolesLocal() > partido.getGolesVisitante()) {
               resultado = "Ganaste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_VICTORIA;
            } else if (partido.getGolesLocal() < partido.getGolesVisitante()) {
               resultado = "Perdiste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_DERROTA;
            } else {
               resultado = "Empataste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_EMPATE;
            }
         } else if (partido.getEquipoVisitante().getId().equals(equipoUsuarioId)) {
            if (partido.getGolesVisitante() > partido.getGolesLocal()) {
               resultado = "Ganaste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_VICTORIA;
            } else if (partido.getGolesVisitante() < partido.getGolesLocal()) {
               resultado = "Perdiste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_DERROTA;
            } else {
               resultado = "Empataste";
               monedasGanadas = UsuarioServiceImpl.MONEDAS_EMPATE;
            }
         } else {
            resultado = "No jugaste este partido";
         }
      } else {
         resultado = "No jugaste este partido";
      }

      model.addAttribute("resultado", resultado);
      model.addAttribute("monedasGanadas", monedasGanadas);

      // Generar narraciones enriquecidas usando el servicio
      List<com.tallerwebi.presentacion.dto.NarracionDTO> narraciones = simularTorneoService
            .generarNarracionesParaPartido(partido);
      model.addAttribute("narraciones", narraciones);
      return "partido-Vista";
   }

   @GetMapping("/tabla-posiciones")
   public ModelAndView mostrarTabla(@RequestParam Long torneoId, HttpServletRequest request) {
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

      // Obtener información del usuario para resaltar su equipo
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");

      ModelAndView mav = new ModelAndView("tabla-posiciones");
      mav.addObject("tabla", tabla);
      mav.addObject("usuarioId", usuarioId);
      return mav;
   }

}