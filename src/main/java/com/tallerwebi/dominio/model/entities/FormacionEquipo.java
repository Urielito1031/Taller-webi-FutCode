package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.PosicionEnum;
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
   private Long id;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_id", nullable = false)
   private Equipo equipo;

   @NotNull
   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "jugador_id", nullable = false)
   private Jugador jugador;

   @NotNull
   @Enumerated(EnumType.STRING)
   @Column(name = "posicion_en_campo", nullable = false)
   private PosicionEnum posicionEnCampo;

}