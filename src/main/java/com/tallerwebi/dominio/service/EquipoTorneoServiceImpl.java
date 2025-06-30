package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;
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

   @Autowired
   public EquipoTorneoServiceImpl(EquipoTorneoRepository repository,TorneoRepository torneoRepository,EquipoRepository equipoRepository) {
      this.repository = repository;
      this.torneoRepository = torneoRepository;
      this.equipoRepository = equipoRepository;
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
      if(torneoYEquipoEsValido(torneoId, equipoId)){
         throw new IllegalArgumentException("El torneo o equipo asociado no pueden ser nulos o no existen");
      }
      //validar cantidad de equipos en torneo, si es copa, pueden ser 30, si es liga, pueden ser 20

      repository.unirEquipoATorneo(equipoId, torneoId);

   }

   private boolean torneoYEquipoEsValido(Long torneoId,Long equipoId){
      if(torneoId == null || equipoId == null){
         return true;
      }

      if(!torneoRepository.existsById(torneoId)){
         throw new IllegalArgumentException("El torneo no existe");
      }

      if(!equipoRepository.existsById(equipoId)){
         throw new IllegalArgumentException("El equipo no existe");
      }

      return false;
   }
}
