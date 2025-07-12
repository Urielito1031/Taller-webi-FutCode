package com.tallerwebi.presentacion.dto;

public class NarracionDTO {
  private String texto;
  private int minuto;
  private String tipoEvento;

  public NarracionDTO() {
  }

  public NarracionDTO(String texto, int minuto, String tipoEvento) {
    this.texto = texto;
    this.minuto = minuto;
    this.tipoEvento = tipoEvento;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public int getMinuto() {
    return minuto;
  }

  public void setMinuto(int minuto) {
    this.minuto = minuto;
  }

  public String getTipoEvento() {
    return tipoEvento;
  }

  public void setTipoEvento(String tipoEvento) {
    this.tipoEvento = tipoEvento;
  }
}