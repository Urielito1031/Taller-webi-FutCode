package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.service.SorteoServiceImpl;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SorteoJugadoresTest {

    private SorteoServiceImpl sorteoService;

    @BeforeEach
    public void init(){
        JugadorLoader jugadorLoader = new JugadorLoader();
        this.sorteoService = new SorteoServiceImpl(jugadorLoader);
    }

    @Test
    public void dadoQueTengoCargado100JugadoresDeseoCargarUnEquipoCon14JugadoresRandomDeCategoriaNORMAL() throws Exception {

        List<JugadorDTO> listaDeJugadores = this.sorteoService.sortearEquipoInicial();

        int cantidadDeJugadores = 13;

        assertEquals(cantidadDeJugadores, listaDeJugadores.size());
    }

}
