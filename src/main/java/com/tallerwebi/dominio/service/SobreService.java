package com.tallerwebi.dominio.service;


import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.dto.SobreDTO;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface SobreService {
    SobreDTO crearSobre(TipoSobre tipo);

    List<SobreDTO> obtenerSobresDTO();
}
