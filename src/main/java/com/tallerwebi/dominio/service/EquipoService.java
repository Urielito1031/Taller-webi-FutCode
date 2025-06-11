package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.presentacion.dto.EquipoDTO;

import java.util.List;

public interface EquipoService{
   List<EquipoDTO> getAll();
    EquipoDTO getById(Long id);
   EquipoDTO getByNombre(String nombre);
   void save(EquipoDTO equipo);
   void update(EquipoDTO equipo);
    void delete(Long id);

   //void assignEquipoToUsuario(Long usuarioId, Long equipoId);

}
