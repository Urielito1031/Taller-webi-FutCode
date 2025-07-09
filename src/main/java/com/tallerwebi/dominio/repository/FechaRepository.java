package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.Fecha;
import org.springframework.stereotype.Repository;

@Repository
public interface FechaRepository {
    void save(Fecha fecha);

    Fecha getFechaByTorneoIdAndNumeroDeFecha(Long torneoId, Long numeroDeFecha);
}
