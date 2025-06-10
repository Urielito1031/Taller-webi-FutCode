package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.presentacion.dto.ClubDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "club")
public class Club {
   @Id
   @Column(name = "id", nullable = false)
   private Long id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "pais_id")
   private Pais pais;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "estadio_id")
   private com.tallerwebi.dominio.model.entities.Estadio estadio;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen")
   private String imagen;

   public ClubDTO convertToDTO() {
      ClubDTO dto = new ClubDTO();
      dto.setId(this.id);
      dto.setNombre(this.nombre);
      dto.setPais(this.pais);
      dto.setImagen(this.imagen);
      return dto;
   }
   public static Club convertToEntity(ClubDTO dto) {
      Club entity = new Club();
      entity.setId(dto.getId());
      entity.setNombre(dto.getNombre());
      entity.setImagen(dto.getImagen());
      if (dto.getPais() != null) {
         Pais pais = new Pais();
         pais.setId(dto.getPais().getId());
         entity.setPais(pais);
      }
      if(dto.getEstadio() != null) {
         Estadio estadio = new Estadio();
         estadio.setId(dto.getEstadio().getId());
         entity.setEstadio(estadio);
      }
      return entity;
   }
}