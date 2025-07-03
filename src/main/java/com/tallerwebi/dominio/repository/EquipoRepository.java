package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Equipo;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface EquipoRepository {
   List<Equipo>getAll();
   Equipo getById(Long id);
   void save(Equipo equipo);
   Boolean existsById(Long equipoId);

   void saveAndFlush(Equipo equipo);
}
