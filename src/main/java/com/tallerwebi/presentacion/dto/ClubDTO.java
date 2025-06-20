package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Club;
import com.tallerwebi.dominio.model.entities.Estadio;
import com.tallerwebi.dominio.model.entities.Pais;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GenerationType;

@Getter
@Setter
public class ClubDTO {
   private Long id;
   private String nombre;
   private Pais pais;
   private Estadio estadio;
   private String imagen;

   public ClubDTO() {}

   public ClubDTO(Long id, String nombre, Pais pais, Estadio estadio, String imagen) {
      this.id = id;
      this.nombre = nombre;
      this.pais = pais;
      this.estadio = estadio;
      this.imagen = imagen;
   }

   public Club convertToEntity(ClubDTO dto){
      Club entity = new Club();
      entity.setId(dto.getId());
      entity.setNombre(dto.getNombre());
      entity.setPais(dto.getPais());
      entity.setImagen(dto.getImagen());
      entity.setEstadio(dto.getEstadio());
      return entity;
   }

   public ClubDTO convertFromEntity(Club club){
      ClubDTO dto = new ClubDTO();
      dto.setId(club.getId());
      dto.setNombre(club.getNombre());
      dto.setPais(club.getPais());
      dto.setEstadio(club.getEstadio());
      dto.setImagen(club.getImagen());
      return dto;
   }
}
