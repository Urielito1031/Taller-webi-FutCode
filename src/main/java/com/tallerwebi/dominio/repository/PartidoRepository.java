package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Partido;

import java.util.List;

public interface PartidoRepository{

   List<Partido> obtenerPartidosPorTorneoId(Long idTorneo);

}
