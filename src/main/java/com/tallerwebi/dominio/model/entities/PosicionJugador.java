package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "posicion_jugador")
public class PosicionJugador {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "posicion_en_campo", nullable = false)
   private String posicionEnCampo;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "jugador_id", nullable = false)
   private Jugador jugador;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "formacion_id", nullable = false)
   private Formacion formacion;

}