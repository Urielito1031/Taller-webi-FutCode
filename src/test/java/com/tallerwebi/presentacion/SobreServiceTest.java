package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.repository.SobreRepository;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SobreServiceTest {
    private SobreRepository sobreRepository;
    private SobreServiceImpl sobreService;

    @BeforeEach
    public void setUp() {
        this.sobreRepository = Mockito.mock(SobreRepository.class);
        this.sobreService = new SobreServiceImpl(sobreRepository);
    }

    @Test
    public void dadoQueCreoUnSobreObtengoUnSobreNoNulo() {
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
    }

    @Test
    public void obtener5JugadoresRandomDeRarezaNormal(){
        List<Jugador> jugadoresMock = List.of(
                new Jugador("Jugador 1", RarezaJugador.NORMAL),
                new Jugador("Jugador 2", RarezaJugador.NORMAL),
                new Jugador("Jugador 3", RarezaJugador.NORMAL),
                new Jugador("Jugador 4", RarezaJugador.NORMAL),
                new Jugador("Jugador 5", RarezaJugador.NORMAL)
        );

        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.NORMAL, 5)).thenReturn(jugadoresMock);

        List<JugadorDTO> resultado = sobreService.obtenerJugadoresRandomPorRareza(RarezaJugador.NORMAL, 5);

        assertThat(resultado.get(0).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
        assertThat(resultado.get(4).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
        assertThat(resultado, hasSize(5));
    }

    @Test
    public void dadoQueCreoUnSobreDeTipoBronceObtengoUnSobreConCincoJugadoresDeRarezaNormal(){
        List<Jugador> jugadoresMock = List.of(
                new Jugador("Jugador 1", RarezaJugador.NORMAL),
                new Jugador("Jugador 2", RarezaJugador.NORMAL),
                new Jugador("Jugador 3", RarezaJugador.NORMAL),
                new Jugador("Jugador 4", RarezaJugador.NORMAL),
                new Jugador("Jugador 5", RarezaJugador.NORMAL)
        );

        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.NORMAL, 5)).thenReturn(jugadoresMock);

        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.NORMAL), is(true));
    }
}