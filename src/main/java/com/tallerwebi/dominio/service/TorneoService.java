package com.tallerwebi.dominio.service;

import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.TablaGeneralDTO;

import java.util.List;

public interface TorneoService {
    TablaGeneralDTO obtenerTablaGeneral(Long id);

    List<PartidoDTO> obtenerCalendario(Long id);
}
