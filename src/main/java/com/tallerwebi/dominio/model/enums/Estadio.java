package com.tallerwebi.dominio.model.enums;

public enum Estadio {
   BOMBONERA("La Bombonera", "Buenos Aires, Argentina"),
   MONUMENTAL("Estadio Monumental", "Buenos Aires, Argentina"),
   SANTIAGO_BERNABEU("Santiago Bernabéu", "Madrid, España"),
   WEMBLEY("Wembley Stadium", "Londres, Inglaterra"),
   MARACANA("Maracanã", "Río de Janeiro, Brasil");

   private final String nombre;
   private final String ubicacion;

   Estadio(String nombre, String ubicacion) {
      this.nombre = nombre;
      this.ubicacion = ubicacion;
   }

   public String getNombre() {
      return nombre;
   }

   public String getUbicacion() {
      return ubicacion;
   }
}