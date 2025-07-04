package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "esquema")
public class Esquema{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Long id;

   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "esquema", nullable = false)
   private FormacionEsquema esquema;

   public Esquema() {}

}