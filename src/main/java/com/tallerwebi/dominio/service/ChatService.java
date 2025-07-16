package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.ChatMensaje;
import java.util.List;

public interface ChatService {
  List<ChatMensaje> obtenerMensajes(Long usuario1, Long usuario2);

  ChatMensaje enviarMensaje(Long remitenteId, Long destinatarioId, String contenido);
}