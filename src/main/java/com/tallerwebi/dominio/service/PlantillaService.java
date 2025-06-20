package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import java.util.List;

public interface PlantillaService {
   EsquemaDTO initPlantillaBase();
   Boolean save(EsquemaDTO formacion);



   EsquemaDTO getFormacionPorEquipoId(Long equipoId);
}
