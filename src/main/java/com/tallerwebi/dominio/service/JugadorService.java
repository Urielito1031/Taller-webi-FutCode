package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;

import java.util.List;

public interface JugadorService{
   List<JugadorDTO>getAll();
   List<JugadorDTO>getAllByEquipoId(Long equipoId);
   void cargarJugadoresAlEquipo(EquipoDTO equipo);
}
