package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.ChatMensaje;
import com.tallerwebi.dominio.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
  @Autowired
  private ChatService chatService;

  @GetMapping("/mensajes")
  public List<ChatMensaje> getMensajes(@RequestParam Long usuario1, @RequestParam Long usuario2) {
    return chatService.obtenerMensajes(usuario1, usuario2);
  }

  @PostMapping("/mensaje")
  public ChatMensaje enviarMensaje(@RequestBody ChatMensaje mensaje) {
    return chatService.enviarMensaje(mensaje.getRemitenteId(), mensaje.getDestinatarioId(), mensaje.getContenido());
  }
}