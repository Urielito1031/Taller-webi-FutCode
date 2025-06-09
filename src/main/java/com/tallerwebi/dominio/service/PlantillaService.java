package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import java.util.List;

public interface PlantillaService {
   Boolean save(EsquemaDTO formacion);
   Boolean validarFormacion(EsquemaDTO formacion);
   void asignarPosicionesYJugadores(EsquemaDTO formacion);
   List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema);

}
