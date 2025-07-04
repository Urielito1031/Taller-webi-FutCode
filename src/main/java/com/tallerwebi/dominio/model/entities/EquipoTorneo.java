package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.presentacion.dto.EquipoTorneoDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "equipo_torneo")
public class EquipoTorneo {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "posicion")
   private Integer posicion = 0;

   @Column(name = "partidos_jugados")
   private Integer partidosJugados = 0;

   @Column(name = "partidos_ganados")
   private Integer partidosGanados= 0;

   @Column(name = "partidos_empatados")
   private Integer partidosEmpatados= 0;

   @Column(name = "partidos_perdidos")
   private Integer partidosPerdidos= 0;

   @Column(name = "goles_a_favor")
   private Integer golesAFavor= 0;

   @Column(name = "goles_en_contra")
   private Integer golesEnContra= 0;

   @Column(name = "puntos")
   private Integer puntos= 0;

   @ManyToOne(fetch = FetchType.EAGER)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "torneo_id")
   private com.tallerwebi.dominio.model.entities.Torneo torneo;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "equipo_id")
   private Equipo equipo;

   public EquipoTorneo() {}
   public EquipoTorneo(Equipo equipo, Torneo torneo) {
      this.equipo = equipo;
      this.torneo = torneo;
   }
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
      equipoTorneoDTO.setEquipo(this.equipo.convertToDTO());
      equipoTorneoDTO.setTorneo(this.torneo.convertToDTO());
      return equipoTorneoDTO;
   }

   @Override
   public String toString() {
      return "EquipoTorneo{" +
        "id=" + id +
        ", posicion=" + posicion +
        ", partidosJugados=" + partidosJugados +
        ", partidosGanados=" + partidosGanados +
        ", partidosEmpatados=" + partidosEmpatados +
        ", partidosPerdidos=" + partidosPerdidos +
        ", golesAFavor=" + golesAFavor +
        ", golesEnContra=" + golesEnContra +
        ", puntos=" + puntos +
        ", torneo=" + (torneo != null ? torneo.getId() : null) +
        ", equipo=" + (equipo != null ? equipo.getId() : null) +
        '}';
   }
}
