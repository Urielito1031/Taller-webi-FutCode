package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import lombok.Data;

@Data
public class HistorialTorneoDTO {
  private String nombreTorneo;
  private String tipoTorneo; // "PARTIDO_UNICO", "LIGA", etc.
  private int puesto;
  private int golesMarcados;
  private boolean ganado; // puesto == 1
  private boolean top3; // puesto <= 3
  private EstadoTorneoEnum estado; // para validaciones futuras
}