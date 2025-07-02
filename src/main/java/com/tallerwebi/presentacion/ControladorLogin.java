package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
   public class ControladorLogin {

      private final ServicioLogin servicioLogin;


      @Autowired
      public ControladorLogin(ServicioLogin servicioLogin){
         this.servicioLogin = servicioLogin;
      }

      @RequestMapping("/login")
      public ModelAndView irALogin() {

         ModelMap modelo = new ModelMap();
         modelo.put("datosLogin", new DatosLogin());
         return new ModelAndView("login", modelo);
      }

      @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
      public ModelAndView validarLogin( @ModelAttribute("datosLogin") DatosLogin datosLogin,HttpServletRequest request) {
         ModelMap model = new ModelMap();

         Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
         if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());


            request.getSession().setAttribute("USUARIO_ID", usuarioBuscado.getId());


            return new ModelAndView("redirect:/home");
         } else {
            model.put("error", "Usuario o clave incorrecta");
         }
         return new ModelAndView("login", model);
      }

      @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
      public ModelAndView registrarme(@Valid @ModelAttribute("usuario") Usuario usuario,BindingResult result) {
         ModelMap model = new ModelMap();

         if(result.hasErrors()) {
            return new ModelAndView("nuevo-usuario", model);
         }
         try{
            servicioLogin.registrar(usuario);
         } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
         } catch (Exception e){
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

   @RequestMapping("/logout")
   public ModelAndView logout(HttpServletRequest request) {
      request.getSession().invalidate();
      return new ModelAndView("redirect:/login");
   }



   @RequestMapping(path = "/", method = RequestMethod.GET)
      public ModelAndView inicio() {
         return new ModelAndView("redirect:/login");
      }
   }

