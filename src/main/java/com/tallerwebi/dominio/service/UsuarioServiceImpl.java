package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements  UsuarioService{

    @Getter
    private Map<Long, Usuario> jugadores = new HashMap<>(); // a cambiar por una peticion a un repositorio

    private RepositorioUsuarioImpl repositorioUsuario;

    public UsuarioServiceImpl(RepositorioUsuarioImpl repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
        jugadores.put(1L, new Usuario()); // Ejemplo de jugador hardcodeado
    }

    @PostMapping("/jugador/agregarSobre")
    public Boolean agregarSobreAJugador(Long id, SobreDTO sobreDTO) {
        Usuario usuario = this.repositorioUsuario.buscarUsuarioPorId(id);
        return usuario.getSobres().add(sobreDTO.fromEntity());
    }

}
