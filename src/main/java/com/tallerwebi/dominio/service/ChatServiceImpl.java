package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.ChatMensaje;
import com.tallerwebi.dominio.repository.ChatMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
  @Autowired
  private ChatMensajeRepository repo;

  @Override
  public List<ChatMensaje> obtenerMensajes(Long usuario1, Long usuario2) {
    return repo.obtenerMensajesEntre(usuario1, usuario2);
  }

  @Override
  public ChatMensaje enviarMensaje(Long remitenteId, Long destinatarioId, String contenido) {
    ChatMensaje msg = new ChatMensaje();
    msg.setRemitenteId(remitenteId);
    msg.setDestinatarioId(destinatarioId);
    msg.setContenido(contenido);
    msg.setLeido(false);
    repo.guardar(msg);
    return msg;
  }
}