package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "jugador_posiciones")
public class JugadorPosicione {
   @EmbeddedId
   private JugadorPosicioneId id;

   @MapsId("jugadorId")
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "jugador_id", nullable = false)
   private Jugador jugador;

}