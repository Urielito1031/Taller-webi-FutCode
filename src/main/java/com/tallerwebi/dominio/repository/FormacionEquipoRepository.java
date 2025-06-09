package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.FormacionEquipo;

import java.util.List;

public interface FormacionEquipoRepository{
   List<FormacionEquipo> findByEquipoId(Long equipoId);
   void deleteByEquipoId(Long equipoId);
   void save(FormacionEquipo formacionEquipo);
}
