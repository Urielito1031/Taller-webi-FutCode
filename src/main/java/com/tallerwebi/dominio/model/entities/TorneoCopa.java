package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "torneo_copa")
public class TorneoCopa{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   @Column(name = "id", nullable = false)
   private Long id;

   @MapsId
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "torneo_id", nullable = false, unique = true)
   private Torneo torneo;

//   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "fase_id", nullable = false)
   private Fase fase;

}