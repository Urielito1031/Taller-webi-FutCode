package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.TipoFormato;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FormatoTorneoDTO {

   private TipoFormato tipo;

   // Atributo para liga
   private Integer rondas;

   // Campos para Copa
   private Integer equiposPorGrupo;
   private Integer equiposQueAvanzan;
   private List<String> fases;


   public FormatoTorneoDTO() {
      this.fases = List.of("Octavos de final","Cuartos de final", "Semifinal", "FINAL");
   }


}