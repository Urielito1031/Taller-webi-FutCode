package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.EventoPartido;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EventoPartidoDTO {
   private EventoPartido tipoEventoPartido;
   private String descripcion;
   private Integer minutoDePartido;



}
