package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "formacion")
public class Formacion {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "esquema", nullable = false)
   private String esquema;

}