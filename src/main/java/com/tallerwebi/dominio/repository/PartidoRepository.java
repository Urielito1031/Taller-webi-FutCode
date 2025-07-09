package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Partido;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepository {
    void save(Partido partido);
}
