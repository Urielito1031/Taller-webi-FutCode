package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;

import java.util.List;

public interface EquipoTorneoRepository{

   List<EquipoTorneo> getAllByTorneoId(Long torneoId);


   void unirEquipoATorneo(Long torneoId, Long equipoId);

   void save(EquipoTorneo equipoTorneo);

}
