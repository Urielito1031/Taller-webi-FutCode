package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.model.entities.Narracion;
import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.*;
import com.tallerwebi.presentacion.dto.NarracionDTO;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.presentacion.dto.SimulacionTorneoResumenDTO;

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
    private final UsuarioService usuarioService;
    private final TorneoService torneoService;

    public SimularTorneoServiceImpl(PartidoRepository partidoRepository, TorneoRepository torneoRepository,
            FechaRepository fechaRepository, FrasePartidoService frasePartidoService,
            NarracionRepository narracionRepository, JugadorService jugadorService, UsuarioService usuarioService,
            UsuarioService usuarioService1, TorneoService torneoService) {
        this.partidoRepository = partidoRepository;
        this.torneoRepository = torneoRepository;
        this.fechaRepository = fechaRepository;
        this.frasePartidoService = frasePartidoService;
        this.narracionRepository = narracionRepository;
        this.jugadorService = jugadorService;
        this.usuarioService = usuarioService1;
        this.torneoService = torneoService;
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
                if (partido.getEquipoLocal().getRatingEquipoGeneral() > partido.getEquipoVisitante()
                        .getRatingEquipoGeneral()) {
                    golesLocal++;
                } else if (partido.getEquipoLocal().getRatingEquipoGeneral() < partido.getEquipoVisitante()
                        .getRatingEquipoGeneral()) {
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

            int cantidadEventosGenerales = generarCantidadAleatoria(15, 25);
            int cantidadTarjetas = generarCantidadAleatoria(2, 5);
            int cantidadExpulsados = generarCantidadAleatoria(0, 3);
            int cantidadLesiones = generarCantidadAleatoria(1, 5);


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
            usuarioService.sumarPremioMonedas(partido, partido.getResultado());

        }

        fechaASimular.setSimulada(true);
        fechaRepository.save(fechaASimular);

        // Verificar si todas las fechas están simuladas para cambiar estado a
        // FINALIZADO
        boolean todasLasFechasSimuladas = torneo.getFechas().stream().allMatch(Fecha::isSimulada);
        if (todasLasFechasSimuladas) {
            System.out.println("[SIMULACION] Antes de finalizar: Torneo " + torneo.getNombre() + " | Estado: "
                    + torneo.getEstado());
            torneo.setEstado(EstadoTorneoEnum.FINALIZADO);
            torneoRepository.save(torneo);
            System.out.println("[SIMULACION] Después de finalizar: Torneo " + torneo.getNombre() + " | Estado: "
                    + torneo.getEstado());
        }
    }

    @Override
    public Long simularFechaYDevolverPrimerPartido(Long torneoId, Long numeroDeFecha) {
        simularFecha(torneoId, numeroDeFecha);

        Fecha fecha = fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);

        if (fecha != null && !fecha.getPartidos().isEmpty()) {
            return fecha.getPartidos().get(0).getId();
        }

        return null;
    }

    @Override
    public Partido obtenerPartidoSimulado(Long partidoId) {
        return partidoRepository.obtenerPartidoConRelaciones(partidoId);
    }

    @Override
    public Fecha obtenerFechaConPartidos(Long torneoId, Long numeroDeFecha) {
        return fechaRepository.getFechaByTorneoIdAndNumeroDeFecha(torneoId, numeroDeFecha);
    }

    public int generarCantidadAleatoria(int minimo, int maximo) {
        return minimo + new Random().nextInt(maximo - minimo + 1);
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
        int cantidadEventosGenerales = generarCantidadAleatoria(15, 25);
        for (int i = 0; i < cantidadEventosGenerales; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.GENERAL, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "GENERAL"));
        }
        // Tarjetas amarillas
        int cantidadTarjetas = generarCantidadAleatoria(1, 3);
        for (int i = 0; i < cantidadTarjetas; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA,
                    equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "TARJETA_AMARILLA"));
        }
        // Tarjetas rojas
        int cantidadRojas = generarCantidadAleatoria(1, 3);
        for (int i = 0; i < cantidadRojas; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_ROJA, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "TARJETA_ROJA"));
        }
        // Expulsiones
        int cantidadExpulsados = generarCantidadAleatoria(1, 2);
        for (int i = 0; i < cantidadExpulsados; i++) {
            int minuto = 1 + random.nextInt(minutoMax);
            Long equipoId = randomEquipo(partido);
            String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.EXPULSION, equipoId);
            narraciones.add(new NarracionDTO(frase, minuto, "EXPULSION"));
        }
        // Lesiones
        int cantidadLesiones = generarCantidadAleatoria(1,3);
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

    @Override
    public SimulacionTorneoResumenDTO simularTorneoRapido(Long torneoId, Long usuarioId) {
        Torneo torneo = torneoRepository.obtenerTorneoConFechas(torneoId);
        Usuario usuario = null;
        int puestoFinal = -1;
        double monedasGanadas = 0;
        double monedasTotales = 0;
        String nombreTorneo = torneo.getNombre();

        // Simular fechas pendientes
        for (Fecha fecha : torneo.getFechas()) {
            if (!fecha.isSimulada()) {
                for (Partido partido : fecha.getPartidos()) {
                    jugadorService.asegurarJugadoresIniciales(partido.getEquipoLocal());
                    jugadorService.asegurarJugadoresIniciales(partido.getEquipoVisitante());

                    int golesLocal = (int) (Math.random() * 5);
                    int golesVisitante = (int) (Math.random() * 5);

                    if (partido.getEquipoLocal().hasJugadores() && partido.getEquipoVisitante().hasJugadores()) {
                        if (partido.getEquipoLocal().getRatingEquipoGeneral() > partido.getEquipoVisitante()
                                .getRatingEquipoGeneral()) {
                            golesLocal++;
                        } else if (partido.getEquipoLocal().getRatingEquipoGeneral() < partido.getEquipoVisitante()
                                .getRatingEquipoGeneral()) {
                            golesVisitante++;
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
                    usuarioService.sumarPremioMonedas(partido, partido.getResultado());
                }
                fecha.setSimulada(true);
                fechaRepository.save(fecha);
            }
        }

        // Calcular tabla final
        List<Partido> partidos = torneo.getFechas().stream()
                .flatMap(f -> f.getPartidos().stream())
                .collect(java.util.stream.Collectors.toList());

        // Usar los EquipoTorneo existentes del torneo
        List<EquipoTorneo> tablaAnterior = new java.util.ArrayList<>(torneo.getEquipos());

        // Resetear estadísticas para recalcular
        for (EquipoTorneo et : tablaAnterior) {
            et.setPosicion(0);
            et.setPuntos(0);
            et.setPartidosJugados(0);
            et.setPartidosGanados(0);
            et.setPartidosEmpatados(0);
            et.setPartidosPerdidos(0);
            et.setGolesAFavor(0);
            et.setGolesEnContra(0);
        }

        List<EquipoTorneo> tabla = torneoService.calcularTablaDePosiciones(partidos, tablaAnterior);

        // Buscar el equipo del usuario y su puesto
        for (EquipoTorneo eq : tabla) {
            if (eq.getEquipo() != null && eq.getEquipo().getUsuario() != null
                    && eq.getEquipo().getUsuario().getId().equals(usuarioId)) {
                puestoFinal = eq.getPosicion(); // Usar la posición calculada por el servicio
                usuario = eq.getEquipo().getUsuario();
                break;
            }
        }

        double premioTorneo = torneo.getPremioMonedas() != null ? torneo.getPremioMonedas() : 0.0;
        if (puestoFinal == 1) {
            monedasGanadas = premioTorneo;
        } else if (puestoFinal == 2) {
            monedasGanadas = (int) (premioTorneo * 0.75);
        } else if (puestoFinal == 3) {
            monedasGanadas = (int) (premioTorneo * 0.5);
        } else if (puestoFinal > 0) {
            monedasGanadas = 3000;
        }
        if (usuario != null) {
            usuario.setMonedas(usuario.getMonedas() + monedasGanadas);
            usuarioService.actualizar(usuario);
            monedasTotales = usuario.getMonedas();
        }

        torneo.setEstado(EstadoTorneoEnum.FINALIZADO);
        System.out.println("[SIMULACION] (RAPIDO) Antes de guardar: Torneo " + torneo.getNombre() + " | Estado: "
                + torneo.getEstado());
        torneoRepository.save(torneo);
        System.out.println("[SIMULACION] (RAPIDO) Después de guardar: Torneo " + torneo.getNombre() + " | Estado: "
                + torneo.getEstado());

        return new SimulacionTorneoResumenDTO(puestoFinal, monedasGanadas, monedasTotales, nombreTorneo);
    }
}
