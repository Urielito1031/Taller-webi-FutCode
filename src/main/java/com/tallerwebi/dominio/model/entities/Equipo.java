package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "equipo")
public class Equipo {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "club_id")
   private Club club;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "formacion_id")
   private com.tallerwebi.dominio.model.entities.Formacion formacion;

}