package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class EsquemaDTO{
   private Long id;
   @NotNull(message = "El esquema es obligatorio")
   private FormacionEsquema esquema;
   @Size(min = 11, max = 11, message = "Debes tener exactamente 11 jugadores")
   private List<PosicionJugadorDTO> alineacion;
   //importante pasarle el ID del equipo asociado
   private Long equipoId;

   public EsquemaDTO(){
      this.alineacion = new ArrayList<>();
   }



   public String toString(){
      return "id: " + id + "\nEquipoId: "+equipoId+ "\n esquema: " + esquema + "\n alineacion: " + alineacion;
   }

}
