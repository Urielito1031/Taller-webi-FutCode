package com.tallerwebi.presentacion.dto;

public class ChatMensajeDTO {
  private Long id;
  private Long remitenteId;
  private Long destinatarioId;
  private String contenido;
  private boolean leido;

  // Getters y setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getRemitenteId() {
    return remitenteId;
  }

  public void setRemitenteId(Long remitenteId) {
    this.remitenteId = remitenteId;
  }

  public Long getDestinatarioId() {
    return destinatarioId;
  }

  public void setDestinatarioId(Long destinatarioId) {
    this.destinatarioId = destinatarioId;
  }

  public String getContenido() {
    return contenido;
  }

  public void setContenido(String contenido) {
    this.contenido = contenido;
  }

  public boolean isLeido() {
    return leido;
  }

  public void setLeido(boolean leido) {
    this.leido = leido;
  }
}