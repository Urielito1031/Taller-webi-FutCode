package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Esquema;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class EquipoDTO {
   private Long id;
   @NotBlank(message = "El nombre del equipo no puede estar vacío")
   private String nombre;
   private ClubDTO club;
   private List<JugadorDTO> jugadores;
   private Double ratingEquipo;
   private Long usuarioId;
   private EsquemaDTO formacionActual;



   public EquipoDTO() {
      this.jugadores = new ArrayList<>();
      this.formacionActual = new EsquemaDTO();
      this.formacionActual.setEsquema(FormacionEsquema.CUATRO_CUATRO_DOS);
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

   public Equipo convertToEntity(EquipoDTO dto) {
      if (dto == null) {
         return null;
      }

      Equipo equipo = new Equipo();
      equipo.setId(dto.getId());
      equipo.setNombre(dto.getNombre());

      if (dto.getClub() != null) {
         equipo.setClub(dto.getClub().convertToEntity(dto.getClub()));
      }

      if (dto.getFormacionActual() != null && dto.getFormacionActual().getId() != null) {
         Esquema esquema = new Esquema();
         esquema.setId(dto.getFormacionActual().getId());
         equipo.setEsquema(esquema);
      } else {

         Esquema esquemaPorDefecto = new Esquema();
         esquemaPorDefecto.setId(1L);
         equipo.setEsquema(esquemaPorDefecto);
      }

      return equipo;
   }

   public EquipoDTO convertFromEntity(Equipo equipo) {
      if (equipo == null) {
         return null;
      }
      EquipoDTO dto = new EquipoDTO();
      dto.setId(equipo.getId());
      dto.setNombre(equipo.getNombre());

      if (equipo.getClub() != null) {
         dto.setClub(new ClubDTO().convertFromEntity(equipo.getClub()));
      }

      if (equipo.getEsquema() != null) {
         EsquemaDTO esquemaDTO = new EsquemaDTO();
         esquemaDTO.setId(equipo.getEsquema().getId());

         dto.setFormacionActual(esquemaDTO);
      }

      return dto;
   }

}
