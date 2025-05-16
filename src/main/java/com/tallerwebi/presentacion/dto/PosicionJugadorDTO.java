package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.PosicionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PosicionJugadorDTO {
   //atributo para recibir el id del formulario
   private Long jugadorId;
   private PosicionEnum posicionEnCampo;
   private JugadorDTO jugador;

   public PosicionJugadorDTO() {}

   public PosicionJugadorDTO(PosicionEnum posicionEnCampo, JugadorDTO jugador) {
      this.jugadorId = (jugador != null) ? jugador.getId() : null;
      this.posicionEnCampo = posicionEnCampo;
      this.jugador = jugador;
   }


}
