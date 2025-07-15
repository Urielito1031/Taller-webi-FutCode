package com.tallerwebi.dominio.model.enums;

public enum TipoFormato {
   LIGA("Liga"),
   COPA("Copa"),
   PARTIDO_UNICO("Partido Único");

   private final String nombre;

   TipoFormato(String nombre) {
      this.nombre = nombre;
   }

   public String getNombre() {
      return nombre;
   }
}