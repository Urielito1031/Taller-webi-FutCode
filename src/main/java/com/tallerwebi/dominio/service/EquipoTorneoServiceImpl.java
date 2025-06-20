package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
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

   @Autowired
   public EquipoTorneoServiceImpl(EquipoTorneoRepository repository) {
      this.repository = repository;
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

   }
}
