package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "jugador")
public class Jugador {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 50)
   private String nombre;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "apellido", nullable = false, length = 50)
   private String apellido;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen")
   private String imagen;

   @javax.validation.constraints.NotNull
   @Column(name = "edad", nullable = false)
   private Integer edad;

   @javax.validation.constraints.NotNull
   @Column(name = "numero_camiseta", nullable = false)
   private Integer numeroCamiseta;

   @javax.validation.constraints.NotNull
   @Column(name = "rating", nullable = false, precision = 4, scale = 1)
   private Double rating;

   @javax.validation.constraints.NotNull
   @Column(name = "lesionado", nullable = false)
   private Boolean lesionado = false;

   @javax.validation.constraints.NotNull
   @Column(name = "estado_fisico", nullable = false, precision = 5, scale = 2)
   private Double estadoFisico;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "pais_id")
   private Pais pais;

   @javax.validation.constraints.NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "rareza_jugador", nullable = false)
   private RarezaJugador rarezaJugador;

   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "posicion", nullable = false)
   private PosicionEnum posicion;

   @ManyToMany(mappedBy = "jugadores")
   private List<Equipo> equipos = new ArrayList<>();

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "sobre_id")
   private Sobre sobre;

   public Jugador(String nombre, RarezaJugador rarezaJugador) {
      this.nombre = nombre;
      this.rarezaJugador = rarezaJugador;
   }

   public Jugador() {

   }

   public static Jugador convertToEntity(JugadorDTO jugadorDTO) {
      if (jugadorDTO == null) {
         return null;
      }

      Jugador jugador = new Jugador();
      jugador.setId(jugadorDTO.getId());
      jugador.setNombre(jugadorDTO.getNombre());
      jugador.setApellido(jugadorDTO.getApellido());
      jugador.setImagen(jugadorDTO.getImagen());
      jugador.setEdad(jugadorDTO.getEdad());
      jugador.setNumeroCamiseta(jugadorDTO.getNumeroCamiseta());
      jugador.setRating(jugadorDTO.getRating());
      jugador.setLesionado(jugadorDTO.getLesionado() != null ? jugadorDTO.getLesionado() : false);
      jugador.setEstadoFisico(jugadorDTO.getEstadoFisico());
      jugador.setRarezaJugador(jugadorDTO.getRarezaJugador());
      jugador.setPosicion(jugadorDTO.getPosicionNatural());

      if (jugadorDTO.getPaisOrigen() != null) {
         Pais pais = new Pais();
         pais.setId(jugadorDTO.getPaisOrigen().getId());
         pais.setNombre(jugadorDTO.getPaisOrigen().getNombre());
         jugador.setPais(pais);
      }

      // Manejar la relación Many-to-Many con equipos
      if (jugadorDTO.getEquipos() != null && !jugadorDTO.getEquipos().isEmpty()) {
         for (EquipoDTO equipoDTO : jugadorDTO.getEquipos()) {
            Equipo equipo = new Equipo();
            equipo.setId(equipoDTO.getId());
            equipo.setNombre(equipoDTO.getNombre());
            jugador.getEquipos().add(equipo);
         }
      }

      return jugador;
   }

   public JugadorDTO convertToDTO() {
      JugadorDTO dto = new JugadorDTO();
      dto.setId(this.getId());
      dto.setNombre(this.getNombre());
      dto.setApellido(this.getApellido());
      dto.setImagen(this.getImagen());
      dto.setEdad(this.getEdad());
      dto.setNumeroCamiseta(this.getNumeroCamiseta());
      dto.setRating(this.getRating());
      dto.setEstadoFisico(this.getEstadoFisico());
      dto.setLesionado(this.getLesionado());
      dto.setPaisOrigen(this.getPais());

      dto.setRarezaJugador(this.getRarezaJugador());
      dto.setPosicionNatural(this.getPosicion());

      // Convertir la lista de equipos
      if (this.getEquipos() != null && !this.getEquipos().isEmpty()) {
         for (Equipo equipo : this.getEquipos()) {
            dto.getEquipos().add(equipo.convertToDTO());
         }
      }

      return dto;
   }

   public String toString() {
      return "JugadorID: " + this.id + "\nNombre: " + this.nombre;
   }

}