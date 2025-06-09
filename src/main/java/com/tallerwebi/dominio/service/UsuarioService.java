package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.stereotype.Service;


@Service
public interface UsuarioService{

    Boolean agregarSobreAJugador(Long idUsuario, SobreDTO sobreDTO);
}
