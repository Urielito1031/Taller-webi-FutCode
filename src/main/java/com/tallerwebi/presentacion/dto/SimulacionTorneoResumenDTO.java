package com.tallerwebi.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulacionTorneoResumenDTO {
  private int puestoFinal;
  private int monedasGanadas;
  private double monedasTotales;
  private String nombreTorneo;
}