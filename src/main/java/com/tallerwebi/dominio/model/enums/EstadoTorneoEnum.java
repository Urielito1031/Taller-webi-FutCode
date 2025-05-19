package com.tallerwebi.dominio.model.enums;

public enum EstadoTorneoEnum {
   ABIERTO("Abierto para inscripciones"),
   EN_CURSO("En curso"),
   CERRADO("Finalizado");

   private final String descripcion;

   EstadoTorneoEnum(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getDescripcion() {
      return descripcion;
   }
}
