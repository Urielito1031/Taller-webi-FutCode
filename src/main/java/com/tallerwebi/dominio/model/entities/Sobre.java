package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.TipoSobre;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_sobre", discriminatorType = DiscriminatorType.STRING)
@Table(name = "sobre")
public abstract class Sobre{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

//   @Enumerated(EnumType.STRING)
//   @Column(name = "tipo_sobre", nullable = false)
//   private TipoSobre tipoSobre;

   @Column(name = "titulo", nullable = false, length = 100)
   private String titulo;

   @Column(name = "precio", nullable = false, precision = 10, scale = 2)
   private Double precio;

   @Column(name = "descripcion")
   private String descripcion;

   @Column(name = "imagen_url")
   private String imagenUrl;

   @ManyToOne (fetch = FetchType.EAGER)
   @JoinColumn (name = "usuario_id")
   private Usuario usuario;

   @OneToMany(mappedBy = "sobre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   private List<Jugador> jugadores;

   protected abstract void setearSobre();
}