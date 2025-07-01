package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.service.SorteoServiceImpl;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SorteoJugadoresTest {

    private SorteoServiceImpl sorteoService;

    /*@BeforeEach
    public void init(){
        JugadorLoader jugadorLoader = new JugadorLoader();
        this.sorteoService = new SorteoServiceImpl(jugadorLoader);
    }

    @Test
    public void dadoQueTengoCargado100JugadoresDeseoCargarUnEquipoCon13JugadoresRandomDeCategoriaNORMAL() throws Exception {

        List<JugadorDTO> listaDeJugadores = this.sorteoService.sortearEquipoInicial();

        int cantidadDeJugadores = 13;

        assertEquals(cantidadDeJugadores, listaDeJugadores.size()-1);
    }*/

}
