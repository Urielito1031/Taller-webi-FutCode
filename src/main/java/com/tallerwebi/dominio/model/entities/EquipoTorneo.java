package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "equipo_torneo")
public class EquipoTorneo {
   @Id
   @Column(name = "id", nullable = false)
   private Long id;

   @javax.validation.constraints.NotNull
   @Column(name = "posicion", nullable = false)
   private Integer posicion;


   @javax.validation.constraints.NotNull
   @Column(name = "partidos_jugados", nullable = false)
   private Integer partidosJugados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_ganados", nullable = false)
   private Integer partidosGanados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_empatados", nullable = false)
   private Integer partidosEmpatados;

   @javax.validation.constraints.NotNull
   @Column(name = "partidos_perdidos", nullable = false)
   private Integer partidosPerdidos;

   @javax.validation.constraints.NotNull
   @Column(name = "goles_a_favor", nullable = false)
   private Integer golesAFavor;

   @javax.validation.constraints.NotNull
   @Column(name = "goles_en_contra", nullable = false)
   private Integer golesEnContra;

   @javax.validation.constraints.NotNull
   @Column(name = "puntos", nullable = false)
   private Integer puntos;

   @ManyToOne(fetch = FetchType.LAZY)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "torneo_id")
   private com.tallerwebi.dominio.model.entities.Torneo torneo;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "equipo_id", nullable = false)
   private Equipo equipo;


   public EquipoTorneoDTO convertToDTO() {
      EquipoTorneoDTO equipoTorneoDTO = new EquipoTorneoDTO();
      equipoTorneoDTO.setId(this.id);
      equipoTorneoDTO.setPosicion(this.posicion);
      equipoTorneoDTO.setPartidosJugados(this.partidosJugados);
      equipoTorneoDTO.setPartidosGanados(this.partidosGanados);
      equipoTorneoDTO.setPartidosEmpatados(this.partidosEmpatados);
      equipoTorneoDTO.setPartidosPerdidos(this.partidosPerdidos);
      equipoTorneoDTO.setGolesAFavor(this.golesAFavor);
      equipoTorneoDTO.setGolesEnContra(this.golesEnContra);
      equipoTorneoDTO.setPuntos(this.puntos);
      equipoTorneoDTO.setEquipoDTO(this.equipo.convertToDTO());
      equipoTorneoDTO.setTorneoDTO(this.torneo.convertToDTO());
      return equipoTorneoDTO;
   }

}