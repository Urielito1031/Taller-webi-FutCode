package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.TipoSobre;
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

    private final RepositorioUsuarioImpl repositorioUsuario;
//    private final SessionFactory sessionFactory;
//

    @Autowired
    public UsuarioServiceImpl(RepositorioUsuarioImpl repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
//        this.sessionFactory = sessionFactory;
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

    public SobreDTO convertirEntidadADTO(Sobre sobre) {
        TipoSobre tipo;

        if (sobre instanceof SobreBronce) {
            tipo = TipoSobre.BRONCE;
        } else if (sobre instanceof SobrePlata) {
            tipo = TipoSobre.PLATA;
        } else if (sobre instanceof SobreOro) {
            tipo = TipoSobre.ORO;
        } else if (sobre instanceof SobreEspecial) {
            tipo = TipoSobre.ESPECIAL;
        } else {
            throw new TipoDeSobreDesconocido();
        }

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTitulo(sobre.getTitulo());
        sobreDTO.setPrecio(sobre.getPrecio());
        sobreDTO.setTipoSobre(tipo);
        sobreDTO.setImagenUrl(sobre.getImagenUrl());

        sobreDTO.setId(sobre.getId());

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

    @Override
    public List<Jugador> convertirJugadoresDtoToEntity(List<JugadorDTO> jugadores) {
        List<Jugador> jugadoresEntidad = new ArrayList<>();

        for (JugadorDTO jugadorDTO : jugadores) {
            jugadoresEntidad.add(jugadorDTO.convertToEntity(jugadorDTO));
        }

        return jugadoresEntidad;
    }



    //    @Override
//    public void actualizarUsuario(Usuario usuario) {
//        getSession().update(usuario);
//    }
//
@Override
public void actualizar(Usuario usuario) {
        repositorioUsuario.actualizar(usuario);
    }


//    private Session getSession() {
//        return sessionFactory.getCurrentSession();
//    }

}
