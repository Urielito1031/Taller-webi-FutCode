package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.factory.SobreFactory;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class SobreDTO {
    private Long id;
    private TipoSobre tipoSobre;
    private List<JugadorDTO> jugadores;

    private String titulo;
    private Double precio;
    private String descripcion;
    private String imagenUrl;

    public SobreDTO(){
        this.jugadores = new ArrayList<>();
    }

    public SobreDTO(String titulo, Double precio, TipoSobre tipoSobre, String imagenUrl) {
        this.titulo = titulo;
        this.precio = precio;
        this.tipoSobre = tipoSobre;
        this.imagenUrl = imagenUrl;
        this.jugadores = new ArrayList<>();
    }

    public SobreDTO(TipoSobre tipoSobre) {
        this.tipoSobre = tipoSobre;
        this.jugadores = new ArrayList<>();
    }

    public SobreDTO(String titulo, Double precio, TipoSobre tipoSobre, String imagen, List<Jugador> jugadores) {
        this.titulo = titulo;
        this.precio = precio;
        this.tipoSobre = tipoSobre;
        this.imagenUrl = imagen;
        this.jugadores = new ArrayList<>();

    }

    public Sobre fromEntity(){
        Sobre sobre = SobreFactory.crearSobre(tipoSobre);

        sobre.setTitulo(titulo);
//        sobre.setTipoSobre(tipoSobre);
        sobre.setPrecio(precio);
        sobre.setDescripcion(descripcion);
        sobre.setImagenUrl(imagenUrl);
        System.out.println(sobre);
        return sobre;
    }


}