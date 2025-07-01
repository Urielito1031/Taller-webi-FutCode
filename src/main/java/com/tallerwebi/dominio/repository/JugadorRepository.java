package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;

import java.util.List;

public interface JugadorRepository{

   List<Jugador> getAll();
   List<Jugador> sortearJugadoresIniciales(RarezaJugador rarezaJugador, int cantidad);
   List<Jugador>getAllByEquipoId(Long equipoId);
   Jugador getById(Long id);
   Jugador save(Jugador jugador);
}
