package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipoTorneoServiceImpl implements EquipoTorneoService {
   private final EquipoTorneoRepository repository;

   private final TorneoRepository torneoRepository;
   private final EquipoRepository equipoRepository;

   private final TorneoService torneoService;

   @Autowired
   public EquipoTorneoServiceImpl(EquipoTorneoRepository repository, TorneoRepository torneoRepository,
         EquipoRepository equipoRepository, TorneoService torneoService) {
      this.repository = repository;
      this.torneoRepository = torneoRepository;
      this.equipoRepository = equipoRepository;
      this.torneoService = torneoService;
   }

   @Override
   public List<EquipoTorneoDTO> getAllByTorneoId(Long torneoId) {
      List<EquipoTorneo> torneoEquipos = repository.getAllByTorneoId(torneoId);
      return torneoEquipos.stream()
            .map(EquipoTorneo::convertToDTO)
            .collect(Collectors.toList());
   }

   @Override
   public List<EquipoTorneo> obtenerEquiposPorIdTorneo(Long idTorneo) {
      return repository.getAllByTorneoId(idTorneo);
   }

   @Override
   public void unirseTorneo(Long torneoId, Long equipoId) {
      if (!torneoYEquipoEsValido(torneoId, equipoId)) {
         throw new IllegalArgumentException("El torneo o equipo asociado no pueden ser nulos o no existen");
      }

      // validar que el torneo no tenga el equipo a unir
      if (!validarEquipoNoUnidoATorneo(torneoId, equipoId)) {
         throw new IllegalArgumentException("El equipo ya se encuentra unido al torneo");
      }

      Torneo torneo = torneoRepository.getById(torneoId);
      Integer capacidadMaxima = torneo.getCapacidadMaxima();
      if (capacidadMaxima == null) {
         throw new IllegalArgumentException("El torneo no tiene definida una capacidad máxima");
      }
      int cantidadDeEquipos = repository.getAllByTorneoId(torneoId).size();
      if (cantidadDeEquipos >= capacidadMaxima) {
         throw new IllegalArgumentException("El torneo ya tiene el maximo de equipos permitidos");
      }

      // Si hay cupo, unir el equipo
      repository.unirEquipoATorneo(torneoId, equipoId);

      // Luego, verificar si hay que cambiar estado/generar fixture
      verificarCapacidadMaximaYActualizarEstado(torneoId, torneo);
   }

   @Override
   public List<EquipoTorneoDTO> getAllByEquipoId(Long equipoId) {
      List<EquipoTorneo> equipoTorneos = repository.getAllByEquipoId(equipoId);
      return equipoTorneos.stream()
              .map(EquipoTorneo::convertToDTO)
              .collect(Collectors.toList());
   }

   private boolean validarEquipoNoUnidoATorneo(Long torneoId, Long equipoId) {
      List<EquipoTorneo> equiposTorneo = repository.getAllByTorneoId(torneoId);
      return equiposTorneo.stream()
            .noneMatch(equipoTorneo -> equipoTorneo.getEquipo().getId().equals(equipoId));
   }

   private void verificarCapacidadMaximaYActualizarEstado(Long torneoId, Torneo torneo) {
      int cantidadDeEquipos = repository.getAllByTorneoId(torneoId).size();
      Integer capacidadMaxima = torneo.getCapacidadMaxima();
      if (capacidadMaxima == null) {
         throw new IllegalArgumentException("El torneo no tiene definida una capacidad máxima");
      }
      if (cantidadDeEquipos == capacidadMaxima) {
         // Cambiar estado del torneo a EN_CURSO y generar fixture
         torneo.setEstado(EstadoTorneoEnum.EN_CURSO);
         torneoRepository.save(torneo);
         this.torneoService.crearFixtureConLasFechas(torneoId);
      }
      if (cantidadDeEquipos > capacidadMaxima) {
         throw new IllegalArgumentException("El torneo ya tiene el maximo de equipos permitidos");
      }
   }

   private boolean torneoYEquipoEsValido(Long torneoId, Long equipoId) {
      return torneoId != null &&
            equipoId != null &&
            torneoRepository.existsById(torneoId) &&
            equipoRepository.existsById(equipoId);
   }

}