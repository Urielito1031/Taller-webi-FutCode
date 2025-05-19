package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.dto.SobreDTO;

public interface SobreService {
    SobreDTO obtenerSobre(TipoSobre tipoSobre);//a cambiar por Sobre
}
