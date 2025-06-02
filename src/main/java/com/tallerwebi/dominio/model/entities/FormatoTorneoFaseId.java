package com.tallerwebi.dominio.model.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class FormatoTorneoFaseId implements Serializable {
   private static final long serialVersionUID = -8810574188916080004L;
   @javax.validation.constraints.NotNull
   @Column(name = "formato_torneo_id", nullable = false)
   private Integer formatoTorneoId;

   @javax.validation.constraints.Size(max = 50)
   @javax.validation.constraints.NotNull
   @Column(name = "fase", nullable = false, length = 50)
   private String fase;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      FormatoTorneoFaseId entity = (FormatoTorneoFaseId) o;
      return Objects.equals(this.fase, entity.fase) &&
            Objects.equals(this.formatoTorneoId, entity.formatoTorneoId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(fase, formatoTorneoId);
   }

}