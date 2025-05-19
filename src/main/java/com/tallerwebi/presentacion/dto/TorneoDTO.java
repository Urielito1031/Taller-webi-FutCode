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
   private List<EquipoDTO> equiposParticipantes;
   private List<PartidoDTO> partidos;
   private FormatoTorneoDTO formatoTorneo;
   private EstadoTorneoEnum estado;

   public TorneoDTO() {
      this.estado = EstadoTorneoEnum.ABIERTO;
      this.equiposParticipantes = new ArrayList<>();
      this.partidos = new ArrayList<>();
   }
   public TorneoDTO(Long id, String nombre,String descripcion, FormatoTorneoDTO formatoTorneo) {
      this.id = id;
      this.nombre = nombre;
      this.descripcion = descripcion;
      this.formatoTorneo = formatoTorneo;
      this.estado = EstadoTorneoEnum.ABIERTO;
      this.equiposParticipantes = new ArrayList<>();
      this.partidos = new ArrayList<>();
   }
   public Integer getCantidadEquipos(){
      return equiposParticipantes.size();
   }



}
