package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "partido")
public class Partido {
//   @Id
//   @Column(name = "id", nullable = false)
//   private Long id;
//
//   @javax.validation.constraints.NotNull
//   @ManyToOne(fetch = FetchType.LAZY, optional = false)
//   @OnDelete(action = OnDeleteAction.CASCADE)
//   @JoinColumn(name = "equipo_local_id", nullable = false)
//   private Equipo equipoLocal;
//
//   @javax.validation.constraints.NotNull
//   @ManyToOne(fetch = FetchType.LAZY, optional = false)
//   @OnDelete(action = OnDeleteAction.CASCADE)
//   @JoinColumn(name = "equipo_visitante_id", nullable = false)
//   private Equipo equipoVisitante;
//
//   @javax.validation.constraints.NotNull
//   @Column(name = "fecha_encuentro", nullable = false)
//   private Instant fechaEncuentro;
//
//   @javax.validation.constraints.NotNull
//   @Lob
//   @Column(name = "estado_partido", nullable = false)
//   private String estadoPartido;
//
//   @ManyToOne(fetch = FetchType.LAZY)
//   @OnDelete(action = OnDeleteAction.CASCADE)
//   @JoinColumn(name = "torneo_id")
//   private com.tallerwebi.dominio.model.entities.Torneo torneo;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "fecha_id")
   private Fecha fecha;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "equipo_local_id", nullable = false)
   private Equipo equipoLocal;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "equipo_visitante_id", nullable = false)
   private Equipo equipoVisitante;

   @Column(name = "goles_local", nullable = false)
   private Integer golesLocal = 0;

   @Column(name = "goles_visitante", nullable = false)
   private Integer golesVisitante = 0;

   @Enumerated(EnumType.STRING)
   @Column(name = "resultado", nullable = false)
   private ResultadoPartido resultado;

}