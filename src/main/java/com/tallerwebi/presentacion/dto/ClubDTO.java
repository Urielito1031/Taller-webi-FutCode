package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.Estadio;
import com.tallerwebi.dominio.model.Pais;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ClubDTO {
   private Long id;
   private String nombre;
   private Pais pais;
   private Estadio estadio;
   private String imagen;

}
