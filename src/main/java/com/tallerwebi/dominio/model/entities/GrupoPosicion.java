package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grupo_posicion")
public class GrupoPosicion {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "grupo_id", nullable = false)
   private Grupo grupo;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_id", nullable = false)
   private Equipo equipo;

   @Column(name = "partidos_jugados")
   private Integer partidosJugados;

   @Column(name = "partidos_ganados")
   private Integer partidosGanados;

   @Column(name = "partidos_empatados")
   private Integer partidosEmpatados;

   @Column(name = "partidos_perdidos")
   private Integer partidosPerdidos;

   @Column(name = "goles_a_favor")
   private Integer golesAFavor;

   @Column(name = "goles_en_contra")
   private Integer golesEnContra;

   @Column(name = "puntos")
   private Integer puntos;

}