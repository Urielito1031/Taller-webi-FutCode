package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "torneo_liga")
public class TorneoLiga{
   @Id
//   @Column(name = "id", nullable = false)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @MapsId
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "torneo_id", nullable = false, unique = true)
   private Torneo torneo;

   @Column(name = "fechas")
   private Integer fechas;

}