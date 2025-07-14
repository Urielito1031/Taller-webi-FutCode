package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;

import java.util.List;


public interface UsuarioService{
    Boolean agregarSobreAJugador(Long idUsuario, SobreDTO sobreDTO);

    Usuario buscarUsuarioPorId(Long id);

    List<SobreDTO> obtenerSobresDelUsuario(Long id);

    void borrarSobreAUsuario(Long idUsuario, Long idSobre);

    void sumarPremioMonedas(Partido partido, ResultadoPartido resultado);

    List<JugadorDTO> convertirJugadoresEntidad(List<Jugador> jugadores);

    void actualizar(Usuario usuario);

    List<Jugador> convertirJugadoresDtoToEntity(List<JugadorDTO> jugadores);


}
