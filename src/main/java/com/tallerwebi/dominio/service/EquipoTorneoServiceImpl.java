package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class EquipoTorneoServiceImpl implements EquipoTorneoService {
   public static final int CAPACIDAD_MAXIMA_TORNEO_LIGA = 15;
   public static final int CAPACIDAD_MAXIMA_TORNEO_COPA = 32;

   private final EquipoTorneoRepository repository;

   private final TorneoRepository torneoRepository;
   private final EquipoRepository equipoRepository;

   private final TorneoService torneoService;

   @Autowired
   public EquipoTorneoServiceImpl(EquipoTorneoRepository repository, TorneoRepository torneoRepository, EquipoRepository equipoRepository, TorneoService torneoService) {
      this.repository = repository;
      this.torneoRepository = torneoRepository;
      this.equipoRepository = equipoRepository;
      this.torneoService = torneoService;
   }

   @Override
   public List<EquipoTorneoDTO> getAllByTorneoId(Long torneoId){
      List<EquipoTorneo> torneoEquipos = repository.getAllByTorneoId(torneoId);
      return torneoEquipos.stream()
        .map(EquipoTorneo::convertToDTO)
        .collect(Collectors.toList());
   }

   @Override
   public void unirseTorneo(Long torneoId,Long equipoId){
      if(!torneoYEquipoEsValido(torneoId, equipoId)){
         throw new IllegalArgumentException("El torneo o equipo asociado no pueden ser nulos o no existen");
      }

      //validar que el torneo no tenga el equipo a unir
      if(!validarEquipoNoUnidoATorneo(torneoId, equipoId)){
         throw new IllegalArgumentException("El equipo ya se encuentra unido al torneo");
      }

      Torneo torneo = torneoRepository.getById(torneoId);

      verificarFormatoTorneoParaValidarCapacidadMaxima(torneoId,torneo);

      repository.unirEquipoATorneo(torneoId, equipoId);
   }

   private boolean validarEquipoNoUnidoATorneo(Long torneoId,Long equipoId){
      //aca llamamos al repositorio para verificar si el equipo ya esta unido al torneo
      List<EquipoTorneo> equiposTorneo = repository.getAllByTorneoId(torneoId);
      return equiposTorneo.stream()
        .noneMatch(equipoTorneo ->
          equipoTorneo.getEquipo().getId().equals(equipoId));
   }

   private void verificarFormatoTorneoParaValidarCapacidadMaxima(Long torneoId,Torneo torneo){
      int cantidadDeEquipos = repository.getAllByTorneoId(torneoId).size();

      if(torneo.getFormatoTorneo().getTipo().equals(TipoFormato.LIGA)){
         if(cantidadDeEquipos == CAPACIDAD_MAXIMA_TORNEO_LIGA){
            this.torneoService.crearFixtureConLasFechas(torneoId);
         }

         if(cantidadDeEquipos >= CAPACIDAD_MAXIMA_TORNEO_LIGA){
            throw new IllegalArgumentException("El torneo ya tiene el maximo de equipos permitidos");
         }
      }

      if(torneo.getFormatoTorneo().getTipo().equals(TipoFormato.COPA)){
         if(repository.getAllByTorneoId(torneoId).size() >= CAPACIDAD_MAXIMA_TORNEO_COPA){
            throw new IllegalArgumentException("El torneo ya tiene el maximo de equipos permitidos");
         }
      }
   }

   private boolean torneoYEquipoEsValido(Long torneoId,Long equipoId){
      return torneoId != null &&
             equipoId != null &&
             torneoRepository.existsById(torneoId) &&
             equipoRepository.existsById(equipoId);
   }


}