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
@Table(name = "pais")
public class Pais {
   @Id
   @Column(name = "id", nullable = false)
   private Long id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @javax.validation.constraints.Size(max = 2)
   @javax.validation.constraints.NotNull
   @Column(name = "codigo_iso", nullable = false, length = 2)
   private String codigoIso;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "bandera_url")
   private String banderaUrl;

}