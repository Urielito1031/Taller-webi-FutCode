package com.tallerwebi.presentacion.dto;

import lombok.Data;

import java.util.List;

@Data
public class TablaGeneralDTO {
    private String nombreTorneo;
    private List<EquipoTorneoDTO> equipos;
}
