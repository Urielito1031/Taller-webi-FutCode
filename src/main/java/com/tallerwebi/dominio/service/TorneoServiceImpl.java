package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService{
   private final TorneoRepository torneoRepository;
   private final EquipoTorneoRepository equipoTorneoRepository;

   @Autowired
   public TorneoServiceImpl(TorneoRepository torneoRepository, EquipoTorneoRepository equipoTorneoRepository) {
      this.torneoRepository = torneoRepository;
       this.equipoTorneoRepository = equipoTorneoRepository;
   }

   @Override
   public List<TorneoDTO> getAll() {
      List<Torneo> torneos = torneoRepository.findAll();
      return torneos.stream().map(Torneo::convertToDTO).collect(Collectors.toList());
   }

   @Override
   public TorneoDTO getById(Long id){
      Torneo torneoById = torneoRepository.getById(id);
      if(torneoById == null){
         return null;
      }
      return torneoById.convertToDTO();
   }

   @Override @Transactional
   public Torneo obtenerTorneoConFechas(Long torneoId) {
      return torneoRepository.obtenerTorneoConFechas(torneoId);
   }

   @Override
   public List<EquipoTorneo> calcularTablaDePosiciones(List<Partido> partidos, List<EquipoTorneo> tablaAnterior){
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
            if (equipoLocal.getEquipo() == null) equipoLocal.setEquipo(local);
            equipoLocal.actualizarConPartido(partido, true);

            // Equipo visitante
            tablaDePosiciones.putIfAbsent(visitante.getId(), new EquipoTorneo());
            EquipoTorneo equipoVisitante = tablaDePosiciones.get(visitante.getId());
            if (equipoVisitante.getEquipo() == null) equipoVisitante.setEquipo(visitante);
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
      int n = equipos.size();
//    Este metodo usa el algoritmo de round-robin xD, googleenlo desp

//    Esto habria que comentarlo si los equipos fuesen pares, pero no lo hago porque el max es 15 y solo se ejecuta si estan los 15
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
   public Torneo buscarPorId(Long id) {
      return torneoRepository.getById(id);
   }

   @Override
   public void guardar(Torneo torneo) {
      torneoRepository.save(torneo);
   }

   @Override
   public void verificarYFinalizarTorneo(Long torneoId) {
      Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);

      boolean todosJugados = torneo.getFechas().stream()
              .flatMap(f -> f.getPartidos().stream())
              .allMatch(p -> p.getResultado() != ResultadoPartido.PENDIENTE);

      if (todosJugados && torneo.getEstado() != EstadoTorneoEnum.FINALIZADO) {
         torneo.setEstado(EstadoTorneoEnum.FINALIZADO);
         torneoRepository.save(torneo);
      }
   }


}
