package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import org.springframework.stereotype.Service;


@Service
public interface UsuarioService{

    void agregarSobreAJugador(Long idUsuario, TipoSobre tipoSobre);

}
