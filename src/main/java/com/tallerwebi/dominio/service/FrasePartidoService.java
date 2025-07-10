package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.EventoPartido;

import java.util.List;

public interface FrasePartidoService {

    String generarFrase(EventoPartido tipoEvento, Long jugadorId, Long equipoId);

    String generarLaFrase(EventoPartido tipoEvento, Jugador jugador, Equipo equipo);

    String generarFraseConJugadorAleatorio(EventoPartido tipoEvento, Long equipoId);

    List<String> obtenerTiposEventoDisponibles();

    int getCantidadFrasesPorTipo(EventoPartido tipoEvento);
}
