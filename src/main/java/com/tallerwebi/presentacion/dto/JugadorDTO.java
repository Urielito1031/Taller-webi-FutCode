package com.tallerwebi.presentacion.dto;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Pais;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JugadorDTO {
   private Long id;
   private String nombre;
   private String apellido;
   private String imagen;
   private Integer edad;
   private Integer numeroCamiseta;
   private Double rating; // Ejemplo: 87.5
   private Boolean lesionado;
   private Double estadoFisico; // De 0.0 a 100.0, y varia segun cantidad de partidos
   //antes era un List<PosicionEnum>
   private PosicionEnum posicionNatural;
   private EquipoDTO equipo;


   private Pais paisOrigen;
   private RarezaJugador rarezaJugador;

   public JugadorDTO() {}
   public JugadorDTO(Long id,String  nombre, String apellido, String imagen, Integer edad,Integer numeroCamiseta, Double rating, Double estadoFisico, PosicionEnum posicion, Pais paisOrigen,RarezaJugador rarezaJugador) {
      this.id = id;
      this.nombre = nombre;
      this.apellido = apellido;
      this.imagen = imagen;
      this.edad = edad;
      this.numeroCamiseta = numeroCamiseta;
      this.rating = rating;
      this.estadoFisico = estadoFisico;
      this.posicionNatural = posicion;
      setLesionado(false);
      this.paisOrigen = paisOrigen;
      this.rarezaJugador = rarezaJugador;
   }
   public Jugador convertToEntity(JugadorDTO dto) {
      Jugador jugador = new Jugador();
      jugador.setId(dto.getId());
      jugador.setNombre(dto.getNombre());
      jugador.setApellido(dto.getApellido());
      jugador.setImagen(dto.getImagen());
      jugador.setEdad(dto.getEdad());
      jugador.setNumeroCamiseta(dto.getNumeroCamiseta());
      jugador.setRating(dto.getRating());
      jugador.setLesionado(dto.getLesionado());
      jugador.setEstadoFisico(dto.getEstadoFisico());

      jugador.setPais(dto.getPaisOrigen());

      // Enum a String
      jugador.setRarezaJugador(dto.getRarezaJugador().name());

      jugador.setPosicion(this.posicionNatural.name());

      if (dto.getEquipo() != null) {
         jugador.setEquipo(dto.getEquipo().convertToEntity(dto.getEquipo()));
      }

      return jugador;
   }

    public JugadorDTO(String nombre, RarezaJugador rarezaJugador) {
      this.nombre = nombre;
      this.rarezaJugador = rarezaJugador;
    }
}