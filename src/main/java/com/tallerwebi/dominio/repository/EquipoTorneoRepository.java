package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;

import java.util.List;

public interface EquipoTorneoRepository{

   //Metodo para usar cuando el usuario selecciona detalle de un torneo
   List<EquipoTorneo> getAllByTorneoId(Long torneoId);

   void unirEquipoATorneo(Long torneoId, Long equipoId);


}
