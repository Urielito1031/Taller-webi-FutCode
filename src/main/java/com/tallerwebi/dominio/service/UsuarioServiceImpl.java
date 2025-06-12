package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements  UsuarioService{

    private RepositorioUsuarioImpl repositorioUsuario;

    @Autowired
    public UsuarioServiceImpl(RepositorioUsuarioImpl repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Boolean agregarSobreAJugador(Long idUsuario, SobreDTO sobreDTO) {
        Usuario usuario = this.buscarUsuarioPorId(idUsuario);

        // agregar exception
        if(usuario == null) {
            return false;
        }
        Sobre sobre = sobreDTO.fromEntity();
        sobre.setUsuario(usuario);
        sobre.setPrecio(1.0);
        sobre.setTitulo("aa");
//        sobre.setTipoSobre(TipoSobre.BRONCE);
        Boolean agregado = usuario.getSobres().add(sobre);

        this.repositorioUsuario.actualizarUsuario(usuario);

        return agregado;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public List<SobreDTO> obtenerSobresDelUsuario(long l) {
        return List.of();
    }


    private Sobre convertirDTOAEntidad(SobreDTO sobreDTO) {
        Sobre sobre = new Sobre();
        sobre.setTipoSobre(sobreDTO.getTipoSobre());
        sobre.setTitulo(sobreDTO.getTitulo());
        sobre.setPrecio(sobreDTO.getPrecio());

        if (sobreDTO.getJugadores() != null && !sobreDTO.getJugadores().isEmpty()) {
            // sobre.setJugadores(convertirJugadoresDTO(sobreDTO.getJugadores()));
        }

        return sobre;
    }

    private SobreDTO convertirEntidadADTO(Sobre sobre) {
        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(sobre.getTipoSobre());
        sobreDTO.setTitulo(sobre.getTitulo());
        sobreDTO.setPrecio(sobre.getPrecio());

        // Si tienes jugadores en el sobre, también convertirlos
//        if (sobre.getJugadores() != null && !sobre.getJugadores().isEmpty()) {
//            // sobreDTO.setJugadores(convertirJugadoresEntidad(sobre.getJugadores()));
//        }

        return sobreDTO;
    }

//    @Override
    public List<SobreDTO> obtenerSobresDelUsuario(Long usuarioId) {
        try {
            // Obtener sobres desde el repositorio
            List<Sobre> sobres = this.repositorioUsuario.obtenerSobresDelUsuario(usuarioId);

            // Convertir entidades a DTOs
            return sobres.stream()
                    .map(this::convertirEntidadADTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error al obtener sobres del usuario en service: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
