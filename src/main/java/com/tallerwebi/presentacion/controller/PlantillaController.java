package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.service.PlantillaService;
import com.tallerwebi.presentacion.dto.EsquemaDTO;

import javax.validation.Valid;
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
      System.out.println("Alineación en /formacion-inicial: " + formacion.getAlineacion());
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      return "vista-plantilla";
   }

   @PostMapping("/guardar-formacion")
   public String guardarFormacion(@Valid @ModelAttribute EsquemaDTO formacion, BindingResult result, Model model) {
      System.out.println("Parámetros recibidos: {} "+ formacion);

      if (result.hasErrors()) {
         System.out.println("Errores de validación: {} "+ result.getAllErrors());
         return prepararErrorRespuesta(model, result);
      }

      if (formacion.getAlineacion() == null || formacion.getAlineacion().size() != 11) {
         model.addAttribute("error", "La formación debe contener exactamente 11 jugadores.");
         return "vista-plantilla";
      }

      Boolean guardadoExitoso = service.save(formacion);
      if (!guardadoExitoso) {
         model.addAttribute("error", "Error al guardar la formación.");
         return "vista-plantilla";
      }

      model.addAttribute("message", "Formación guardada con éxito!");
      model.addAttribute("formacion", service.initPlantillaBase());
      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      return "vista-plantilla";
   }

   private String prepararErrorRespuesta(Model model, BindingResult result) {
      EsquemaDTO formacion = service.initPlantillaBase();
      model.addAttribute("formacion", formacion);
      model.addAttribute("esquemas", Arrays.asList(FormacionEsquema.values()));
      model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
      return "vista-plantilla";
   }
}
