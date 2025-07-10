package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.EventoPartido;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.dominio.service.FrasePartidoService;
import com.tallerwebi.dominio.service.FrasePartidoServiceImpl;
import com.tallerwebi.presentacion.dto.FrasesPartidoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FrasePartidoServiceImplTest {

    private JugadorRepository jugadorRepository;
    private EquipoRepository equipoRepository;
    private FrasePartidoServiceImpl frasePartidoService;

    @BeforeEach
    void setUp() {
        jugadorRepository = Mockito.mock(JugadorRepository.class);
        equipoRepository = Mockito.mock(EquipoRepository.class);

        frasePartidoService = new FrasePartidoServiceImpl(jugadorRepository, equipoRepository);
    }

    @Test
    void deberiaObtenerFrasesDeGol() {
        List<String> tipos = frasePartidoService.obtenerTiposEventoDisponibles();

        assertTrue(tipos.contains("GOL"));
        assertTrue(frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.GOL) > 0);

        System.out.println(tipos.toString());
    }

    @Test
    void deberiaGenerarFraseParaJugadorYEquipo() {
        Jugador jugador = new Jugador();
        jugador.setId(1L);
        jugador.setNombre("Messi");

        Equipo equipo = new Equipo();
        equipo.setId(1L);
        equipo.setNombre("Barcelona");

        when(jugadorRepository.getById(1L)).thenReturn(jugador);
        when(equipoRepository.getById(1L)).thenReturn(equipo);

        String frase = frasePartidoService.generarFrase(EventoPartido.GOL, 1L, 1L);

        assertTrue(frase.contains("Messi"));
        assertTrue(frase.contains("Barcelona"));
        System.out.println(frase);
    }

    @Test
    public void deberiaDevolverCantidadDeFrasesPorTipo() {
        int cantidad = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.GOL);
        int cantidad2 = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.EXPULSION);
        int cantidad3 = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.GENERAL);
        int cantidad4 = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.TARJETA_AMARILLA);
        int cantidad5 = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.TARJETA_ROJA);
        int cantidad6 = frasePartidoService.getCantidadFrasesPorTipo(EventoPartido.LESION);

        assertTrue(cantidad > 0);
        assertTrue(cantidad2 > 0);
        assertTrue(cantidad3 > 0);
        assertTrue(cantidad4 > 0);
        assertTrue(cantidad5 > 0);
        assertTrue(cantidad6 > 0);
    }

//    @Test
//    public void dadoQueTengoFrasesDePartidoDeGolVemosQueAmbasSonDiferentes() {
//        Jugador jugador = new Jugador();
//        jugador.setId(1L);
//        jugador.setNombre("Messi");
//
//        Equipo equipo = new Equipo();
//        equipo.setId(1L);
//        equipo.setNombre("Barcelona");
//
//        when(jugadorRepository.getById(1L)).thenReturn(jugador);
//        when(equipoRepository.getById(1L)).thenReturn(equipo);
//
//        String frase = frasePartidoService.generarFrase(EventoPartido.GOL, 1L, 1L);
//        String frase2 = frasePartidoService.generarFrase(EventoPartido.GOL, 1L, 1L);
//
//        // si son iguales seria mucha coincidencia JAJA
//        assertNotEquals(frase, frase2);
//    }

    @Test
    public void deberiaGenerarFraseConJugadorAleatorio() {
        // Arrange
        Jugador jugador = new Jugador();
        jugador.setId(99L);
        jugador.setNombre("Di María");
        Jugador jugador2 = new Jugador();
        jugador2.setId(98L);
        jugador2.setNombre("Messi");
        Jugador jugador3 = new Jugador();
        jugador3.setId(97L);
        jugador3.setNombre("Jorge");
        Jugador jugador4 = new Jugador();
        jugador4.setId(96L);
        jugador4.setNombre("Juanma");

        List<Jugador> jugadores = List.of(jugador, jugador2, jugador3, jugador4);

        when(jugadorRepository.getAllByEquipoId(10L)).thenReturn(jugadores);
        when(jugadorRepository.getById(99L)).thenReturn(jugador);
        when(jugadorRepository.getById(98L)).thenReturn(jugador2);
        when(jugadorRepository.getById(97L)).thenReturn(jugador3);
        when(jugadorRepository.getById(96L)).thenReturn(jugador4);

        Equipo equipo = new Equipo();
        equipo.setId(10L);
        equipo.setNombre("Argentina");
        when(equipoRepository.getById(10L)).thenReturn(equipo);

        // Act
        String frase = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA, 10L);
        String frase2 = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA, 10L);
        String frase3 = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA, 10L);
        String frase4 = frasePartidoService.generarFraseConJugadorAleatorio(EventoPartido.TARJETA_AMARILLA, 10L);

        // Assert
//        assertTrue(frase.contains("Di María"));
//        assertTrue(frase.contains("Argentina"));

        System.out.println(frase);
        System.out.println(frase2);
        System.out.println(frase3);
        System.out.println(frase4);
    }



}
