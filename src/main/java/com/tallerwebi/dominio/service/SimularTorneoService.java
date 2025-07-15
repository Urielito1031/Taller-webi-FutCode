package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.presentacion.dto.NarracionDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import com.tallerwebi.presentacion.dto.SimulacionTorneoResumenDTO;

@Service
public interface SimularTorneoService {
    void simularFecha(Long torneoId, Long numeroDeFecha);

    Long simularFechaYDevolverPrimerPartido(Long torneoId, Long numeroDeFecha);

    Partido obtenerPartidoSimulado(Long partidoId);

    Fecha obtenerFechaConPartidos(Long torneoId, Long numeroDeFecha);

    List<NarracionDTO> generarNarracionesParaPartido(Partido partido);

    SimulacionTorneoResumenDTO simularTorneoRapido(Long torneoId, Long usuarioId);
}
