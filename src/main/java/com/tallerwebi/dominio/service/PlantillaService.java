package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.EsquemaDTO;

import javax.transaction.Transactional;


public interface PlantillaService {
   EsquemaDTO initPlantillaBase(Long idEquipo);
   Boolean save(EsquemaDTO formacion);



   EsquemaDTO getFormacionPorEquipoId(Long equipoId);

    @Transactional
    Double getRatingOnceTitular(Long idEquipo);
}
