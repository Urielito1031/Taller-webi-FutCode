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
public class GrupoEquipoId implements Serializable {
   private static final long serialVersionUID = -9081929188563112630L;
   @javax.validation.constraints.NotNull
   @Column(name = "grupo_id", nullable = false)
   private Integer grupoId;

   @javax.validation.constraints.NotNull
   @Column(name = "equipo_id", nullable = false)
   private Integer equipoId;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
      GrupoEquipoId entity = (GrupoEquipoId) o;
      return Objects.equals(this.equipoId, entity.equipoId) &&
            Objects.equals(this.grupoId, entity.grupoId);
   }

   @Override
   public int hashCode() {
      return Objects.hash(equipoId, grupoId);
   }

}