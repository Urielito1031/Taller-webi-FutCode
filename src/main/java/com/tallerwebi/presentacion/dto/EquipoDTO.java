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
    public EquipoDTO() {}


    public EquipoDTO(String nombre, ClubDTO club) {
        this.nombre = nombre;
        this.club = club;
        this.formacionActual = new FormacionDTO();
        this.jugadores = new ArrayList<>();
    }
}
