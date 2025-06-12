package com.tallerwebi.dominio.model.entities;

import com.tallerwebi.dominio.model.enums.TipoFormato;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "formato_torneo")
public class FormatoTorneo {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Enumerated(EnumType.STRING)
   @Column(name = "tipo", nullable = false)
   private TipoFormato tipo;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "fase_id")
   private Fase fase;


   public String toString(){
      return "Tipo: " + tipo;
   }
}