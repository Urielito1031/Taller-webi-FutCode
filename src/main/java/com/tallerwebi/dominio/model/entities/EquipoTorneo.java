package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "equipo_torneo")
public class EquipoTorneo {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Column(name = "posicion", nullable = false)
   private Integer posicion;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "nombre_imagen")
   private String nombreImagen;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_jugados", nullable = false)
   private Integer partidosJugados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_ganados", nullable = false)
   private Integer partidosGanados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_empatados", nullable = false)
   private Integer partidosEmpatados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_perdidos", nullable = false)
   private Integer partidosPerdidos;

   @javax.validation.constraints.NotNull
   @Column(name = "goles_a_favor", nullable = false)
   private Integer golesAFavor;

   @javax.validation.constraints.NotNull
   @Column(name = "goles_en_contra", nullable = false)
   private Integer golesEnContra;

   @javax.validation.constraints.NotNull
   @Column(name = "puntos", nullable = false)
   private Integer puntos;

   @ManyToOne(fetch = FetchType.LAZY)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "torneo_id")
   private com.tallerwebi.dominio.model.entities.Torneo torneo;

}