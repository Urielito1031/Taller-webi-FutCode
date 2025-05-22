package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;

import java.util.List;

public interface PlantillaService {
   FormacionDTO initPlantillaBase();
   Boolean save(FormacionDTO formacion);
   Boolean validarFormacion(FormacionDTO formacion);
   void asignarPosicionesYJugadores(FormacionDTO formacion);
   List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema);

}
