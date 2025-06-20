package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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

        switch (sobre.getTipoSobre()){
            case BRONCE:
                sobre.setTitulo("Sobre de Bronce");
                sobre.setPrecio(2500.0);
                sobre.setImagenUrl("sobreFutCodeBronce.png");
                break;
            case PLATA:
                sobre.setTitulo("Sobre de Plata");
                sobre.setPrecio(5000.0);
                sobre.setImagenUrl("sobreFutCodePlata.png");
                break;
            case ORO:
                sobre.setTitulo("Sobre de Oro");
                sobre.setPrecio(7500.0);
                sobre.setImagenUrl("sobreFutCodeOro.png");
                break;
            case ESPECIAL:
                sobre.setTitulo("Sobre de Especial");
                sobre.setPrecio(10000.0);
                sobre.setImagenUrl("sobreFutCodeEspecial.png");
                break;
                default:
                    return false;
        }

        sobre.setUsuario(usuario);

        Boolean agregado = usuario.getSobres().add(sobre);

        this.repositorioUsuario.actualizarUsuario(usuario);

        return agregado;
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }


    @Override
    public List<SobreDTO> obtenerSobresDelUsuario(Long id) {
        List<Sobre> sobres = this.repositorioUsuario.obtenerSobresDelUsuario(id);
        return sobres.stream().map(this::convertirEntidadADTO).collect(Collectors.toList());
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
        sobreDTO.setImagenUrl(sobre.getImagenUrl());

        // Si tienes jugadores en el sobre, también convertirlos
//        if (sobre.getJugadores() != null && !sobre.getJugadores().isEmpty()) {
//            // sobreDTO.setJugadores(convertirJugadoresEntidad(sobre.getJugadores()));
//        }

        return sobreDTO;
    }

}
