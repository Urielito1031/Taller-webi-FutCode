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
   @Column(name = "id", nullable = false)
   private Integer id;

   @MapsId
   @OneToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id", nullable = false)
   private Torneo torneo;

   @Column(name = "fechas")
   private Integer fechas;

}