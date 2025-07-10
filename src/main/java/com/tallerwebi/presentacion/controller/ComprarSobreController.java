package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private final SobreService sobreService;
    private final UsuarioService usuarioService;

    @Autowired
    public ComprarSobreController(UsuarioService usuarioService, SobreService sobreService) {
        this.sobreService = sobreService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/comprar-sobres")
    public ModelAndView mostrarVistaComprarSobres(HttpServletRequest request){
        Long id = (Long) request.getSession().getAttribute("USUARIO_ID");
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
        List<SobreDTO> sobres = this.sobreService.obtenerSobresDTO();
        ModelAndView mav = new ModelAndView("vista-comprar-sobres");
        mav.addObject("sobres", sobres);
        mav.addObject("cantidadSobres", this.usuarioService.obtenerSobresDelUsuario(id).size());


        if (usuario.getEquipo() != null) {
            mav.addObject("equipoNombre", usuario.getEquipo().getNombre());
        } else {
            mav.addObject("equipoNombre", "Sin equipo");
        }

        mav.addObject("monedas", usuario.getMonedas());

        return mav;
    }

    @GetMapping("/mis-sobres")
    public ModelAndView mostrarVistaMisSobres(HttpServletRequest request){
        Long id = (Long) request.getSession().getAttribute("USUARIO_ID");
        List<SobreDTO> sobres = this.usuarioService.obtenerSobresDelUsuario(id);

        ModelAndView mav = new ModelAndView("vista-mis-sobres");
        mav.addObject("sobres", sobres);
        mav.addObject("cantidadSobres", sobres.size());

        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);

        if (usuario.getEquipo() != null) {
            mav.addObject("equipoNombre", usuario.getEquipo().getNombre());
        } else {
            mav.addObject("equipoNombre", "Sin equipo");
        }

        mav.addObject("monedas", usuario.getMonedas());
        return mav;
    }

    @PostMapping("/agregarSobre")
    public ModelAndView crearSobre(@RequestParam("tipoDeSobre")TipoSobre tipo, HttpServletRequest request){
        SobreDTO sobreObtenido = this.sobreService.crearSobre(tipo);

        Long id = (Long) request.getSession().getAttribute("USUARIO_ID");

        try{
            this.usuarioService.agregarSobreAJugador(id, sobreObtenido);
            return new ModelAndView("redirect:/jugador/mis-sobres");

        }catch(MonedasInsuficientes ex){
            ModelAndView mav = new ModelAndView("vista-comprar-sobres");
            mav.addObject("mensajeError", ex.getMessage());
            mav.addObject("sobres", this.sobreService.obtenerSobresDTO());

            Usuario usuario = this.usuarioService.buscarUsuarioPorId(id);
            mav.addObject("monedas", usuario.getMonedas());

            return mav;

        }

    }


}
