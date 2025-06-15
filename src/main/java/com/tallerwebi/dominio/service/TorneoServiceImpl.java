package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Torneo;

import com.tallerwebi.dominio.repository.TorneoRepository;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TorneoServiceImpl implements TorneoService{

   private final TorneoRepository repository;

   @Autowired
   public TorneoServiceImpl(TorneoRepository repository) {
      this.repository = repository;
   }

   @Override
   public List<TorneoDTO> getAll() {
      List<Torneo> torneos = repository.findAll();

      return torneos.stream()
            .map(Torneo::convertToDTO)
            .collect(Collectors.toList());
   }

   @Override
   public TorneoDTO getById(Long id){
      Torneo torneoById = repository.getById(id);
      if(torneoById == null){
         return null;
      }
      return torneoById.convertToDTO();
   }


}
