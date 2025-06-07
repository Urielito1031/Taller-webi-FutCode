package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grupo_equipo")
public class GrupoEquipo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

   @MapsId("grupoId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "grupo_id", nullable = false)
   private Grupo grupo;

   @MapsId("equipoId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_id", nullable = false)
   private Equipo equipo;

}