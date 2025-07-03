package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.dominio.service.*;
import com.tallerwebi.presentacion.controller.EquipoInicialControlador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.hsqldb.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EquipoInicialControladorTest {

    @Mock
    private JugadorRepository jugadorRepository;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private EquipoRepository equipoRepository;

    @Mock
    private UsuarioServiceImpl usuarioService;

    @Mock
    private JugadorServiceImpl jugadorService;

    @Mock
    private EquipoServiceImpl equipoService;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private EquipoInicialControlador equipoInicialControlador;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void dadoQueCreoUnEquipoInicialEsteObtendraLos14JugadoresIniciales() throws Exception {
        EquipoDTO equipoNuevo = new EquipoDTO();
        equipoNuevo.setNombre("Nuevo Equipo");
        equipoNuevo.setId(1L);

        List<JugadorDTO> jugadoresMock = new ArrayList<>();

        // cargamos lista de jugadores
        for(int i = 0; i < 14; i++){
            JugadorDTO jugador = new JugadorDTO();
            jugador.setNombre("Jugador " + i);
            jugador.setId((long)i);
            jugadoresMock.add(jugador);
        }

        // cargamos los jugadores en el equipo
        doAnswer(invocation -> {
            EquipoDTO equipo = invocation.getArgument(0);
            equipo.setJugadores(jugadoresMock);
            return null;
        }).when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));

        when(jugadorService.getAllByEquipoId(equipoNuevo.getId())).thenReturn(jugadoresMock);

        jugadorService.cargarJugadoresAlEquipo(equipoNuevo);
        List<JugadorDTO> resultado = jugadorService.getAllByEquipoId(equipoNuevo.getId());

        assertNotNull(resultado);
        assertEquals(14, resultado.size());
        System.out.println(resultado.get(0).getNombre());
        System.out.println(resultado.get(1).getNombre());
    }

    @Test
    public void dadoQueTengoUnEquipoInicialControladorDeseoVerificarElProcesoCompletoObteniendoRespuestaCorrecta(){

        EquipoDTO equipoNuevo = new EquipoDTO();
        equipoNuevo.setNombre("EquipoMock");
        equipoNuevo.setId(1L);

        Long idUsuario = 2L;
        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(idUsuario);

        List<JugadorDTO> jugadoresMock = IntStream.range(0, 14)
                .mapToObj(i -> {
                    JugadorDTO jugador = new JugadorDTO();
                    jugador.setNombre("Jugador " + i);
                    jugador.setId((long)i);
                    jugador.setRarezaJugador(RarezaJugador.NORMAL);
                    return jugador;
                }).collect(Collectors.toList());

        doAnswer( invocation -> {
            EquipoDTO equipo = invocation.getArgument(0);
            equipo.setJugadores(jugadoresMock);
            return null;
        }).when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));

        when(httpSession.getAttribute("equipo")).thenReturn(equipoNuevo);
        when(httpSession.getAttribute("USUARIO_ID")).thenReturn(idUsuario);
        when(usuarioService.buscarUsuarioPorId(idUsuario)).thenReturn(usuarioMock);

        ModelAndView mav = this.equipoInicialControlador.sorteEquipoInicial(httpSession);

        assertEquals("sorteoEquipo", mav.getViewName());
        assertEquals(equipoNuevo, mav.getModel().get("equipo"));
        assertEquals("EquipoMock", mav.getModel().get("nombreEquipo"));
        assertNotNull(equipoNuevo.getJugadores());
        assertEquals(14, equipoNuevo.getJugadores().size());

       verify(equipoService).saveBoth(equipoNuevo, usuarioMock);
    }

}
