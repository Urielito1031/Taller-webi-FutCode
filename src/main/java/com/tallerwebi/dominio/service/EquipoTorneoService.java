package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;

import java.util.List;

public interface EquipoTorneoService{


   //Metodo para usar cuando selecciona unirse a un torneo
   void unirseTorneo(Long torneoId, Long equipoId);

   List<EquipoTorneo> getAllByEquipoId(Long equipoId);
}
