package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "fase")
public class Fase{
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @Size(max = 255)
   @NotNull
   @Column(name = "nombre", nullable = false)
   private String nombre;

}