package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "partido")
public class Partido {
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

   @Column(name = "fecha_encuentro", nullable = false)
   private LocalDateTime fechaEncuentro;

}