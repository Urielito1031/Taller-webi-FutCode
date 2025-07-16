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

   public String getCompatibilidadCss() {
      if (jugador == null || jugador.getPosicionNatural() == null || posicionEnCampo == null) {
         return "posicion-desconocida"; // fallback defensivo
      }

      PosicionEnum natural = jugador.getPosicionNatural();
      PosicionEnum campo = posicionEnCampo;

      if (natural == campo) {
         return "posicion-correcta";
      }

      boolean esPenalizacionParcial =
              (natural == PosicionEnum.DEFENSOR && campo == PosicionEnum.MEDIOCAMPISTA) ||
                      (natural == PosicionEnum.DELANTERO && campo == PosicionEnum.MEDIOCAMPISTA) ||
                      (natural == PosicionEnum.MEDIOCAMPISTA && campo == PosicionEnum.DELANTERO);

      boolean esPenalizacionFuerte =
              (natural == PosicionEnum.ARQUERO && campo != PosicionEnum.ARQUERO) ||
                      (natural != PosicionEnum.ARQUERO && campo == PosicionEnum.ARQUERO);

      if (esPenalizacionParcial) {
         return "posicion-parcial";
      }

      if (esPenalizacionFuerte) {
         return "posicion-incorrecta";
      }

      return "posicion-incorrecta"; // por defecto si no es ni correcta ni parcial
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
