package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.PartidoDTO2;
import com.tallerwebi.presentacion.dto.TablaGeneralDTO;

import java.util.List;

public interface TorneoService2 {
    TablaGeneralDTO obtenerTablaGeneral(Long id);

    List<PartidoDTO2> obtenerCalendario(Long id);
}
