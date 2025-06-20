package com.tallerwebi.presentacion.dto;

import lombok.Data;

@Data
public class EquipoTorneoDTO {

   private Long id;
   private int posicion;
   private int partidosJugados;
   private int partidosGanados;
   private int partidosEmpatados;
   private int partidosPerdidos;
   private int golesAFavor;
   private int golesEnContra;
   private int puntos;

   private TorneoDTO torneo;
   private EquipoDTO equipo;

   public EquipoTorneoDTO(int posicion,int partidosJugados, int partidosGanados, int partidosEmpatados, int partidosPerdidos, int golesAFavor, int golesEnContra, int puntos) {
      this.posicion = posicion;
      this.partidosJugados = partidosJugados;
      this.partidosGanados = partidosGanados;
      this.partidosEmpatados = partidosEmpatados;
      this.partidosPerdidos = partidosPerdidos;
      this.golesAFavor = golesAFavor;
      this.golesEnContra = golesEnContra;
      this.puntos = puntos;
   }

   public EquipoTorneoDTO() {
   }



   public int getDg() {
      return golesAFavor - golesEnContra;
   }

   public String toString(){
      return "EquipoTorneoDTO{" +
        "id=" + id +
        ", posicion=" + posicion +
        ", partidosJugados=" + partidosJugados +
        ", partidosGanados=" + partidosGanados +
        ", partidosEmpatados=" + partidosEmpatados +
        ", partidosPerdidos=" + partidosPerdidos +
        ", golesAFavor=" + golesAFavor +
        ", golesEnContra=" + golesEnContra +
        ", puntos=" + puntos +
        ", torneoDTO={id=" + (torneo != null ? torneo.getId() : "null") +
        ", nombre='" + (torneo != null ? torneo.getNombre() : "null") + "'}" +
        ", equipoDTO={id=" + (equipo != null ? equipo.getId() : "null") +
        ", nombre='" + (equipo != null ? equipo.getNombre() : "null") + "'}" +
        '}';
   }


}
