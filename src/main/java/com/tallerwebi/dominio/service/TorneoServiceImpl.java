package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;

import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import com.tallerwebi.presentacion.dto.CrearTorneoDTO;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import com.tallerwebi.dominio.repository.FormatoTorneoRepository;
import com.tallerwebi.dominio.model.enums.TipoFormato;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService {
   private final TorneoRepository torneoRepository;
   private final EquipoTorneoRepository equipoTorneoRepository;
   private final FormatoTorneoRepository formatoTorneoRepository;

   @Autowired
   public TorneoServiceImpl(TorneoRepository torneoRepository, EquipoTorneoRepository equipoTorneoRepository,
         FormatoTorneoRepository formatoTorneoRepository) {
      this.torneoRepository = torneoRepository;
      this.equipoTorneoRepository = equipoTorneoRepository;
      this.formatoTorneoRepository = formatoTorneoRepository;
   }

   @Override
   public List<TorneoDTO> getAll() {
      List<Torneo> torneos = torneoRepository.findAll();
      return torneos.stream().map(Torneo::convertToDTO).collect(Collectors.toList());
   }

   @Override
   public TorneoDTO getById(Long id) {
      Torneo torneoById = torneoRepository.getById(id);
      if (torneoById == null) {
         return null;
      }
      return torneoById.convertToDTO();
   }

   @Override
   @Transactional
   public Torneo obtenerTorneoConFechas(Long torneoId) {
      return torneoRepository.obtenerTorneoConFechas(torneoId);
   }

   @Override
   public List<EquipoTorneo> calcularTablaDePosiciones(List<Partido> partidos, List<EquipoTorneo> tablaAnterior) {
      Map<Long, EquipoTorneo> tablaDePosiciones = new HashMap<>();

      for (EquipoTorneo equipoAnterior : tablaAnterior) {
         equipoAnterior.setPosicionAnterior(equipoAnterior.getPosicion());

         equipoAnterior.setPuntos(0);
         equipoAnterior.setPartidosJugados(0);
         equipoAnterior.setPartidosGanados(0);
         equipoAnterior.setPartidosEmpatados(0);
         equipoAnterior.setPartidosPerdidos(0);
         equipoAnterior.setGolesAFavor(0);
         equipoAnterior.setGolesEnContra(0);

         tablaDePosiciones.put(equipoAnterior.getEquipo().getId(), equipoAnterior);
      }

      for (Partido partido : partidos) {
         if (partido.getResultado() != ResultadoPartido.PENDIENTE) {
            Equipo local = partido.getEquipoLocal();
            Equipo visitante = partido.getEquipoVisitante();

            // Equipo local
            tablaDePosiciones.putIfAbsent(local.getId(), new EquipoTorneo());
            EquipoTorneo equipoLocal = tablaDePosiciones.get(local.getId());
            if (equipoLocal.getEquipo() == null)
               equipoLocal.setEquipo(local);
            equipoLocal.actualizarConPartido(partido, true);

            // Equipo visitante
            tablaDePosiciones.putIfAbsent(visitante.getId(), new EquipoTorneo());
            EquipoTorneo equipoVisitante = tablaDePosiciones.get(visitante.getId());
            if (equipoVisitante.getEquipo() == null)
               equipoVisitante.setEquipo(visitante);
            equipoVisitante.actualizarConPartido(partido, false);

            equipoTorneoRepository.save(equipoLocal);
            equipoTorneoRepository.save(equipoVisitante);
         }
      }

      List<EquipoTorneo> lista = new ArrayList<>(tablaDePosiciones.values());
      lista.sort(Comparator.comparingInt(EquipoTorneo::getPuntos).reversed());

      for (int i = 0; i < lista.size(); i++) {
         EquipoTorneo equipo = lista.get(i);
         int posicionNueva = i + 1;
         equipo.setPosicion(posicionNueva);
      }

      return lista;
   }

   @Override
   public List<Fecha> generarFechas(List<Equipo> equipos, Torneo torneo) {
      // Verificar si es un torneo de partido único
      boolean esPartidoUnico = torneo.getFormatoTorneo().getTipo().name().equals("PARTIDO_UNICO");

      if (esPartidoUnico) {
         // Para torneos de partido único, generar solo una fecha con un partido
         List<Fecha> fechas = new ArrayList<>();
         Fecha fecha = new Fecha();
         fecha.setNumeroDeFecha(1L);
         fecha.setTorneo(torneo);

         List<Partido> partidos = new ArrayList<>();

         // Crear un solo partido entre los dos equipos
         if (equipos.size() >= 2) {
            Partido partido = new Partido();
            partido.setEquipoLocal(equipos.get(0));
            partido.setEquipoVisitante(equipos.get(1));
            partido.setFecha(fecha);
            LocalDateTime fechaEncuentro = LocalDate.now()
                  .with(DayOfWeek.SATURDAY)
                  .atTime(15, 0); // sábado 15:00 hs

            partido.setFechaEncuentro(fechaEncuentro);
            partidos.add(partido);
            partido.setResultado(ResultadoPartido.PENDIENTE);
         }

         fecha.setPartidos(partidos);
         fechas.add(fecha);
         return fechas;
      }

      // Lógica original para torneos de liga
      int n = equipos.size();
      // Este metodo usa el algoritmo de round-robin xD, googleenlo desp

      // Esto habria que comentarlo si los equipos fuesen pares, pero no lo hago
      // porque el max es 15 y solo se ejecuta si estan los 15
      if (n % 2 != 0) {
         equipos.add(null);
         n++;
      }

      List<Fecha> fechas = new ArrayList<>();

      for (int ronda = 0; ronda < n - 1; ronda++) {
         Fecha fecha = new Fecha();
         fecha.setNumeroDeFecha((long) ronda + 1);
         fecha.setTorneo(torneo);

         List<Partido> partidos = new ArrayList<>();

         for (int i = 0; i < n / 2; i++) {
            Equipo local = equipos.get(i);
            Equipo visitante = equipos.get(n - 1 - i);

            if (local != null && visitante != null) {
               Partido partido = new Partido();
               partido.setEquipoLocal(local);
               partido.setEquipoVisitante(visitante);
               partido.setFecha(fecha);
               LocalDateTime fechaEncuentro = LocalDate.now()
                     .plusWeeks(ronda)
                     .with(DayOfWeek.SATURDAY)
                     .atTime(15, 0); // sábado 15:00 hs

               fechaEncuentro = fechaEncuentro.with(java.time.DayOfWeek.SATURDAY);
               partido.setFechaEncuentro(fechaEncuentro);
               partidos.add(partido);
               partido.setResultado(ResultadoPartido.PENDIENTE);
            }
         }

         fecha.setPartidos(partidos);
         fechas.add(fecha);

         // rotar equipos (excepto el primero)
         List<Equipo> nuevos = new ArrayList<>();
         nuevos.add(equipos.get(0));
         nuevos.add(equipos.get(n - 1));
         for (int j = 1; j < n - 1; j++) {
            nuevos.add(equipos.get(j));
         }
         equipos = nuevos;
      }

      return fechas;
   }

   @Override
   public void crearFixtureConLasFechas(Long torneoId) {
      Torneo torneo = this.torneoRepository.getById(torneoId);

      List<Equipo> equipos = torneo.getEquipos().stream()
            .map(EquipoTorneo::getEquipo)
            .collect(Collectors.toList());

      List<Fecha> fechas = this.generarFechas(equipos, torneo);

      torneo.getFechas().addAll(fechas);
      torneoRepository.save(torneo);
   }

   @Override
   public void crearTorneoPersonalizado(CrearTorneoDTO crearTorneoDTO) {
      Torneo torneo = new Torneo();
      torneo.setDescripcion(crearTorneoDTO.getDescripcion());
      torneo.setPremioMonedas(crearTorneoDTO.getMonedasPrimerLugar().doubleValue());
      torneo.setEstado(EstadoTorneoEnum.ABIERTO);
      torneo.setNombre(crearTorneoDTO.getNombre());
      torneo.setCapacidadMaxima(crearTorneoDTO.getCantidadEquipos());
      // Si la capacidad es 2, formato PARTIDO_UNICO, si no, LIGA
      TipoFormato tipoFormato = crearTorneoDTO.getCantidadEquipos() == 2 ? TipoFormato.PARTIDO_UNICO : TipoFormato.LIGA;
      FormatoTorneo formato = formatoTorneoRepository.findByTipo(tipoFormato);
      if (formato == null) {
         formato = new FormatoTorneo();
         formato.setTipo(tipoFormato);
         formatoTorneoRepository.save(formato);
      }
      torneo.setFormatoTorneo(formato);
      torneoRepository.save(torneo);
   }

   @Override
   public List<TorneoDTO> getAllByFormato(TipoFormato tipoFormato) {
      List<Torneo> torneos = torneoRepository.findAllByFormato(tipoFormato);
      return torneos.stream().map(Torneo::convertToDTO).collect(Collectors.toList());
   }
}
