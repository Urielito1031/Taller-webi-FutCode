package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SobreDTO {
    private TipoSobre tipoSobre;
    private List<JugadorDTO> jugadores;

    private String titulo;
    private Double precio;
    private String descripcion;
    private String imagenUrl;

    public SobreDTO(){};

    public SobreDTO(String titulo, Double precio, TipoSobre tipoSobre, String imagenUrl) {
        this.titulo = titulo;
        this.precio = precio;
        this.tipoSobre = tipoSobre;
        this.imagenUrl = imagenUrl;
    }
}