package com.tallerwebi.presentacion.dto;

import lombok.Data;

@Data


public class EquipoTorneoDTO {

    private Long id;
    private int posicion;
    private String nombre;
    private String nombreImagen;
    private int partidosJugados;
    private int partidosGanados;
    private int partidosEmpatados;
    private int partidosPerdidos;
    private int golesAFavor;
    private int golesEnContra;
    private int puntos;

    public EquipoTorneoDTO(int posicion, String nombre, String nombreImagen, int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos, int golesAFavor, int golesEnContra, int puntos) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.nombreImagen = nombreImagen;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.partidosEmpatados = partidosEmpatados;
        this.partidosPerdidos = partidosPerdidos;
        this.golesAFavor = golesAFavor;
        this.golesEnContra = golesEnContra;
        this.puntos = puntos;
    }

    public EquipoTorneoDTO() {

    }

    public int getDg() {
        return golesAFavor - golesEnContra;
    }
}
