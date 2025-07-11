package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.EquipoService;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SobreController {
    private SobreService sobreService;
    private UsuarioService usuarioService;
    private JugadorService jugadorService;

    @Autowired
    public SobreController(SobreService sobreService, UsuarioService usuarioService, JugadorService jugadorService) {
        this.usuarioService = usuarioService;
        this.sobreService = sobreService;
        this.jugadorService = jugadorService;
    }

    @PostMapping("/sobre")
    public ModelAndView getSobre(@RequestParam("tipoDeSobre") TipoSobre tipoSobre, HttpServletRequest request) throws UsuarioNoEncontrado {
        SobreDTO sobre = this.sobreService.crearSobre(tipoSobre);

        Long id_usuario = (Long) request.getSession().getAttribute("USUARIO_ID");

        SobreDTO sobreParaBorrar = this.usuarioService.obtenerSobresDelUsuario(id_usuario)
                .stream()
                .filter(s -> s.getTipoSobre().equals(tipoSobre))
                .findFirst()
                .orElse(null);

        if (id_usuario == null){
            throw new UsuarioNoEncontrado("El usuario con ID " + id_usuario + " no fue encontrado.");
        }

        this.usuarioService.borrarSobreAUsuario(id_usuario, sobreParaBorrar.getId());

        ModelAndView mav = new ModelAndView("sobre");
        mav.addObject("sobre", sobre);

        // AGREGAR LOS JUGADORES QUE ESTAN EN EL SOBRE AL USUARIO
        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id_usuario);

        List<Jugador> jugadores = sobre.fromEntity().getJugadores();
        this.jugadorService.agregarJugadoresDelSobreAlEquipo(usuario.getEquipo().getId(), jugadores);

        return mav;
    }



}
