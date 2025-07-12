package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Narracion;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.FrasesPartidoDTO;
import com.tallerwebi.presentacion.dto.NarracionDTO;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SimularTorneoServiceImpl implements SimularTorneoService {

    private final PartidoRepository partidoRepository;
    private final TorneoRepository torneoRepository;
    private final FechaRepository fechaRepository;
    private final FrasePartidoService frasePartidoService;
    private final NarracionRepository narracionRepository;
    private final JugadorService jugadorService;
    private final Random random = new Random();

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

            // Agrego esta validacion porque no tengo los jugadores cargados en la base de
            // datos
            if (partido.getEquipoLocal().hasJugadores() && partido.getEquipoVisitante().hasJugadores()) {
                if (partido.getEquipoLocal().getRatingEquipo() > partido.getEquipoVisitante().getRatingEquipo()) {
                    golesLocal++;
                } else if (partido.getEquipoLocal().getRatingEquipo() < partido.getEquipoVisitante()
                        .getRatingEquipo()) {
                    golesVisitante++;
                }
            }

            // if(partido.getEquipoLocal().getJugadores() != null &&
            // partido.getEquipoVisitante().getJugadores() != null) {
            // if(partido.getEquipoLocal().getRatingEquipo() >
            // partido.getEquipoVisitante().getRatingEquipo()){
            // golesLocal++;
            // }else if(partido.getEquipoLocal().getRatingEquipo() <
            // partido.getEquipoVisitante().getRatingEquipo()){
            // golesVisitante++;
            // }
            // }

            // Generar y guardar frases de goles

            // Generar frases de goles solo si hay jugadores

            System.out.println("antes de la narracion");
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

            int cantidadEventosGenerales = generarCantidadAleatoria(25);
            int cantidadTarjetas = generarCantidadAleatoria(5);
            int cantidadExpulsados = generarCantidadAleatoria(3);
            int cantidadLesiones = generarCantidadAleatoria(5);
            ;

            // GENERAL
            for (int i = 0; i < cantidadEventosGenerales; i++) {
                try {
                    Long equipoId = randomEquipo(partido);
                    String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GENERAL, equipoId);
                    narracionRepository.guardar(new Narracion(frase, partido));
                } catch (Exception e) {
                    System.err.println("Error generando frase GENERAL: " + e.getMessage());
                }
            }

            // TARJETA_AMARILLA
            for (int i = 0; i < cantidadTarjetas; i++) {
                try {
                    Long equipoId = randomEquipo(partido);
                    String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA,
                            equipoId);
                    narracionRepository.guardar(new Narracion(frase, partido));
                } catch (Exception e) {
                    System.err.println("Error generando frase AMARILLA: " + e.getMessage());
                }
            }

            // TARJETA_ROJA
            for (int i = 0; i < cantidadTarjetas; i++) {
                try {
                    Long equipoId = randomEquipo(partido);
                    String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_ROJA,
                            equipoId);
                    narracionRepository.guardar(new Narracion(frase, partido));
                } catch (Exception e) {
                    System.err.println("Error generando frase ROJA: " + e.getMessage());
                }
            }

            // EXPULSION
            for (int i = 0; i < cantidadExpulsados; i++) {
                try {
                    Long equipoId = randomEquipo(partido);
                    String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.EXPULSION,
                            equipoId);
                    narracionRepository.guardar(new Narracion(frase, partido));
                } catch (Exception e) {
                    System.err.println("Error generando frase EXPULSION: " + e.getMessage());
                }
            }

            // LESION
            for (int i = 0; i < cantidadLesiones; i++) {
                try {
                    Long equipoId = randomEquipo(partido);
                    String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.LESION, equipoId);
                    narracionRepository.guardar(new Narracion(frase, partido));
                } catch (Exception e) {
                    System.err.println("Error generando frase LESION: " + e.getMessage());
                }
            }

            partido.setGolesLocal(golesLocal);
            partido.setGolesVisitante(golesVisitante);

            if (golesLocal > golesVisitante) {
                partido.setResultado(ResultadoPartido.LOCAL_GANA);
            } else if (golesVisitante > golesLocal) {
                partido.setResultado(ResultadoPartido.VISITANTE_GANA);
            } else {
                partido.setResultado(ResultadoPartido.EMPATE);
            }

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

    private int generarCantidadAleatoria(int max) {
        return random.nextInt(max + 1);
    }

    private Long randomEquipo(Partido partido) {
        if (Math.random() < 0.5) {
            return partido.getEquipoLocal().getId();
        } else {
            return partido.getEquipoVisitante().getId();
        }
    }

    @Override
    public List<NarracionDTO> generarNarracionesParaPartido(Partido partido) {
        List<NarracionDTO> narraciones = new ArrayList<>();
        Random random = new Random();
        int minutoMax = 90;

        int golesLocal = partido.getGolesLocal();
        int golesVisitante = partido.getGolesVisitante();

        // Goles local
        for (int i = 0; i < golesLocal; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GOL,
                    partido.getEquipoLocal().getId());
            narraciones.add(new NarracionDTO(frase, minuto, "GOL"));
        }
        // Goles visitante
        for (int i = 0; i < golesVisitante; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GOL,
                    partido.getEquipoVisitante().getId());
            narraciones.add(new NarracionDTO(frase, minuto, "GOL"));
        }
        // Eventos generales
        int cantidadEventosGenerales = generarCantidadAleatoria(10);
        for (int i = 0; i < cantidadEventosGenerales; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GENERAL, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "GENERAL"));
        }
        // Tarjetas amarillas
        int cantidadTarjetas = generarCantidadAleatoria(3);
        for (int i = 0; i < cantidadTarjetas; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA,
                    equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "TARJETA_AMARILLA"));
        }
        // Tarjetas rojas
        int cantidadRojas = generarCantidadAleatoria(1);
        for (int i = 0; i < cantidadRojas; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_ROJA, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "TARJETA_ROJA"));
        }
        // Expulsiones
        int cantidadExpulsados = generarCantidadAleatoria(1);
        for (int i = 0; i < cantidadExpulsados; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.EXPULSION, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "EXPULSION"));
        }
        // Lesiones
        int cantidadLesiones = generarCantidadAleatoria(2);
        for (int i = 0; i < cantidadLesiones; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.LESION, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "LESION"));
        }
        // Ordenar narraciones por minuto ascendente
        narraciones.sort((a, b) -> Integer.compare(a.getMinuto(), b.getMinuto()));
        return narraciones;
    }

}
