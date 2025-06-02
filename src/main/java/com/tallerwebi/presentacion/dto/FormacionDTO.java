package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Formacion;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class FormacionDTO {
   private Long id;
   @NotNull(message = "El esquema es obligatorio")
   private FormacionEsquema esquema;
   @Size(min =11,max =11, message = "Debes tener exactamente 11 jugadores")
   private List<PosicionJugadorDTO> alineacion;

   public FormacionDTO() {
      this.alineacion = new ArrayList<>();
   }


   //a implementar
//   public Formacion toEntity(){
//      Formacion formacion = new Formacion();
//      formacion.setEsquema(esquema.toString());
//
//      return formacion;
//
//   }

   public String toString(){
      return "id: " + id + "\n esquema: " + esquema + "\n alineacion: " + alineacion;
   }

}
