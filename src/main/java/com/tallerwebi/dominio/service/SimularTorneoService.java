package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Partido;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public interface SimularTorneoService {
    void simularFecha(Long torneoId, Long numeroDeFecha, Long idEquipo);
    //Long simularFechaYDevolverPrimerPartido(Long torneoId, Long numeroDeFecha);
    Partido obtenerPartidoSimulado(Long partidoId);
    Fecha obtenerFechaConPartidos(Long torneoId, Long numeroDeFecha);
}
