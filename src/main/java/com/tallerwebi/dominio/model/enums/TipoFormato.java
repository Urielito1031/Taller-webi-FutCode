package com.tallerwebi.dominio.model.enums;

public enum TipoFormato {
   LIGA("Liga"),
   COPA("Copa");

   private final String nombre;

   TipoFormato(String nombre) {
      this.nombre = nombre;
   }

   public String getNombre() {
      return nombre;
   }
}