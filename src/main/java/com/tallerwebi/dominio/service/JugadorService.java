package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;

import java.util.List;

public interface JugadorService{
   List<JugadorDTO>getAll();
   List<JugadorDTO>getAllByEquipoId(Long equipoId);
   List<Jugador> sortearJugadoresIniciales(int cantidad);
   void cargarJugadoresAlEquipo(EquipoDTO equipo);
   void asegurarJugadoresIniciales(Equipo equipo);
   void agregarJugadoresDelSobreAlEquipo(Long equipoId, List<Jugador> jugadores);
}
