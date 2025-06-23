package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SobreRepository {
    List<Jugador> getJugadoresPorRareza(RarezaJugador rareza, int cantidad);
}
