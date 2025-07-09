package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Partido;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PartidoDTO {
   private Long id;
   private EquipoDTO equipoLocal;
   private EquipoDTO equipoVisitante;
   private Date fechaEncuentro;
   private List<EventoPartidoDTO> eventosPartido;

   public PartidoDTO() {}
   public PartidoDTO(Long id, EquipoDTO equipoLocal,EquipoDTO equipoVisitante, Date fechaEncuentro) {
      this.id = id;
      this.equipoLocal = equipoLocal;
      this.equipoVisitante = equipoVisitante;
      this.fechaEncuentro = fechaEncuentro;
      this.eventosPartido = new ArrayList<>();
   }

   public static Partido convertToDTO(Partido partido){

   }
}
