package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class TorneoDTO {

   private Long id;
   private String nombre;
   private String descripcion;

   private FormatoTorneoDTO formatoTorneo;
   private EstadoTorneoEnum estado;

   public TorneoDTO() {
      this.estado = EstadoTorneoEnum.ABIERTO;

   }
   public TorneoDTO(Long id, String nombre,String descripcion, FormatoTorneoDTO formatoTorneo) {
      this.id = id;
      this.nombre = nombre;
      this.descripcion = descripcion;
      this.formatoTorneo = formatoTorneo;
      this.estado = EstadoTorneoEnum.ABIERTO;

   }



}
