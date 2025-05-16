package com.tallerwebi.presentacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class TorneoDTO {
    private Long id;
    private String nombre;
    private List<PartidoDTO> partidos;
}
