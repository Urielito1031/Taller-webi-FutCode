package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.factory.SobreFactory;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public Boolean agregarSobreAJugador(Long idUsuario, SobreDTO sobreDTO) {
        Usuario usuario = this.buscarUsuarioPorId(idUsuario);

        if(usuario == null) throw new UsuarioNoEncontrado("El usuario con ID " + idUsuario + " no fue encontrado.");

        Sobre sobre = sobreDTO.fromEntity();
        sobre.setUsuario(usuario);

        validarMonedas(usuario.getMonedas(), sobre.getPrecio());
        usuario.setMonedas(usuario.getMonedas() - sobre.getPrecio());

        Boolean agregado = usuario.getSobres().add(sobre);

        this.repositorioUsuario.actualizarUsuario(usuario);

        return agregado;
    }

    private void validarMonedas(Double monedasUsuario, Double precioSobre){
        if(monedasUsuario < precioSobre){
            throw new MonedasInsuficientes();
        }
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

    @Override
    public void borrarSobreAUsuario(Long idUsuario, Long idSobre){
        this.repositorioUsuario.borrarSobreAUsuario(idUsuario, idSobre);
    }





    private Sobre convertirDTOAEntidad(SobreDTO sobreDTO) {
        Sobre sobre = SobreFactory.crearSobre(sobreDTO.getTipoSobre());
//        sobre.setTipoSobre(sobreDTO.getTipoSobre());
        sobre.setTitulo(sobreDTO.getTitulo());
        sobre.setPrecio(sobreDTO.getPrecio());

        if (sobreDTO.getJugadores() != null && !sobreDTO.getJugadores().isEmpty()) {
            // sobre.setJugadores(convertirJugadoresDTO(sobreDTO.getJugadores()));
        }

        return sobre;
    }

    public SobreDTO convertirEntidadADTO(Sobre sobre) {

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setId(sobre.getId());
//        sobreDTO.setTipoSobre(sobre.getTipoSobre());
        sobreDTO.setTitulo(sobre.getTitulo());
        sobreDTO.setPrecio(sobre.getPrecio());
        sobreDTO.setImagenUrl(sobre.getImagenUrl());

//      convertir jugadores del sobre JugadorDTO tambien
        if (sobre.getJugadores() != null && !sobre.getJugadores().isEmpty()) {
             sobreDTO.setJugadores(convertirJugadoresEntidad(sobre.getJugadores()));
        }

        return sobreDTO;
    }

    public JugadorDTO convertirJugadorEntidadADTO(Jugador jugador) {
        JugadorDTO jugadorDTO = new JugadorDTO();
        jugadorDTO.setId(jugador.getId());
        jugadorDTO.setNombre(jugador.getNombre());
        jugadorDTO.setApellido(jugador.getApellido());
        jugadorDTO.setImagen(jugador.getImagen());
        jugadorDTO.setRating(jugador.getRating());
        jugadorDTO.setPosicionNatural(jugador.getPosicion());
        jugadorDTO.setRarezaJugador(jugador.getRarezaJugador());
        return jugadorDTO;
    }

    @Override
    public List<JugadorDTO> convertirJugadoresEntidad(List<Jugador> jugadores) {
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();

        for (Jugador jugador : jugadores) {
            JugadorDTO jugadorDTO = convertirJugadorEntidadADTO(jugador);
            jugadoresDTO.add(jugadorDTO);
        }

        return jugadoresDTO;
    }

}
