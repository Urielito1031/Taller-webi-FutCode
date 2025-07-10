package com.tallerwebi.dominio.model.enums;

import lombok.Getter;

@Getter
public enum EstadoTorneoEnum {
   ABIERTO("Abierto para inscripciones"),
   EN_CURSO("En curso"),
   FINALIZADO("Finalizado");

   private final String descripcion;

   EstadoTorneoEnum(String descripcion) {
      this.descripcion = descripcion;
   }

}
