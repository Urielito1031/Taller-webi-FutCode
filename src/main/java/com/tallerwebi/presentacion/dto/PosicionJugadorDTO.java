package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class PosicionJugadorDTO {
   //atributo para recibir el id del formulario
   private Long jugadorId;
   private PosicionEnum posicionEnCampo;
   private JugadorDTO jugador;

   public PosicionJugadorDTO() {
   }


   public PosicionJugadorDTO(PosicionEnum posicionEnCampo, JugadorDTO jugador) {
      this.jugadorId = (jugador != null) ? jugador.getId() : null;
      this.posicionEnCampo = posicionEnCampo != null ? posicionEnCampo : (jugador != null ? jugador.getPosicionNatural() : null);
      this.jugador = jugador;
   }

   //al guardar la formacion, esto da null, investigar
   public String toString(){
      return  "JugadorID: "+ this.getJugadorId()+"\nJugador: " + jugador.getNombre() + "\nPosicion: " + posicionEnCampo;
   }

}
