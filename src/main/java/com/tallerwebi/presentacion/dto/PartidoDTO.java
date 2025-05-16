package com.tallerwebi.presentacion.dto;

import lombok.Data;

@Data
public class PartidoDTO {
    private String equipoLocal;
    private String escudoLocal;
    private Integer golesLocal;

    private String equipoVisitante;
    private String escudoVisitante;
    private Integer golesVisitante;

    private Integer numeroFecha;
    private boolean jugado;
}
