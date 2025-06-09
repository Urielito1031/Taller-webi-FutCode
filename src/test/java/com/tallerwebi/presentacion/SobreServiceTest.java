package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SobreServiceTest {
    private JugadorLoader jugadorLoader;
    private SobreServiceImpl sobreService;

    @BeforeEach
    public void setUp() {
        this.jugadorLoader = Mockito.mock(JugadorLoader.class);

        //eliminar jugador loader?
        this.sobreService = new SobreServiceImpl(jugadorLoader);
    }

    @Test
    public void dadoQueCreoUnSobreObtengoUnSobreNoNulo() {
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
    }

    @Test
    public void obtener5JugadoresRandomDeRarezaNormal(){
        List<JugadorDTO> jugadoresMock = List.of(
                new JugadorDTO("Jugador 1", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 2", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 3", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 4", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 5", RarezaJugador.NORMAL)
        );

        Mockito.when(jugadorLoader.cargarJugadoresDesdeJSON()).thenReturn(jugadoresMock);

//        List<JugadorDTO> resultado = sobreService.obtenerJugadoresRandomPorRareza(RarezaJugador.NORMAL, 5);
//
//        assertThat(resultado.get(0).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
//        assertThat(resultado.get(4).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
//        assertThat(resultado, hasSize(5));
    }

    @Test
    public void dadoQueCreoUnSobreDeTipoBronceObtengoUnSobreConCincoJugadoresDeRarezaNormal(){
        List<JugadorDTO> jugadoresMock = List.of(
                new JugadorDTO("Jugador 1", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 2", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 3", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 4", RarezaJugador.NORMAL),
                new JugadorDTO("Jugador 5", RarezaJugador.NORMAL)
        );

        Mockito.when(jugadorLoader.cargarJugadoresDesdeJSON()).thenReturn(jugadoresMock);

        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.NORMAL), is(true));
    }





}
