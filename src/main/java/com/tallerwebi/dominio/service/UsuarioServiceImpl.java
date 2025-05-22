package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioServiceImpl implements  UsuarioService{

    private Map<Long, Usuario> jugadores = new HashMap<>(); // a cambiar por una peticion a un repositorio

    public UsuarioServiceImpl() {
        jugadores.put(1L, new Usuario()); // Ejemplo de jugador hardcodeado
    }

    public void agregarSobreAJugador(Long id, TipoSobre tipoSobre) {
        Usuario usuario = jugadores.get(id);
        SobreDTO sobre = null;

        switch (tipoSobre) {
            case BRONCE:
                sobre = new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png");
                break;

            case PLATA:
                sobre = new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png");
                break;

            case ORO:
                sobre = new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png");
                break;

            case ESPECIAL:
                sobre = new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png");
                break;
        }

        usuario.agregarSobre(sobre);
    }

}
