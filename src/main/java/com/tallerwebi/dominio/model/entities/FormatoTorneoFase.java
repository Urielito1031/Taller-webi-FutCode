package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "formato_torneo_fases")
public class FormatoTorneoFase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
   @MapsId("formatoTorneoId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "formato_torneo_id", nullable = false)
   private FormatoTorneo formatoTorneo;

   @Column(name = "equipos")
   private Integer equipos;

}