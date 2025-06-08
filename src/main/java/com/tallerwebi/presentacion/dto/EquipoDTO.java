package com.tallerwebi.presentacion.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class EquipoDTO {
    private Long id;
    private String nombre;
    private ClubDTO club;
    private List<JugadorDTO> jugadores;
    private FormacionDTO formacionActual;
    private Double ratingEquipo;
    public EquipoDTO() {}


    public EquipoDTO(String nombre, ClubDTO club) {
        this.nombre = nombre;
        this.club = club;
        this.formacionActual = new FormacionDTO();
        this.jugadores = new ArrayList<>();
    }

    public Double ratingEquipo(List<JugadorDTO> jugadores){
        double total = 0;

        for (JugadorDTO j : jugadores) {
            total += j.getRating();
        }
        return total/jugadores.size();
    }

}
