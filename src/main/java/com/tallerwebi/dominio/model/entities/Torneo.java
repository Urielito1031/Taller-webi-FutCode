package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.presentacion.dto.FormatoTorneoDTO;
import com.tallerwebi.presentacion.dto.TorneoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "torneo")
public class Torneo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @javax.validation.constraints.Size(max = 100)
   @javax.validation.constraints.NotNull
   @Column(name = "nombre", nullable = false, length = 100)
   private String nombre;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "descripcion")
   private String descripcion;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "formato_torneo_id")
   private FormatoTorneo formatoTorneo;

   @Enumerated(EnumType.STRING)
   @Column(name = "estado", nullable = false)
   private EstadoTorneoEnum estado;

   @Column(name = "premio_monedas")
   private Double premioMonedas;

   @Column(name = "capacidad_maxima")
   private Integer capacidadMaxima;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "torneo_liga_id", unique = true, nullable = true)
   private TorneoLiga torneoLiga;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "torneo_copa_id", unique = true, nullable = true)
   private TorneoCopa torneoCopa;

   @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
   @OrderBy("numeroDeFecha ASC")
   private Set<Fecha> fechas = new TreeSet<>();

   @OneToMany(mappedBy = "torneo", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<EquipoTorneo> equipos = new HashSet<>();

   public String toString() {
      return "id: " + id + ",\n nombre: " + nombre + ", \ndescripcion: " + descripcion + "\nformato: " + formatoTorneo
            + ", \nestado: " + estado;
   }

   public TorneoDTO convertToDTO() {
      TorneoDTO dto = new TorneoDTO();
      dto.setId(this.getId());
      dto.setNombre(this.getNombre());
      dto.setEstado(this.getEstado());
      dto.setDescripcion(this.getDescripcion());
      dto.setCapacidadMaxima(this.getCapacidadMaxima());

      FormatoTorneo formato = this.getFormatoTorneo();
      if (formato != null) {
         FormatoTorneoDTO formatoDTO = new FormatoTorneoDTO();
         formatoDTO.setTipo(formato.getTipo());
         dto.setFormatoTorneo(formatoDTO);
      }
      // Si formato es null, no lo setea
      return dto;
   }
}