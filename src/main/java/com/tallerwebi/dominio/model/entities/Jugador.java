package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "jugador")
public class Jugador {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

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
   private BigDecimal rating;

   @javax.validation.constraints.NotNull
   @Column(name = "lesionado", nullable = false)
   private Boolean lesionado = false;

   @javax.validation.constraints.NotNull
   @Column(name = "estado_fisico", nullable = false, precision = 5, scale = 2)
   private BigDecimal estadoFisico;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "club_id")
   private Club club;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "pais_id")
   private Pais pais;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "rareza_jugador", nullable = false)
   private String rarezaJugador;

}