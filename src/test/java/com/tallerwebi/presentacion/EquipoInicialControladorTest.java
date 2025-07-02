package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.dominio.service.*;
import com.tallerwebi.presentacion.controller.EquipoInicialControlador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EquipoInicialControladorTest {

    @InjectMocks
    private JugadorService jugadorService;

    @Mock
    private JugadorRepository jugadorRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @InjectMocks
    private EquipoService equipoService;

    private EquipoInicialControlador equipoInicialControlador;

    @BeforeEach
    public void init(){
        this.equipoInicialControlador = new EquipoInicialControlador(jugadorService, usuarioService, equipoService);
    }

//    @Test
//    public void dadoQueCreoUnEquipoEsteObtendraLos14JugadoresIniciales() throws Exception {
//        Equipo equipoNuevo = new Equipo();
//
//        List<Jugador> jugadores = this.jugadorService.sortearJugadoresIniciales(14);
//
//
//    }

}
