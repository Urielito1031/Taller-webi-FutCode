package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.dominio.service.EquipoTorneoService;
import com.tallerwebi.dominio.service.SimularTorneoService;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.service.FrasePartidoService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import com.tallerwebi.presentacion.dto.CrearTorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.presentacion.dto.SimulacionTorneoResumenDTO;
import javax.validation.Valid;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import java.util.HashMap;
import java.util.Map;
import com.tallerwebi.dominio.service.PartidoService;
import com.tallerwebi.presentacion.dto.HistorialTorneoDTO;

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
   private final PartidoService partidoService;

   @Autowired
   public TorneoController(TorneoService torneoService, EquipoTorneoService equipoTorneoService,
         UsuarioService usuarioService,
         SimularTorneoService simularTorneoService, TorneoRepository torneoRepository,
         EquipoTorneoRepository equipoTorneoRepository, PartidoRepository partidoRepository,
         FechaRepository fechaRepository, NarracionRepository narracionRepository,
         FrasePartidoService frasePartidoService,
         PartidoService partidoService) {
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
      this.partidoService = partidoService;
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

         // Obtener torneos de formato PARTIDO_UNICO
         List<TorneoDTO> torneosUnicos = torneoService.getAllByFormato(TipoFormato.PARTIDO_UNICO);
         model.addAttribute("torneosUnicos", torneosUnicos);

         model.addAttribute("torneos", torneosUnidos);
         model.addAttribute("cantidadSobres", this.usuarioService.obtenerSobresDelUsuario(usuarioId).size());
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

      // Convertir a DTO y setear partidoId
      List<EquipoTorneoDTO> torneoEquiposDTO = tabla.stream().map(et -> {
         EquipoTorneoDTO dto = et.convertToDTO();
         // Solo para PARTIDO_UNICO, buscar el partido correspondiente
         if (torneo.getFormatoTorneo() != null &&
               "PARTIDO_UNICO".equals(torneo.getFormatoTorneo().getTipo().name())) {
            Partido partido = partidos.stream()
                  .filter(p -> (p.getEquipoLocal() != null && p.getEquipoLocal().getId().equals(et.getEquipo().getId()))
                        || (p.getEquipoVisitante() != null
                              && p.getEquipoVisitante().getId().equals(et.getEquipo().getId())))
                  .findFirst().orElse(null);
            if (partido != null) {
               dto.setPartidoId(partido.getId());
            }
         }
         return dto;
      }).collect(Collectors.toList());

      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      model.addAttribute("usuarioId", usuarioId != null ? usuarioId : -1L);

      boolean usuarioYaUnido = false;
      if (usuarioId != null && usuarioId != -1L) {
         usuarioYaUnido = tabla.stream()
               .anyMatch(eq -> eq.getEquipo() != null && eq.getEquipo().getUsuario() != null
                     && eq.getEquipo().getUsuario().getId().equals(usuarioId));
      }
      model.addAttribute("usuarioYaUnido", usuarioYaUnido);

      model.addAttribute("torneo", torneo);
      model.addAttribute("torneoEquipos", torneoEquiposDTO);
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
      mav.addObject("torneo", torneo); // <-- Agregar el objeto torneo al modelo

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

      // Nueva lógica: ¿hay fechas no simuladas?
      boolean hayFechasNoSimuladas = torneo.getFechas().stream().anyMatch(f -> !f.isSimulada());
      mav.addObject("hayFechasNoSimuladas", hayFechasNoSimuladas);

      // Verificar si hay resumen en session
      SimulacionTorneoResumenDTO resumenTorneo = (SimulacionTorneoResumenDTO) request.getSession()
            .getAttribute("resumenTorneo");
      if (resumenTorneo != null) {
         mav.addObject("resumenTorneo", resumenTorneo);
         // Limpiar el resumen de la session para que no aparezca de nuevo
         request.getSession().removeAttribute("resumenTorneo");
      }

      return mav;
   }

   @PostMapping("/generar-fixture")
   public String generarFixture(@RequestParam Long torneoId) {
      this.torneoService.crearFixtureConLasFechas(torneoId);
      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

   @PostMapping("/simular-fecha")
   public String simularFecha(@RequestParam Long torneoId, @RequestParam Long numeroFecha, HttpServletRequest request) {

      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }

      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null || usuario.getEquipo() == null) {
         // No tiene equipo asignado, redirigir a donde corresponda
         return "redirect:/torneo/fechas?torneoId=" + torneoId;
      }

      // 1. Simular toda la fecha
      simularTorneoService.simularFecha(torneoId, numeroFecha);

      Long equipoId = usuario.getEquipo().getId();

      // Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroFecha);

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

   @PostMapping("/simular-torneo")
   public String simularTorneoCompleto(@RequestParam Long torneoId, HttpServletRequest request,
         RedirectAttributes redirectAttributes) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }
      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null || usuario.getEquipo() == null) {
         return "redirect:/torneo/fechas?torneoId=" + torneoId;
      }
      if (usuario.getMonedas() == null || usuario.getMonedas() < 12000) {
         redirectAttributes.addFlashAttribute("mensajeError",
               "No tienes suficientes monedas para simular el torneo completo (12.000 requeridas).");
         return "redirect:/torneo/fechas?torneoId=" + torneoId;
      }
      // Descontar monedas
      usuario.setMonedas(usuario.getMonedas() - 12000);
      usuarioService.actualizar(usuario);
      // Simular todas las fechas pendientes (rápido, sin narraciones) y obtener
      // resumen
      SimulacionTorneoResumenDTO resumen = simularTorneoService.simularTorneoRapido(torneoId, usuarioId);

      // Guardar resumen en session
      request.getSession().setAttribute("resumenTorneo", resumen);
      redirectAttributes.addFlashAttribute("mensajeTorneo", "¡El torneo fue simulado completamente!");
      return "redirect:/torneo/fechas?torneoId=" + torneoId;
   }

   @GetMapping("/simular-partido")
   public String mostrarSimuladorDePartido(@RequestParam Long partidoId, Model model, HttpServletRequest request) {
      Partido partido = simularTorneoService.obtenerPartidoSimulado(partidoId);
      if (partido == null) {
         return "redirect:/torneo/fechas"; // o página de error
      }

      // Obtener el torneo directamente del partido (ya cargado con relaciones)
      Torneo torneo = partido.getFecha().getTorneo();
      boolean esPartidoUnico = torneo.getFormatoTorneo().getTipo().name().equals("PARTIDO_UNICO");
      Double premioTorneo = torneo.getPremioMonedas();

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
      model.addAttribute("esPartidoUnico", esPartidoUnico);
      model.addAttribute("premioTorneo", premioTorneo);
      model.addAttribute("nombreTorneo", torneo.getNombre());

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

   @GetMapping("/crear-personalizado")
   public String mostrarFormularioCrearTorneo(Model model) {
      model.addAttribute("crearTorneoDTO", new CrearTorneoDTO());
      return "crear-torneo";
   }

   @PostMapping("/crear-personalizado")
   public String procesarFormularioCrearTorneo(@Valid @ModelAttribute("crearTorneoDTO") CrearTorneoDTO crearTorneoDTO,
         BindingResult result,
         Model model,
         RedirectAttributes redirectAttributes) {
      if (result.hasErrors()) {
         return "crear-torneo";
      }
      // Lógica para crear el torneo (a implementar en el servicio)
      torneoService.crearTorneoPersonalizado(crearTorneoDTO);
      redirectAttributes.addFlashAttribute("mensajeTorneo", "¡Torneo creado exitosamente!");
      return "redirect:/home";
   }

   @GetMapping("/historial")
   public String historialTorneos(Model model, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      if (usuarioId == null) {
         return "redirect:/login";
      }
      Usuario usuario = this.usuarioService.buscarUsuarioPorId(usuarioId);
      if (usuario == null || usuario.getEquipo() == null) {
         model.addAttribute("torneosGanados", null);
         model.addAttribute("torneosOtros", null);
         return "vista-historial-torneos";
      }
      Long equipoId = usuario.getEquipo().getId();
      List<EquipoTorneoDTO> equiposTorneos = equipoTorneoService.getAllByEquipoId(equipoId);
      List<HistorialTorneoDTO> ganados = new ArrayList<>();
      List<HistorialTorneoDTO> otros = new ArrayList<>();
      // Traer todos los partidos jugados por el equipo
      List<com.tallerwebi.presentacion.dto.PartidoHistorialDTO> partidos = partidoService
            .obtenerPartidosJugadosPorEquipoId(equipoId);
      for (EquipoTorneoDTO etdto : equiposTorneos) {
         TorneoDTO torneo = etdto.getTorneo();
         System.out.println("[HISTORIAL] Torneo: " + (torneo != null ? torneo.getNombre() : "null") +
               " | Estado: " + (torneo != null && torneo.getEstado() != null ? torneo.getEstado().name() : "null") +
               " | Puesto: " + etdto.getPosicion());
         if (torneo != null && torneo.getEstado() == EstadoTorneoEnum.FINALIZADO) {
            HistorialTorneoDTO dto = new HistorialTorneoDTO();
            dto.setNombreTorneo(torneo.getNombre());
            dto.setTipoTorneo(torneo.getFormatoTorneo() != null ? torneo.getFormatoTorneo().getTipo().name() : "");
            dto.setPuesto(etdto.getPosicion());
            dto.setGanado(etdto.getPosicion() == 1);
            dto.setTop3(etdto.getPosicion() <= 3);
            dto.setEstado(torneo.getEstado());
            // Calcular goles marcados en ese torneo
            int goles = partidos.stream()
                  .filter(p -> p.getTorneo() != null && p.getTorneo().getId().equals(torneo.getId()))
                  .mapToInt(p -> {
                     if (p.getEquipoLocal() != null && p.getEquipoLocal().getId().equals(equipoId)) {
                        return p.getGolesLocal() != null ? p.getGolesLocal() : 0;
                     } else if (p.getEquipoVisitante() != null && p.getEquipoVisitante().getId().equals(equipoId)) {
                        return p.getGolesVisitante() != null ? p.getGolesVisitante() : 0;
                     } else {
                        return 0;
                     }
                  }).sum();
            dto.setGolesMarcados(goles);
            if (dto.isGanado()) {
               ganados.add(dto);
            } else {
               otros.add(dto);
            }
         }
      }
      model.addAttribute("torneosGanados", ganados);
      model.addAttribute("torneosOtros", otros);
      return "vista-historial-torneos";
   }
}