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
   public String guardarFormacion(@Valid @ModelAttribute EsquemaDTO formacionAGuardar,
                                  BindingResult result, Model model, HttpServletRequest request) {
      System.out.println("Parámetros recibidos:");
      request.getParameterMap().forEach((key, value) -> System.out.println(key + ": " + Arrays.toString(value)));
      System.out.println("Formación recibida: " + formacionAGuardar);

      if (result.hasErrors()) {
         System.out.println("tiene errores");
         EsquemaDTO formacion = service.initPlantillaBase();
         model.addAttribute("formacion", formacion);
         List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
         model.addAttribute("esquemas", esquemas);
         model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
         return "vista-plantilla";
      }

      // Asignar posiciones y construir JugadorDTO desde jugadorId
      FormacionEsquema esquema = formacionAGuardar.getEsquema();
      List<PosicionEnum> posiciones = service.getPosicionesPorEsquema(esquema);



      EsquemaDTO formacion = service.initPlantillaBase();
      formacion.setEsquema(FormacionEsquema.fromString(formacionAGuardar.getEsquema().getFormacionTexto()));
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      model.addAttribute("message", "Formación guardada con éxito!");
      return "vista-plantilla";
   }
}
