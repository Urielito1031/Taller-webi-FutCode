package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.FormacionEquipo;
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



   public static PosicionJugadorDTO convertToDTO(FormacionEquipo formacionEquipo){
      PosicionJugadorDTO dto = new PosicionJugadorDTO();
      dto.setJugadorId(formacionEquipo.getJugador().getId());
      dto.setPosicionEnCampo(formacionEquipo.getPosicionEnCampo());


      JugadorDTO jugadorDTO = new JugadorDTO();
      jugadorDTO.setId(formacionEquipo.getJugador().getId());
      jugadorDTO.setNombre(formacionEquipo.getJugador().getNombre());
      jugadorDTO.setApellido(formacionEquipo.getJugador().getApellido());
      jugadorDTO.setImagen(formacionEquipo.getJugador().getImagen());
      jugadorDTO.setNumeroCamiseta(formacionEquipo.getJugador().getNumeroCamiseta());
      jugadorDTO.setRating(formacionEquipo.getJugador().getRating());
      jugadorDTO.setEstadoFisico(formacionEquipo.getJugador().getEstadoFisico());
      jugadorDTO.setPosicionNatural(formacionEquipo.getJugador().getPosicion());

      dto.setJugador(jugadorDTO);
      return dto;
   }

   public String toString() {
      return "JugadorID: " + this.getJugadorId() +
        "\nJugador: " + (jugador != null ? jugador.getNombre() : "Sin nombre") +
        "\nPosicion: " + posicionEnCampo;
   }



}
