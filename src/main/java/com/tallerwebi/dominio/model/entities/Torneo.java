package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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


   public String toString(){
      return "id: "+id+",\n nombre: "+nombre+", \ndescripcion: "+descripcion+ "\nformato: "+formatoTorneo+", \nestado: "+estado;
   }
}