package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.EsquemaDTO;


public interface PlantillaService {
   EsquemaDTO initPlantillaBase(Long idEquipo);
   Boolean save(EsquemaDTO formacion);



   EsquemaDTO getFormacionPorEquipoId(Long equipoId);
}
