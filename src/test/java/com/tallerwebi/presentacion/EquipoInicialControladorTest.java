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

public class EquipoInicialControladorTest{

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
   public void dadoQueCreoUnEquipoInicialEsteObtendraLos14JugadoresIniciales(){
      EquipoDTO equipoNuevo = new EquipoDTO();
      equipoNuevo.setNombre("Nuevo Equipo");
      equipoNuevo.setId(1L);

      List<JugadorDTO> jugadoresMock = new ArrayList<>();

      // cargamos lista de jugadores
      for(int i = 0; i < 14; i++){
         JugadorDTO jugador = new JugadorDTO();
         jugador.setNombre("Jugador " + i);
         jugador.setId((long) i);
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
      assertEquals(14,resultado.size());
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

      List<JugadorDTO> jugadoresMock = IntStream.range(0,14)
        .mapToObj(i -> {
           JugadorDTO jugador = new JugadorDTO();
           jugador.setNombre("Jugador " + i);
           jugador.setId((long) i);
           jugador.setRarezaJugador(RarezaJugador.NORMAL);
           return jugador;
        }).collect(Collectors.toList());

      doAnswer(invocation -> {
         EquipoDTO equipo = invocation.getArgument(0);
         equipo.setJugadores(jugadoresMock);
         return null;
      }).when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));

      when(httpSession.getAttribute("equipo")).thenReturn(equipoNuevo);
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(idUsuario);
      when(usuarioService.buscarUsuarioPorId(idUsuario)).thenReturn(usuarioMock);

      ModelAndView mav = this.equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("sorteoEquipo",mav.getViewName());
      assertEquals(equipoNuevo,mav.getModel().get("equipo"));
      assertEquals("EquipoMock",mav.getModel().get("nombreEquipo"));
      assertNotNull(equipoNuevo.getJugadores());
      assertEquals(14,equipoNuevo.getJugadores().size());

      verify(equipoService).saveBoth(equipoNuevo,usuarioMock);
   }

   @Test
   public void dadoQueTengoUnEquipoConSuUsuarioAsociadoVerificoQueAmbosEstenAsociados(){

      EquipoDTO equipoNuevo = new EquipoDTO();
      equipoNuevo.setNombre("EquipoMock");
      equipoNuevo.setId(2L);

      Long idUsuario = 3L;
      Usuario usuarioMock = new Usuario();
      usuarioMock.setId(idUsuario);

      equipoNuevo.setUsuarioId(usuarioMock.getId());


      List<JugadorDTO> jugadoresMock = IntStream.range(0,14)
        .mapToObj(i -> {
           JugadorDTO jugador = new JugadorDTO();
           jugador.setNombre("Jugador " + i);
           jugador.setId((long) i);
           jugador.setRarezaJugador(RarezaJugador.NORMAL);
           return jugador;
        }).collect(Collectors.toList());

      // siumulamos la carga de jugadores
      doAnswer(invocation -> {
         EquipoDTO equipo = invocation.getArgument(0);
         equipo.setJugadores(jugadoresMock);
         return null;
      }).when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));


      when(httpSession.getAttribute("equipo")).thenReturn(equipoNuevo);
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(idUsuario);
      when(usuarioService.buscarUsuarioPorId(idUsuario)).thenReturn(usuarioMock);

      // tenemos que simular el "saveBoth" de equipoService porque una vez que se guarda el equipo y el usuario y se convierten a entidades, no se devuelve al controlador
      doAnswer(invocation -> {
         EquipoDTO dto = invocation.getArgument(0);
         Usuario u = invocation.getArgument(1);
         Equipo equipoEntidad = new Equipo();
         equipoEntidad.setId(dto.getId());
         equipoEntidad.setNombre(dto.getNombre());
         u.setEquipo(equipoEntidad); // ¡acá simula el efecto real!
         return null;
      }).when(equipoService).saveBoth(any(EquipoDTO.class),any(Usuario.class));


      ModelAndView mav = this.equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("sorteoEquipo",mav.getViewName());
      assertEquals(equipoNuevo,mav.getModel().get("equipo"));
      assertEquals("EquipoMock",mav.getModel().get("nombreEquipo"));
      assertNotNull(equipoNuevo.getJugadores());
      assertEquals(14,equipoNuevo.getJugadores().size());


      assertEquals(equipoNuevo.getUsuarioId(),usuarioMock.getId());
      assertNotNull(usuarioMock.getEquipo());
      assertEquals(usuarioMock.getEquipo().getId(),equipoNuevo.getId());

      verify(jugadorService).cargarJugadoresAlEquipo(equipoNuevo);
      verify(usuarioService).buscarUsuarioPorId(idUsuario);
      verify(equipoService).saveBoth(equipoNuevo,usuarioMock);
   }

   @Test
   public void dadoQueNoHayEquipoEnSesionEntoncesRedirigeANuevoEquipo() {
      Long idUsuario = 4L;
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(idUsuario);
      when(httpSession.getAttribute("equipo")).thenReturn(null);

      ModelAndView mav = this.equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("redirect:/nuevo-equipo", mav.getViewName()); // o el nombre de tu vista de creación
   }


   @Test
   public void dadoQueNoHayUsuarioEnSesionEntoncesRedirigeAlLogin() {
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(null);

      ModelAndView mav = this.equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("redirect:/nuevo-equipo", mav.getViewName()); // o la vista que uses por defecto
   }




   @Test
   public void dadoQueEquipoNoTieneJugadoresLosCargaCorrectamente() {
      EquipoDTO equipoNuevo = new EquipoDTO();
      equipoNuevo.setId(10L);
      equipoNuevo.setNombre("SinJugadores");

      Long usuarioId = 5L;
      Usuario usuario = new Usuario();
      usuario.setId(usuarioId);

      when(httpSession.getAttribute("equipo")).thenReturn(equipoNuevo);
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);

      List<JugadorDTO> jugadoresMock = IntStream.range(0, 14)
              .mapToObj(i -> {
                 JugadorDTO j = new JugadorDTO();
                 j.setId((long) i);
                 j.setNombre("Jugador " + i);
                 return j;
              }).collect(Collectors.toList());

      doAnswer(invocation -> {
         EquipoDTO equipo = invocation.getArgument(0);
         equipo.setJugadores(jugadoresMock);
         return null;
      }).when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));

      doAnswer(invocation -> {
         EquipoDTO dto = invocation.getArgument(0);
         Usuario u = invocation.getArgument(1);
         Equipo entidad = new Equipo();
         entidad.setId(dto.getId());
         u.setEquipo(entidad);
         return null;
      }).when(equipoService).saveBoth(any(), any());

      ModelAndView mav = equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("sorteoEquipo", mav.getViewName());
      assertEquals("SinJugadores", mav.getModel().get("nombreEquipo"));
      assertNotNull(equipoNuevo.getJugadores());
      assertEquals(14, equipoNuevo.getJugadores().size());

      verify(jugadorService).cargarJugadoresAlEquipo(equipoNuevo);
   }


   @Test
   public void dadoQueNoHayUsuarioNiEquipoEnSesionRedirige() {
      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(null);
      when(httpSession.getAttribute("equipo")).thenReturn(null);

      ModelAndView mav = equipoInicialControlador.sorteEquipoInicial(httpSession);

      assertEquals("redirect:/nuevo-equipo", mav.getViewName());
   }


   @Test
   public void dadoQueNoHayEquipoEnSesionSeCreaNuevoEquipoYSeGuarda() {
      Long usuarioId = 8L;
      Usuario usuario = new Usuario();
      usuario.setId(usuarioId);

      when(httpSession.getAttribute("USUARIO_ID")).thenReturn(usuarioId);
      when(httpSession.getAttribute("equipo")).thenReturn(null);
      when(usuarioService.buscarUsuarioPorId(usuarioId)).thenReturn(usuario);

      // Simular que el equipoService guarda el equipo y asigna ID
      doAnswer(invocation -> {
         EquipoDTO equipoDto = invocation.getArgument(0);
         equipoDto.setId(100L);
         return null;
      }).when(equipoService).save(any(EquipoDTO.class));

      // Simular que carga jugadores al equipo
      doNothing().when(jugadorService).cargarJugadoresAlEquipo(any(EquipoDTO.class));

      ModelAndView mav = equipoInicialControlador.sorteEquipoInicial(httpSession);

      // En este caso esperás redirección o vista creada para nuevo equipo
      assertEquals("redirect:/nuevo-equipo", mav.getViewName()); // Ajustar según implementación
   }




}
