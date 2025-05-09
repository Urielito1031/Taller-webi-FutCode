package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.FormacionEsquema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class FormacionDTO {
   private Long id;
   private FormacionEsquema esquema;
   private List<PosicionJugadorDTO> alineacion;
}
