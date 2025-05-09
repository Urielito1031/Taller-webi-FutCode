package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.FormacionEsquema;
import com.tallerwebi.dominio.service.PlantillaServiceImpl;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class PlantillaController {

   private PlantillaServiceImpl service;
   public PlantillaController(PlantillaServiceImpl service) {
      this.service = service;
   }



   @GetMapping("/plantilla")
   public String showViewPlantilla(Model model) {
      FormacionDTO formacion = service.initPlantillaBase();
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      model.addAttribute("formacion", formacion);
      return "vista-plantilla";
   }

   @GetMapping("/formacion-inicial")
   public String cambiarFormacion(@RequestParam("esquema") String esquemaTexto, Model model) {
      //convertir el valor string a enum
      FormacionEsquema esquemaSeleccionado = FormacionEsquema.fromString(esquemaTexto);
      FormacionDTO formacion = service.initPlantillaBase();
      formacion.setEsquema(esquemaSeleccionado);
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema>esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      return "vista-plantilla";


   }
}
