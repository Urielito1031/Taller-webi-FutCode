package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.FechaRepository;
import com.tallerwebi.dominio.repository.PartidoRepository;
import com.tallerwebi.dominio.repository.TorneoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class SimularTorneoServiceImpl implements SimularTorneoService {

    private final PartidoRepository partidoRepository;
    private final TorneoRepository torneoRepository;
    private final FechaRepository fechaRepository;

    public SimularTorneoServiceImpl(PartidoRepository partidoRepository, TorneoRepository torneoRepository, FechaRepository fechaRepository) {
        this.partidoRepository = partidoRepository;
        this.torneoRepository = torneoRepository;
        this.fechaRepository = fechaRepository;
    }


    @Override
    public void simularFecha(Long torneoId, Long numeroDeFecha) {
        Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);

        // SE PUEDE CAMBIAR POR UNA EXCEPTION
        if (torneo == null || torneo.getFechas().isEmpty()) {
            throw new IllegalArgumentException("Torneo no encontrado o sin fechas");
        }

        Fecha fechaASimular = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);

        for (Partido partido : fechaASimular.getPartidos()) {
            int golesLocal = (int) (Math.random() * 5);
            int golesVisitante = (int) (Math.random() * 5);

            // Agrego esta validacion porque no tengo los jugadores cargados en la base de datos
            if(partido.getEquipoLocal().getJugadores() != null && partido.getEquipoVisitante().getJugadores() != null) {
                if(partido.getEquipoLocal().getRatingEquipo() > partido.getEquipoVisitante().getRatingEquipo()){
                    golesLocal++;
                }else if(partido.getEquipoLocal().getRatingEquipo() < partido.getEquipoVisitante().getRatingEquipo()){
                    golesVisitante++;
                }
            }

            partido.setGolesLocal(golesLocal);
            partido.setGolesVisitante(golesVisitante);

            if (golesLocal > golesVisitante) { partido.setResultado(ResultadoPartido.LOCAL_GANA); }
            else if (golesVisitante > golesLocal) { partido.setResultado(ResultadoPartido.VISITANTE_GANA); }
            else { partido.setResultado(ResultadoPartido.EMPATE); }

            partidoRepository.save(partido);
        }

        fechaASimular.setSimulada(true);
        fechaRepository.save(fechaASimular);
    }

    @Override
    public Long simularFechaYDevolverPrimerPartido(Long torneoId, Long numeroDeFecha) {
        simularFecha(torneoId, numeroDeFecha); // Ya lo tenés hecho

        Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);

        if (fecha != null && !fecha.getPartidos().isEmpty()) {
            return fecha.getPartidos().get(0).getId(); // Primer partido simulado
        }

        return null;
    }

    @Override
    public Partido obtenerPartidoSimulado(Long partidoId) {
        return partidoRepository.obtenerPartidoPorId(partidoId);
    }


    @Override
    public Fecha obtenerFechaConPartidos(Long torneoId, Long numeroDeFecha) {
        return fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    }
}
