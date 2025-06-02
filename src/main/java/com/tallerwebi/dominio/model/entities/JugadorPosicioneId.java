package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class JugadorPosicioneId implements Serializable {
   private static final long serialVersionUID = 9068271781293557641L;
   @javax.validation.constraints.NotNull
   @Column(name = "jugador_id", nullable = false)
   private Integer jugadorId;

   @javax.validation.constraints.NotNull
   @Lob
   @Column(name = "posicion_natural", nullable = false)
   private String posicionNatural;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      JugadorPosicioneId entity = (JugadorPosicioneId) o;
      return Objects.equals(this.jugadorId, entity.jugadorId) &&
            Objects.equals(this.posicionNatural, entity.posicionNatural);
   }

   @Override
   public int hashCode() {
      return Objects.hash(jugadorId, posicionNatural);
   }

}