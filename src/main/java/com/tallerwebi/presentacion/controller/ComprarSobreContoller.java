package com.tallerwebi.presentacion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComprarSobreContoller {
    @GetMapping("/comprar-sobres")
    public String mostrarVistaComprarSobres(){
        return "vista-comprar-sobres";
    }

}
