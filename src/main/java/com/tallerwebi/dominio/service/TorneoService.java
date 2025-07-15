package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import com.tallerwebi.presentacion.dto.CrearTorneoDTO;

import java.util.List;

public interface TorneoService {
   List<TorneoDTO> getAll();

   TorneoDTO getById(Long id);

   Torneo obtenerTorneoConFechas(Long torneoId);

   List<EquipoTorneo> calcularTablaDePosiciones(List<Partido> partidos, List<EquipoTorneo> tablaAnterior);

   List<Fecha> generarFechas(List<Equipo> equipos, Torneo torneo);

   void crearFixtureConLasFechas(Long torneoId);

   void crearTorneoPersonalizado(CrearTorneoDTO crearTorneoDTO);
}
