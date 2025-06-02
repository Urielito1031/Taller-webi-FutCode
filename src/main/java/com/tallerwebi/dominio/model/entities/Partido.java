package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "partido")
public class Partido {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_local_id", nullable = false)
   private Equipo equipoLocal;

   @javax.validation.constraints.NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "equipo_visitante_id", nullable = false)
   private Equipo equipoVisitante;

   @javax.validation.constraints.NotNull
   @Column(name = "fecha_encuentro", nullable = false)
   private Instant fechaEncuentro;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "estado_partido", nullable = false)
   private String estadoPartido;

   @ManyToOne(fetch = FetchType.LAZY)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "torneo_id")
   private com.tallerwebi.dominio.model.entities.Torneo torneo;

}