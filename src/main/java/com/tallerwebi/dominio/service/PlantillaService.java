package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.FormacionDTO;

public interface PlantillaService {
   Boolean save(FormacionDTO formacion);
   Boolean formacionIsValid(FormacionDTO formacion);



}
