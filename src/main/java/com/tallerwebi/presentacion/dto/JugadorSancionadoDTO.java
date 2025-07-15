package com.tallerwebi.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JugadorSancionadoDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String imagen;
  private String motivo; // "LESION" o "ROJA"
  private int fechasRestantes;
}