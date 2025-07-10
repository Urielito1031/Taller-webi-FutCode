package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Narracion;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.FrasesPartidoDTO;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SimularTorneoServiceImpl implements SimularTorneoService {

    private final PartidoRepository partidoRepository;
    private final TorneoRepository torneoRepository;
    private final FechaRepository fechaRepository;
    private final FrasePartidoService frasePartidoService;
    private final NarracionRepository narracionRepository;
    private final JugadorService jugadorService;

    public SimularTorneoServiceImpl(PartidoRepository partidoRepository, TorneoRepository torneoRepository,
                                    FechaRepository fechaRepository, FrasePartidoService frasePartidoService,
                                    NarracionRepository narracionRepository, JugadorService jugadorService) {
        this.partidoRepository = partidoRepository;
        this.torneoRepository = torneoRepository;
        this.fechaRepository = fechaRepository;
        this.frasePartidoService = frasePartidoService;
        this.narracionRepository = narracionRepository;
        this.jugadorService = jugadorService;
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
            jugadorService.asegurarJugadoresIniciales(partido.getEquipoLocal());
            jugadorService.asegurarJugadoresIniciales(partido.getEquipoVisitante());

            int golesLocal = (int) (Math.random() * 5);
            int golesVisitante = (int) (Math.random() * 5);

            // Agrego esta validacion porque no tengo los jugadores cargados en la base de datos
            if(partido.getEquipoLocal().hasJugadores()  && partido.getEquipoVisitante().hasJugadores()) {
                if(partido.getEquipoLocal().getRatingEquipo() > partido.getEquipoVisitante().getRatingEquipo()){
                    golesLocal++;
                }else if(partido.getEquipoLocal().getRatingEquipo() < partido.getEquipoVisitante().getRatingEquipo()){
                    golesVisitante++;
                }
            }

//            if(partido.getEquipoLocal().getJugadores() != null && partido.getEquipoVisitante().getJugadores() != null) {
//                if(partido.getEquipoLocal().getRatingEquipo() > partido.getEquipoVisitante().getRatingEquipo()){
//                    golesLocal++;
//                }else if(partido.getEquipoLocal().getRatingEquipo() < partido.getEquipoVisitante().getRatingEquipo()){
//                    golesVisitante++;
//                }
//            }

            // Generar y guardar frases de goles

            // Generar frases de goles solo si hay jugadores
            if (partido.getEquipoLocal().hasJugadores()) {
                for (int i = 0; i < golesLocal; i++) {
                    try {
                        String frase = frasePartidoService.generarFraseConJugadorAleatorio(
                                EventoPartido.GOL, partido.getEquipoLocal().getId());
                        narracionRepository.guardar(new Narracion(frase, partido));
                    } catch (Exception e) {
                        // Log error pero continúa la simulación
                        System.err.println("Error generando frase para equipo local: " + e.getMessage());
                    }
                }
            }

            if (partido.getEquipoVisitante().hasJugadores()) {
                for (int i = 0; i < golesVisitante; i++) {
                    try {
                        String frase = frasePartidoService.generarFraseConJugadorAleatorio(
                                EventoPartido.GOL, partido.getEquipoVisitante().getId());
                        narracionRepository.guardar(new Narracion(frase, partido));
                    } catch (Exception e) {
                        // Log error pero continúa la simulación
                        System.err.println("Error generando frase para equipo visitante: " + e.getMessage());
                    }
                }
            }

//            for (int i = 0; i < golesLocal; i++) {
//                String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GOL, partido.getEquipoLocal().getId());
//                narracionRepository.guardar(new Narracion(frase, partido));
//            }
//
//            for (int i = 0; i < golesVisitante; i++) {
//                String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GOL, partido.getEquipoVisitante().getId());
//                narracionRepository.guardar(new Narracion(frase, partido));
//            }

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
