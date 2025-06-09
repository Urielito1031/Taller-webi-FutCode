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



   public String toString(){
      return "Tipo: " + tipo;
   }
}