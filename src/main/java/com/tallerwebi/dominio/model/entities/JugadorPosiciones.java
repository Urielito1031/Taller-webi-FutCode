package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "jugador_posiciones")
public class JugadorPosiciones {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "posicion_id")
   private Long id;

   @Column(name = "posicion_natural", nullable = false)
   @Enumerated(EnumType.STRING) // Mapea el enum como String en la base de datos
   private PosicionEnum posicionNatural;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "jugador_id", nullable = false)
   private Jugador jugador;

}