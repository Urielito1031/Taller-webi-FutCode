package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sobre")
public class Sobre {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "tipo_sobre", nullable = false)
   private String tipoSobre;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "titulo", nullable = false, length = 100)
   private String titulo;

   @javax.validation.constraints.NotNull
   @Column(name = "precio", nullable = false, precision = 10, scale = 2)
   private Double precio;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "descripcion")
   private String descripcion;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen_url")
   private String imagenUrl;

}