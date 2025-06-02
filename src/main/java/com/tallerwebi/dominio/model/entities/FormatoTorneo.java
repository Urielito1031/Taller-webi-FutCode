package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "formato_torneo")
public class FormatoTorneo {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "tipo", nullable = false)
   private String tipo;

   @Column(name = "rondas")
   private Integer rondas;

   @Column(name = "equipos_por_grupo")
   private Integer equiposPorGrupo;

   @Column(name = "equipos_que_avanzan")
   private Integer equiposQueAvanzan;

   @Column(name = "tipo_grupo")
   private Boolean tipoGrupo;

   @Column(name = "ida_y_vuelta")
   private Boolean idaYVuelta;

   @Column(name = "puntos_por_victoria")
   private Integer puntosPorVictoria;

   @Column(name = "puntos_por_empate")
   private Integer puntosPorEmpate;

   @Column(name = "puntos_por_derrota")
   private Integer puntosPorDerrota;

}