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
    private Double ratingEquipo;

    private EsquemaDTO formacionActual;
    public EquipoDTO() {}


    public EquipoDTO(String nombre, ClubDTO club) {
        this.nombre = nombre;
        this.club = club;
        this.jugadores = new ArrayList<>();
        this.formacionActual = new EsquemaDTO();
    }

    public Double ratingEquipo(List<JugadorDTO> jugadores){
        double total = 0;

        for (JugadorDTO j : jugadores) {
            total += j.getRating();
        }
        return total/jugadores.size();
    }


    @Override
    public String toString() {
        return "EquipoDTO{" +
          "id=" + (id != null ? id : "null") +
          ", nombre='" + (nombre != null ? nombre : "null") + '\'' +
          ", club=" + (club != null ? club : "null") +
          ", jugadores=" + (jugadores != null ? jugadores : "null") +
          ", ratingEquipo=" + (ratingEquipo != null ? ratingEquipo : "null") +
          ", formacionActual=" + (formacionActual != null ? formacionActual : "null") +
                          '}';
    }

//todavia no tiene logica
    public Equipo convertToEntity(EquipoDTO equipo){
   return null;
    }

    public EquipoDTO convertFromEntity(Equipo equipo){
    return null;}
}
