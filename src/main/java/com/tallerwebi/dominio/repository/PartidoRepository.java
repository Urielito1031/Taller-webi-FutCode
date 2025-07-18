package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Partido;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartidoRepository {
    void save(Partido partido);

    List<Partido> obtenerPartidosPorTorneoId(Long idTorneo);

    Partido obtenerPartidoPorId(Long id);

    Partido obtenerPartidoConRelaciones(Long id);

    List<Partido> obtenerPartidosPorEquipoId(Long idEquipo);

    List<Partido> obtenerPartidosJugadosPorEquipoId(Long idEquipo);
}
