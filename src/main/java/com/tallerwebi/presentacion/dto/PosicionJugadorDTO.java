package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.PosicionEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PosicionJugadorDTO {
   private PosicionEnum posicionEnCampo;
   private JugadorDTO jugador;

   public PosicionJugadorDTO(PosicionEnum posicionEnCampo, JugadorDTO jugador) {
      this.posicionEnCampo = posicionEnCampo;
      this.jugador = jugador;
   }


}
