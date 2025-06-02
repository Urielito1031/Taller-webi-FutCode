package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "club")
public class Club {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "pais_id")
   private Pais pais;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "estadio_id")
   private com.tallerwebi.dominio.model.entities.Estadio estadio;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen")
   private String imagen;

}