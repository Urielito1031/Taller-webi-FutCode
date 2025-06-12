package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
public class PlantillaController {

   private final PlantillaService service;


   @Autowired
   public PlantillaController(PlantillaService service) {
      this.service = service;
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
   public String showViewPlantilla(Model model) {
      EsquemaDTO formacion = service.initPlantillaBase();
      System.out.println("Alineación en /plantilla: " + formacion.getAlineacion());
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      model.addAttribute("formacion", formacion);
      return "vista-plantilla";
   }

   @GetMapping("/formacion-inicial")
   public String cambiarFormacion(@RequestParam("esquema") String esquemaTexto, Model model) {
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      EsquemaDTO formacion = service.initPlantillaBase();
      formacion.setEsquema(esquemaSeleccionado);
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      return "vista-plantilla";
   }

   @PostMapping("/guardar-formacion")
   public String guardarFormacion(@Valid @ModelAttribute EsquemaDTO formacion, BindingResult result, Model model) {
      if (formacion == null) {
         System.out.println("Formacion no encontrada");
         model.addAttribute("formacion", new EsquemaDTO());
         model.addAttribute("error", "Formación no encontrada.");
         return "vista-plantilla";
      }

      if (result.hasErrors()) {
         System.out.println("Errores de validación: " + result.getAllErrors());
         model.addAttribute("formacion", new EsquemaDTO());
         return prepararErrorRespuesta(model, result);
      }

      if (formacion.getAlineacion() == null || formacion.getAlineacion().size() != 11) {
         model.addAttribute("formacion", new EsquemaDTO());
         model.addAttribute("error", "La formación debe contener exactamente 11 jugadores.");
         return "vista-plantilla";
      }

      // Validación de equipoId
      if (formacion.getEquipoId() == null || formacion.getEquipoId() == 0L) {
         System.out.println("Error: equipoId es null o 0. Valor recibido: " + formacion.getEquipoId());
         model.addAttribute("formacion", new EsquemaDTO());
         model.addAttribute("error", "El ID del equipo no está definido. Contacta al administrador.");
         return "vista-plantilla";
      }

      Boolean guardadoExitoso = service.save(formacion);
      if (!guardadoExitoso) {
         model.addAttribute("formacion", new EsquemaDTO());
         model.addAttribute("error", "Error al guardar la formación.");
         return "vista-plantilla";
      }

      model.addAttribute("message", "Formación guardada con éxito!");
      model.addAttribute("formacion", service.initPlantillaBase());
      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      return "vista-plantilla";
   }

   private String prepararErrorRespuesta(Model model, BindingResult result) {
      model.addAttribute("formacion", new EsquemaDTO());
      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
      return "vista-plantilla";
   }
}
