package com.tallerwebi.dominio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.presentacion.dto.FrasesPartidoDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FrasePartidoServiceImpl implements FrasePartidoService {

    private final FrasesPartidoDTO frasesPartido;
    private final JugadorRepository jugadorRepository;
    private final EquipoRepository equipoRepository;
    private final Random random = new Random();


    public FrasePartidoServiceImpl(JugadorRepository jugadorRepository, EquipoRepository equipoRepository) {
        this.frasesPartido = cargarFrasesDesdeJson();
        this.jugadorRepository = jugadorRepository;
        this.equipoRepository = equipoRepository;
    }

    private FrasesPartidoDTO cargarFrasesDesdeJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = getClass().getResourceAsStream("/frasesEventoPartido.json");

            if (inputStream == null) {
                throw new RuntimeException("No se encontró el archivo frasesEventopartido.json");
            }

            return mapper.readValue(inputStream, FrasesPartidoDTO.class);

        } catch (IOException e) {
            throw new RuntimeException("Error al cargar frases desde JSON", e);
        }
    }

    @Override
    public String generarFrase(EventoPartido tipoEvento, Long jugadorId, Long equipoId) {
        // Obtener datos del jugador y equipo
        Jugador jugador = jugadorRepository.getById(jugadorId);
        if(jugador == null){
            throw new RuntimeException("Jugador no encontrado");
        }

        Equipo equipo = equipoRepository.getById(equipoId);
        if(equipo == null){
            throw new RuntimeException("Equipo no encontrado");
        }

        return generarLaFrase(tipoEvento, jugador, equipo);
    }

    @Override
    public String generarLaFrase(EventoPartido tipoEvento, Jugador jugador, Equipo equipo) {
        List<String> plantillas = frasesPartido.getFrasesPorTipo(tipoEvento);

        if (plantillas.isEmpty()) {
            return String.format("Evento %s - %s (%s)",
                    tipoEvento.name(), jugador.getNombre(), equipo.getNombre());
        }

        // Seleccionar plantilla aleatoria
        String plantillaSeleccionada = plantillas.get(random.nextInt(plantillas.size()));

        // Reemplazar placeholders
        return plantillaSeleccionada
                .replace("{jugador}", jugador.getNombre())
                .replace("{equipo}", equipo.getNombre());
    }

    @Override
    public String generarFraseConJugadorAleatorio(EventoPartido tipoEvento, Long equipoId) {
        List<Jugador> jugadores = jugadorRepository.getAllByEquipoId(equipoId);

        if (jugadores.isEmpty()) {
            throw new IllegalStateException("No hay jugadores activos para el equipo: " + equipoId);
        }

        Jugador jugadorAleatorio = jugadores.get(random.nextInt(jugadores.size()));

        return generarFrase(tipoEvento, jugadorAleatorio.getId(), equipoId);
    }

    @Override
    public List<String> obtenerTiposEventoDisponibles() {
        return new ArrayList<>(frasesPartido.getFrases().keySet());
    }

    @Override
    public int getCantidadFrasesPorTipo(EventoPartido tipoEvento) {
        return frasesPartido.getFrasesPorTipo(tipoEvento).size();
    }

    }
