package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "grupo")
public class Grupo {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 50)
   private String nombre;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "torneo_id", nullable = false)
   private com.tallerwebi.dominio.model.entities.Torneo torneo;

}