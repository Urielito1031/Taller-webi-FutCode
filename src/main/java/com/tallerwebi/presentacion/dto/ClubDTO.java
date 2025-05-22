package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.Estadio;
import com.tallerwebi.dominio.model.enums.Pais;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
}
