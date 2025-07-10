package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.SobreServiceImpl;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.repository.SobreRepository;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;

public class SobreServiceTest {
    private SobreRepository sobreRepository;
    private SobreServiceImpl sobreService;

    @BeforeEach
    public void setUp() {
        this.sobreRepository = Mockito.mock(SobreRepository.class);
        this.sobreService = new SobreServiceImpl(sobreRepository);
    }

    @Test
    public void dadoQueCreoUnSobre_cuandoLoCreoConTipoBronce_entoncesObtengoUnSobreNoNuloYDelTipoCorrecto() {
        // GIVEN (ya mockeado en beforeEach)

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
    }

    @Test
    public void dadoQueExistenJugadoresDeRarezaNormal_cuandoObtengo5JugadoresRandomDeRarezaNormal_entoncesObtengoUnaListaDe5JugadoresNormales() {
        // GIVEN
        List<Jugador> jugadoresMock = List.of(
          new Jugador("Jugador 1", RarezaJugador.NORMAL),
          new Jugador("Jugador 2", RarezaJugador.NORMAL),
          new Jugador("Jugador 3", RarezaJugador.NORMAL),
          new Jugador("Jugador 4", RarezaJugador.NORMAL),
          new Jugador("Jugador 5", RarezaJugador.NORMAL)
        );
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.NORMAL, 5)).thenReturn(jugadoresMock);

        // WHEN
        List<JugadorDTO> resultado = sobreService.obtenerJugadoresRandomPorRareza(RarezaJugador.NORMAL, 5);

        // THEN
        assertThat(resultado, notNullValue());
        assertThat(resultado, hasSize(5));
        assertThat(resultado.get(0).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
        assertThat(resultado.get(4).getRarezaJugador(), equalTo(RarezaJugador.NORMAL));
        // Verificamos que todos los jugadores en la lista son de rareza NORMAL
        assertThat(resultado.stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.NORMAL), is(true));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.NORMAL, 5);
    }

    @Test
    public void dadoQueExistenJugadoresDeRarezaNormal_cuandoCreoUnSobreDeTipoBronce_entoncesObtengoUnSobreConCincoJugadoresDeRarezaNormal() {
        // GIVEN
        List<Jugador> jugadoresMock = List.of(
          new Jugador("Jugador 1", RarezaJugador.NORMAL),
          new Jugador("Jugador 2", RarezaJugador.NORMAL),
          new Jugador("Jugador 3", RarezaJugador.NORMAL),
          new Jugador("Jugador 4", RarezaJugador.NORMAL),
          new Jugador("Jugador 5", RarezaJugador.NORMAL)
        );
        // Mockeamos la llamada que hace el servicio a su repositorio para obtener jugadores
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.NORMAL, 5)).thenReturn(jugadoresMock);

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.NORMAL), is(true));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.NORMAL, 5);
    }

    // --- Nuevos Tests Representativos ---

    @Test
    public void dadoQueExistenJugadoresDeRarezaRara_cuandoCreoUnSobreDeTipoPlata_entoncesObtengoUnSobreConCincoJugadoresDeRarezaRara() {
        // GIVEN
        List<Jugador> jugadoresRarosMock = List.of(
          new Jugador("Jugador Raro 1", RarezaJugador.RARO),
          new Jugador("Jugador Raro 2", RarezaJugador.RARO),
          new Jugador("Jugador Raro 3", RarezaJugador.RARO),
          new Jugador("Jugador Raro 4", RarezaJugador.RARO),
          new Jugador("Jugador Raro 5", RarezaJugador.RARO)
        );
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.RARO, 5)).thenReturn(jugadoresRarosMock);

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.PLATA);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.PLATA));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.RARO), is(true));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.RARO, 5);
    }

    @Test
    public void dadoQueExistenJugadoresDeRarezaEpica_cuandoCreoUnSobreDeTipoOro_entoncesObtengoUnSobreConCincoJugadoresDeRarezaEpica() {
        // GIVEN
        List<Jugador> jugadoresEpicosMock = List.of(
          new Jugador("Jugador Épico 1", RarezaJugador.EPICO),
          new Jugador("Jugador Épico 2", RarezaJugador.EPICO),
          new Jugador("Jugador Épico 3", RarezaJugador.EPICO),
          new Jugador("Jugador Épico 4", RarezaJugador.EPICO),
          new Jugador("Jugador Épico 5", RarezaJugador.EPICO)
        );
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.EPICO, 5)).thenReturn(jugadoresEpicosMock);

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.ORO);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.ORO));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.EPICO), is(true));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.EPICO, 5);
    }

    @Test
    public void dadoQueExistenJugadoresDeRarezaLeyenda_cuandoCreoUnSobreDeTipoEspecial_entoncesObtengoUnSobreConCincoJugadoresDeRarezaLeyenda() {
        // GIVEN
        List<Jugador> jugadoresLeyendaMock = List.of(
          new Jugador("Jugador Leyenda 1", RarezaJugador.LEYENDA),
          new Jugador("Jugador Leyenda 2", RarezaJugador.LEYENDA),
          new Jugador("Jugador Leyenda 3", RarezaJugador.LEYENDA),
          new Jugador("Jugador Leyenda 4", RarezaJugador.LEYENDA),
          new Jugador("Jugador Leyenda 5", RarezaJugador.LEYENDA)
        );
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.LEYENDA, 5)).thenReturn(jugadoresLeyendaMock);

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.ESPECIAL);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.ESPECIAL));
        assertThat(sobre.getJugadores(), hasSize(5));
        assertThat(sobre.getJugadores().stream().allMatch(j -> j.getRarezaJugador() == RarezaJugador.LEYENDA), is(true));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.LEYENDA, 5);
    }

    @Test
    public void dadoQueNoHayJugadoresDeUnaRarezaEspecifica_cuandoIntentoCrearUnSobre_entoncesObtengoUnSobreConListaDeJugadoresVacia() {
        // GIVEN
        // Simular que el repositorio devuelve una lista vacía para Rareza.NORMAL
        Mockito.when(sobreRepository.getJugadoresPorRareza(RarezaJugador.NORMAL, 5)).thenReturn(new ArrayList<>());

        // WHEN
        SobreDTO sobre = this.sobreService.crearSobre(TipoSobre.BRONCE);

        // THEN
        assertThat(sobre, notNullValue());
        assertThat(sobre.getTipoSobre(), equalTo(TipoSobre.BRONCE));
        assertThat(sobre.getJugadores(), is(empty())); // Esperamos una lista de jugadores vacía
        assertThat(sobre.getJugadores(), hasSize(0));
        Mockito.verify(sobreRepository, Mockito.times(1)).getJugadoresPorRareza(RarezaJugador.NORMAL, 5);
    }

    @Test
    public void dadoQueSeSolicitanSobresDTO_cuandoSeObtienen_entoncesRetornaLaListaCorrectaDeSobresPredefinidos() {
        // GIVEN (No se necesita mockear el repositorio, ya que el método no lo usa)

        // WHEN
        List<SobreDTO> sobresDTO = sobreService.obtenerSobresDTO();

        // THEN
        assertThat(sobresDTO, notNullValue());
        assertThat(sobresDTO, hasSize(4)); // Hay 4 tipos de sobres definidos
        assertThat(sobresDTO.get(0).getTipoSobre(), equalTo(TipoSobre.BRONCE));
        assertThat(sobresDTO.get(1).getTipoSobre(), equalTo(TipoSobre.PLATA));
        assertThat(sobresDTO.get(2).getTipoSobre(), equalTo(TipoSobre.ORO));
        assertThat(sobresDTO.get(3).getTipoSobre(), equalTo(TipoSobre.ESPECIAL));
        assertThat(sobresDTO.get(0).getPrecio(), equalTo(2500.0));
        assertThat(sobresDTO.get(1).getPrecio(), equalTo(5000.0));
        assertThat(sobresDTO.get(2).getPrecio(), equalTo(7500.0));
        assertThat(sobresDTO.get(3).getPrecio(), equalTo(10000.0));
    }
}