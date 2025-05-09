package com.tallerwebi.dominio.model;

public enum FormacionEsquema {
   CUATRO_TRES_TRES(4, 3, 3),
   CUATRO_CUATRO_DOS(4, 4, 2),
   TRES_CINCO_DOS(3, 5, 2),
   CUATRO_DOS_TRES_UNO(4, 2, 3, 1),
   CINCO_TRES_DOS(5, 3, 2),
   TRES_CUATRO_TRES(3, 4, 3);

   private final int defensas;
   private final int mediocampistas;
   private final int delanteros;
   private final int totalJugadores;

   // Constructor para formaciones con 3 grupos (defensas, medios, delanteros)
   FormacionEsquema(int defensas, int mediocampistas, int delanteros) {
      this.defensas = defensas;
      this.mediocampistas = mediocampistas;
      this.delanteros = delanteros;
      this.totalJugadores = defensas + mediocampistas + delanteros;
   }

   // Constructor para formaciones con 4 grupos (p. ej., 4-2-3-1)
   FormacionEsquema(int defensas, int mediocampistasDefensivos, int mediocampistasOfensivos, int delanteros) {
      this.defensas = defensas;
      this.mediocampistas = mediocampistasDefensivos + mediocampistasOfensivos;
      this.delanteros = delanteros;
      this.totalJugadores = defensas + mediocampistas + delanteros;
   }

   // Método para obtener la representación en texto (ej. "4-3-3")
   public String getFormacionTexto() {
      if (this == CUATRO_DOS_TRES_UNO) {
         return "4-2-3-1";
      }
      return defensas + "-" + mediocampistas + "-" + delanteros;
   }

   // Getters
   public int getDefensas() {
      return defensas;
   }

   public int getMediocampistas() {
      return mediocampistas;
   }

   public int getDelanteros() {
      return delanteros;
   }

   public int getTotalJugadores() {
      return totalJugadores;
   }

   // Método para obtener el enum a partir de una cadena (opcional)
   public static FormacionEsquema fromString(String formacionTexto) {
      switch (formacionTexto) {
         case "4-3-3":
            return CUATRO_TRES_TRES;
         case "4-4-2":
            return CUATRO_CUATRO_DOS;
         case "3-5-2":
            return TRES_CINCO_DOS;
         case "4-2-3-1":
            return CUATRO_DOS_TRES_UNO;
         case "5-3-2":
            return CINCO_TRES_DOS;
         case "3-4-3":
            return TRES_CUATRO_TRES;
         default:
            throw new IllegalArgumentException("Formación no válida: " + formacionTexto);
      }
   }
}