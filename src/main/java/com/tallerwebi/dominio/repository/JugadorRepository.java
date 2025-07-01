package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;

import java.util.List;

public interface JugadorRepository{

   List<Jugador> getAll();
   List<Jugador> sortearJugadoresIniciales(RarezaJugador rarezaJugador, PosicionEnum posicion, int cantidad);
   List<Jugador>getAllByEquipoId(Long equipoId);
   Jugador getById(Long id);
   Jugador save(Jugador jugador);
}
