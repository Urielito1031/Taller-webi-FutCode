package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Jugador;

import java.util.List;

public interface JugadorRepository{

   List<Jugador> getAll();

   List<Jugador>getAllByEquipoId(Long equipoId);
   Jugador getById(Long id);
   Jugador save(Jugador jugador);
}
