package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Equipo;
import lombok.Getter;
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

    private EsquemaDTO formacionActual;
    public EquipoDTO() {}

    public EquipoDTO(String nombre, ClubDTO club) {
        this.nombre = nombre;
        this.club = club;
        this.formacionActual = new EsquemaDTO();
        this.jugadores = new ArrayList<>();
    }

    public Equipo convertToEntity(EquipoDTO equipo){
   return null;
    }

    public EquipoDTO convertFromEntity(Equipo equipo){
    return null;}
}
