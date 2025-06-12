package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
public interface UsuarioService{
    Boolean agregarSobreAJugador(Long idUsuario, SobreDTO sobreDTO);

    Usuario buscarUsuarioPorId(Long id);

    List<SobreDTO> obtenerSobresDelUsuario(Long id);
}
