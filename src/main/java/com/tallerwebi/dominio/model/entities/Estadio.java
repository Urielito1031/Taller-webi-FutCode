package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "estadio")
public class Estadio {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @Column(name = "capacidad")
   private Integer capacidad;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "ubicacion")
   private String ubicacion;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen_url")
   private String imagenUrl;

}