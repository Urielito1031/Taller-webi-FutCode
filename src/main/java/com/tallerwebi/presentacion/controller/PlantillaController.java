package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.JugadorService;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class PlantillaController {

   private final PlantillaService service;
   private final JugadorService jugadorService;
   private final UsuarioService usuarioService;


   @Autowired
   public PlantillaController(PlantillaService service,JugadorService jugadorService,UsuarioService usuarioService) {
      this.service = service;
      this.jugadorService = jugadorService;
      this.usuarioService = usuarioService;
   }

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.registerCustomEditor(FormacionEsquema.class, new FormacionEsquemaEditor());
      binder.registerCustomEditor(PosicionEnum.class, new PropertyEditorSupport() {
         @Override
         public void setAsText(String text) throws IllegalArgumentException {
            setValue(PosicionEnum.valueOf(text.toUpperCase()));
         }
      });
   }

   @GetMapping("/plantilla")
   public String showViewPlantilla(Model model, HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      System.out.println("EL ID DE USUARIO EN SESSION ES: " + usuarioId);
      if (usuarioId == null) {
         System.out.println("No hay usuario autenticado. Redirigiendo a login.");
         return "redirect:/login";
      }

      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      System.out.println("EL USUARIO EN SESSION ES: " + usuario);
      if (usuario == null) {
         System.out.println("Usuario no encontrado para ID: " + usuarioId);
         return "redirect:/login";
      }

      Long equipoId = usuario.getEquipo() != null ? usuario.getEquipo().getId() : null;
      if (equipoId == null) {
         System.out.println("El usuario no tiene un equipo asignado. Redirigiendo a nuevo-equipo.");
         return "redirect:/nuevo-equipo";
      }
      System.out.println("EL EQUIPO EN SESSION ES: " + equipoId);

      EsquemaDTO formacion = service.getFormacionPorEquipoId(equipoId);


      List<JugadorDTO> jugadores = jugadorService.getAllByEquipoId(formacion.getEquipoId());
      model.addAttribute("jugadores", jugadores != null ? jugadores : new ArrayList<>());
      System.out.println("Los jugadores del equipo son: ");
      for (JugadorDTO jugador : jugadores) {
         System.out.println("Jugador: " + jugador.getId());
      }
      model.addAttribute("formacion", formacion);

      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);

      return "vista-plantilla";
   }

   @GetMapping("/formacion-inicial")
   public String cambiarFormacion(@RequestParam("esquema") String esquemaTexto,Model model,HttpServletRequest request) {
      Long usuarioId = (Long) request.getSession().getAttribute("USUARIO_ID");
      Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
      Long equipoId = usuario.getEquipo().getId();

      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      EsquemaDTO formacion = service.initPlantillaBase(equipoId);

      formacion.setEsquema(esquemaSeleccionado);
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);


      //agregado para mostrar los jugadores totales del mismo equipo
      List<JugadorDTO> jugadores  = jugadorService.getAllByEquipoId(formacion.getEquipoId());
      model.addAttribute("jugadores", jugadores);


      return "vista-plantilla";
   }

   @PostMapping("/guardar-formacion")
   public String guardarFormacion(@Valid @ModelAttribute EsquemaDTO formacion, BindingResult result, Model model) {


      if (result.hasErrors()) {
         model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
         List<JugadorDTO> jugadores = jugadorService.getAllByEquipoId(formacion.getEquipoId());
         model.addAttribute("jugadores", jugadores != null ? jugadores : new ArrayList<>());
         return prepararErrorRespuesta(model, result);
      }

      if (formacion.getAlineacion() == null || formacion.getAlineacion().size() != 11) {
         model.addAttribute("formacion", formacion);
         model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
         List<JugadorDTO> jugadores = jugadorService.getAllByEquipoId(formacion.getEquipoId());
         model.addAttribute("jugadores", jugadores != null ? jugadores : new ArrayList<>());
         model.addAttribute("error", "La formación debe contener exactamente 11 jugadores.");
         return "vista-plantilla";
      }

      Boolean guardadoExitoso = service.save(formacion);
      if (!guardadoExitoso) {
         model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
         List<JugadorDTO> jugadores = jugadorService.getAllByEquipoId(formacion.getEquipoId());
         model.addAttribute("jugadores", jugadores != null ? jugadores : new ArrayList<>());
         model.addAttribute("error", "Error al guardar la formación.");
         return "vista-plantilla";
      }

      // Cargar la formación guardada usando el equipoId de la formación

      EsquemaDTO formacionGuardada = service.getFormacionPorEquipoId(formacion.getEquipoId());
      //esto se guarda bien con estos datos, pero no se muestra en la vista
      model.addAttribute("formacion", formacionGuardada);

      // Cargar la lista de jugadores del equipo
      List<JugadorDTO> jugadores = jugadorService.getAllByEquipoId(formacion.getEquipoId());
      model.addAttribute("jugadores", jugadores != null ? jugadores : new ArrayList<>());

      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      model.addAttribute("message", "Formación guardada con éxito!");

      return "vista-plantilla";
   }

   private String prepararErrorRespuesta(Model model, BindingResult result) {
      model.addAttribute("formacion", new EsquemaDTO());
      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
      return "vista-plantilla";
   }



}
