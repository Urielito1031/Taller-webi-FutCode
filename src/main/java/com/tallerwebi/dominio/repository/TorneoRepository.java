package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Torneo;

import java.util.List;

public interface TorneoRepository {
   List<Torneo> findAll();
   void save(Torneo torneo);
   Torneo getById(Long id);

   boolean existsById(Long torneoId);

   Torneo obtenerTorneoConFechas(Long torneoId);
}
