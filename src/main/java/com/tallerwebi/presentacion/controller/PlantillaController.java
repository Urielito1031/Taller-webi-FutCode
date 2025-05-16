package com.tallerwebi.presentacion.controller;

import com.tallerwebi.dominio.model.FormacionEsquema;
import com.tallerwebi.dominio.model.PosicionEnum;
import com.tallerwebi.dominio.service.PlantillaServiceImpl;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import jakarta.validation.Valid;
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

   private PlantillaServiceImpl service;
   public PlantillaController(PlantillaServiceImpl service) {
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
   @PostMapping("/guardar-formacion")
   public String guardarFormacion(@Valid @ModelAttribute FormacionDTO formacionAGuardar,
                                  BindingResult result, Model model, HttpServletRequest request) {
      System.out.println("Parámetros recibidos:");
      request.getParameterMap().forEach((key, value) -> System.out.println(key + ": " + Arrays.toString(value)));
      System.out.println("Formación recibida: " + formacionAGuardar);
      for (PosicionJugadorDTO pj : formacionAGuardar.getAlineacion()) {
         System.out.println("Posición: " + pj.getPosicionEnCampo() + ", JugadorId: " + pj.getJugadorId());
      }
      if (result.hasErrors()) {
         System.out.println("tiene errores");
         FormacionDTO formacion = service.initPlantillaBase();
         model.addAttribute("formacion", formacion);
         List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
         model.addAttribute("esquemas", esquemas);
         model.addAttribute("error", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
         return "vista-plantilla";
      }

      // Asignar posiciones y construir JugadorDTO desde jugadorId
      FormacionEsquema esquema = formacionAGuardar.getEsquema();
      List<PosicionEnum> posiciones = service.getPosicionesPorEsquema(esquema);
      List<PosicionJugadorDTO> alineacion = formacionAGuardar.getAlineacion();
      for (int i = 0; i < alineacion.size(); i++) {
         PosicionJugadorDTO posicionJugador = alineacion.get(i);
         posicionJugador.setPosicionEnCampo(posiciones.get(i));

         Long jugadorId = posicionJugador.getJugadorId();
         if (jugadorId == null) {
            throw new IllegalArgumentException("ID de jugador es null para la posición " + i);
         }
         JugadorDTO jugador = new JugadorDTO();
         jugador.setId(jugadorId);
         posicionJugador.setJugador(jugador);
      }

      // Simular guardado
      System.out.println("Esquema: " + formacionAGuardar.getEsquema().getFormacionTexto());
      formacionAGuardar.getAlineacion().forEach(posicionJugador ->
            System.out.println("Jugador guardado: " + posicionJugador.getJugador().getId() +
                  ", Posición: " + posicionJugador.getPosicionEnCampo()));

      FormacionDTO formacion = service.initPlantillaBase();
      formacion.setEsquema(FormacionEsquema.fromString(formacionAGuardar.getEsquema().getFormacionTexto()));
      model.addAttribute("formacion", formacion);
      List<FormacionEsquema> esquemas = Arrays.asList(FormacionEsquema.values());
      model.addAttribute("esquemas", esquemas);
      model.addAttribute("message", "Formación guardada con éxito!");
      return "vista-plantilla";
   }
}
