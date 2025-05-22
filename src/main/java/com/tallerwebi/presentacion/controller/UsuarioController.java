package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/agregarSobre")
    public String agregarSobre(@RequestParam("TipoDeSobre") TipoSobre tipoSobre, Usuario usuario){
        Long id = usuario.getId();
        usuarioServiceImpl.agregarSobreAJugador(id, tipoSobre);
        return "redirect:/comprar-sobres";

    }
}
