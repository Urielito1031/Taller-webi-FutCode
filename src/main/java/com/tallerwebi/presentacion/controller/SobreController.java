package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SobreController {
    private SobreService sobreService;
    private UsuarioService usuarioService;

    @Autowired
    public SobreController(SobreService sobreService, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.sobreService = sobreService;
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
//        Usuario usuario = this.usuarioService.buscarUsuarioPorId(id_usuario);
//        Equipo equipo = usuario.getEquipo();
//        EquipoDTO equipoDTO = equipo.convertToDTO();
//        equipoDTO.getJugadores().addAll(sobre.getJugadores());

        return mav;
    }



}
