package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    private final TorneoService torneoService;


    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin, TorneoService torneoService){
        this.servicioLogin = servicioLogin;
       this.torneoService = torneoService;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        System.out.println(usuarioBuscado);
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            System.out.println(usuario);
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/nuevo-equipo");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public String irAHome(Model model) {

        try{
        List<TorneoDTO> torneos = torneoService.getAll();
        if(torneos == null){
            System.out.println("No se encontro el torneo");
            model.addAttribute("error", "torneos no encontrados");
        }
        model.addAttribute("torneos", torneos);

        }catch(Exception e){
            System.out.println("la lista de torneos devuelve nullpointer" + e);
        }

        return "home";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }
}

