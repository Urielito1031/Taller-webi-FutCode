package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


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

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "equipo_id")
   private Equipo equipo;


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



      if (this.getEquipo() != null) {
         EquipoDTO equipoDTO = new EquipoDTO();
         dto.setEquipo(equipoDTO.convertFromEntity(this.getEquipo()));
      }

      return dto;
   }

   public String toString(){
      return "JugadorID: "+ this.id+ "\nNombre: "+ this.nombre;
   }



}