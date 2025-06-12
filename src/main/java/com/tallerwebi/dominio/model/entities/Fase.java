package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fase")
public class Fase{
   @Id
   @Column(name = "id", nullable = false)
   private Long id;

   @Size(max = 255)
   @NotNull
   @Column(name = "nombre", nullable = false)
   private String nombre;

   @OneToMany(mappedBy = "fase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<FormatoTorneo> formatoTorneos = new ArrayList<>();



}