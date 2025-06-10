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
   @Column(name = "id", nullable = false)
   private Integer id;

   @MapsId
   @OneToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id", nullable = false)
   private Torneo torneo;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "id_fase", nullable = false)
   private Fase idFase;

}