package com.tallerwebi.dominio.repository;

import com.tallerwebi.dominio.model.entities.ChatMensaje;
import java.util.List;

public interface ChatMensajeRepository {
  List<ChatMensaje> obtenerMensajesEntre(Long usuario1, Long usuario2);

  void guardar(ChatMensaje mensaje);
}