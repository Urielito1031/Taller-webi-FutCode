package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SorteoServiceImpl {

    private final JugadorLoader jugadorLoader;

    public SorteoServiceImpl(JugadorLoader jugadorLoader) {
        this.jugadorLoader = jugadorLoader;
    }

    public List<JugadorDTO> sortearEquipoInicial(){

        // traemos los jugadores del JSON como objetos
        List<JugadorDTO> todosLosJugadores = this.jugadorLoader.cargarJugadoresDesdeJSON();

        // seleccionamos solo a los jugadores de rareza NORMAL
        List<JugadorDTO> jugadoresDisponibles = todosLosJugadores.stream()
                .filter(j -> j.getRarezaJugador().equals(RarezaJugador.NORMAL))
                .collect(Collectors.toList());

        // mezclamos la lista
        Collections.shuffle(jugadoresDisponibles);

        // nuevo equipo
        List<JugadorDTO> nuevoEquipo = new ArrayList<>();

        // agregamos mediante el metodo de seleccion de jugador al nuevo equipo
        nuevoEquipo.addAll(seleccionarJugadores(jugadoresDisponibles, PosicionEnum.ARQUERO, 2));
        nuevoEquipo.addAll(seleccionarJugadores(jugadoresDisponibles, PosicionEnum.DEFENSOR, 5));
        nuevoEquipo.addAll(seleccionarJugadores(jugadoresDisponibles, PosicionEnum.MEDIOCAMPISTA, 3));
        nuevoEquipo.addAll(seleccionarJugadores(jugadoresDisponibles, PosicionEnum.DELANTERO, 4));

        return nuevoEquipo;
    }

    private List<JugadorDTO> seleccionarJugadores(List<JugadorDTO> futurosJugadores, PosicionEnum posicion, int cantidad){
        return futurosJugadores.stream()
                .filter(j -> j.getPosicionNatural().contains(posicion))
                .limit(cantidad)
                .collect(Collectors.toList());
    }
}