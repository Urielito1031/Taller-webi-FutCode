package com.tallerwebi.presentacion.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.AssertTrue;

public class CrearTorneoDTO {
  @NotNull(message = "La descripción es obligatoria")
  @Size(min = 20, message = "La descripción debe tener al menos 20 caracteres")
  private String descripcion;

  @NotNull(message = "Debes indicar el premio para el primer lugar")
  @Min(value = 5000, message = "El premio debe ser al menos 5000 monedas")
  @Max(value = 10000, message = "El premio no puede superar las 10000 monedas")
  private Integer monedasPrimerLugar;

  @NotNull(message = "Debes indicar la cantidad de equipos")
  @Min(value = 2, message = "Debe haber al menos 2 equipos")
  @Max(value = 32, message = "No puede haber más de 32 equipos")
  private Integer cantidadEquipos;

  @NotNull(message = "El nombre es obligatorio")
  @Size(min = 5, message = "El nombre debe tener al menos 5 caracteres")
  private String nombre;

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getMonedasPrimerLugar() {
    return monedasPrimerLugar;
  }

  public void setMonedasPrimerLugar(Integer monedasPrimerLugar) {
    this.monedasPrimerLugar = monedasPrimerLugar;
  }

  public Integer getCantidadEquipos() {
    return cantidadEquipos;
  }

  public void setCantidadEquipos(Integer cantidadEquipos) {
    this.cantidadEquipos = cantidadEquipos;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  @AssertTrue(message = "La cantidad de equipos debe ser un número par")
  public boolean isCantidadEquiposPar() {
    return cantidadEquipos != null && cantidadEquipos % 2 == 0;
  }
}