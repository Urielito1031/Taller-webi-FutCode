package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sobre")
public class Sobre {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   @Column(name = "id", nullable = false)
   private Long id;

//   @javax.validation.constraints.NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "tipo_sobre", nullable = false)
   private TipoSobre tipoSobre;

//   @javax.validation.constraints.Size(max = 100)
//   @javax.validation.constraints.NotNull
   @Column(name = "titulo", nullable = false, length = 100)
   private String titulo;

//   @javax.validation.constraints.NotNull
   @Column(name = "precio", nullable = false, precision = 10, scale = 2)
   private Double precio;

//   @javax.validation.constraints.Size(max = 255)
   @Column(name = "descripcion")
   private String descripcion;

//   @javax.validation.constraints.Size(max = 255)
   @Column(name = "imagen_url")
   private String imagenUrl;

   @ManyToOne (fetch = FetchType.EAGER)
   @JoinColumn (name = "usuario_id")
   private Usuario usuario;

   @OneToMany(mappedBy = "sobre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<Jugador> jugadores;

}