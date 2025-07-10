package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.PartidoHistorialDTO;

import java.util.List;

public interface PartidoService{
      void save(Partido partido);

      List<PartidoDTO> obtenerPartidosPorTorneoId(Long idTorneo);

      List<PartidoDTO> obtenerPartidosPorEquipoId(Long idEquipo);

      List<PartidoHistorialDTO> obtenerPartidosJugadosPorEquipoId(Long idEquipo);

}
