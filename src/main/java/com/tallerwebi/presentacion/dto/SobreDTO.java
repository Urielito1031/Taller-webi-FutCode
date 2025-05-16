package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.TipoSobre;
import lombok.Data;

import java.util.List;

@Data
public class SobreDTO {
    private List<JugadorDTO> jugadores;
    private TipoSobre tipoSobre;
}
