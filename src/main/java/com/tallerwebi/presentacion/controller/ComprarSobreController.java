package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/jugador")
public class ComprarSobreController {

    private SobreService sobreService;
    private UsuarioService usuarioService;

    @Autowired
    public ComprarSobreController(UsuarioService usuarioService, SobreService sobreService) {
        this.sobreService = sobreService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/comprar-sobres")
    public ModelAndView mostrarVistaComprarSobres(){
        List<SobreDTO> sobres = this.sobreService.obtenerSobresDTO();
        ModelAndView mav = new ModelAndView("vista-comprar-sobres");
        mav.addObject("sobres", sobres);
        return mav;
    }

    @GetMapping("/mis-sobres")
    public ModelAndView mostrarVistaMisSobres(){
        // A cambiar por el id de la session
        List<SobreDTO> sobres = this.usuarioService.obtenerSobresDelUsuario(1002L);

        ModelAndView mav = new ModelAndView("vista-mis-sobres");
        mav.addObject("sobres", sobres);

        return mav;
    }

    @PostMapping("/agregarSobre")
    public String crearSobre(@RequestParam("tipoDeSobre")TipoSobre tipo, HttpSession request){
        System.out.println(tipo);

        SobreDTO sobreObtenido = this.sobreService.crearSobre(tipo);

//        Long id = session.getAttribute("usuario").getId();


        String returnText = "No se pudo crear el sobre";

        if(sobreObtenido != null){
            // A cambiar por ID de la session
            Boolean agregado = this.usuarioService.agregarSobreAJugador(1L, sobreObtenido);
            if(agregado){
//              returnText = "Sobre creado con exito";
                return "redirect:/jugador/mis-sobres";
            }
        }
        return "redirect:/jugador/mis-sobres";
    }


}
