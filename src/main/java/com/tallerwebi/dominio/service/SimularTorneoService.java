package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Partido;
import org.springframework.stereotype.Service;

@Service
public interface SimularTorneoService {
    void simularFecha(Long torneoId, Long numeroDeFecha);
    Long simularFechaYDevolverPrimerPartido(Long torneoId, Long numeroDeFecha);
    Partido obtenerPartidoSimulado(Long partidoId);
}
