package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JugadorServiceImpl implements JugadorService{


   private final JugadorRepository repository;

   @Autowired
   public JugadorServiceImpl(JugadorRepository repository) {
      this.repository = repository;
   }


   @Override
   public List<JugadorDTO> getAll(){

      List<Jugador> jugadoresEntities = repository.getAll();

      return jugadoresEntities.stream()
        .map(Jugador::convertToDTO)
        .collect(Collectors.toList());
   }

   @Override
   public List<JugadorDTO> getAllByEquipoId(Long equipoId){
   List<Jugador> jugadoresEntities = repository.getAllByEquipoId(equipoId);
   if (jugadoresEntities != null && !jugadoresEntities.isEmpty()) {
      return jugadoresEntities.stream()
        .map(Jugador::convertToDTO)
        .collect(Collectors.toList());
   }
      return null;
   }
}
