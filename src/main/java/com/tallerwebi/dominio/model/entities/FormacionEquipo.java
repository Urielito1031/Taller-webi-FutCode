package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "formacion_equipo")
public class FormacionEquipo{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id", nullable = false)
   private Integer id;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_id", nullable = false)
   private Equipo equipo;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "jugador_id", nullable = false)
   private Jugador jugador;

   @NotNull
   @Lob
   @Column(name = "posicion_en_campo", nullable = false)
   private String posicionEnCampo;

}