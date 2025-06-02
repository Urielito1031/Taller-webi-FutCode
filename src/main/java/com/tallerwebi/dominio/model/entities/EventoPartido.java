package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "evento_partido")
public class EventoPartido {
   @Id
   @Column(name = "id", nullable = false)
   private Integer id;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "tipo_evento_partido", nullable = false)
   private String tipoEventoPartido;

   @javax.validation.constraints.Size(max = 255)
   @Column(name = "descripcion")
   private String descripcion;

   @javax.validation.constraints.NotNull
   @Column(name = "minuto_de_partido", nullable = false)
   private Integer minutoDePartido;

   @ManyToOne(fetch = FetchType.LAZY)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "partido_id")
   private com.tallerwebi.dominio.model.entities.Partido partido;

}