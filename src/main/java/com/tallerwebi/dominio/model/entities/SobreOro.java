package com.tallerwebi.dominio.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ORO")
public class SobreOro extends Sobre{
    @Override
    protected void setearSobre() {
        this.setTitulo("Sobre de Oro");
        this.setPrecio(7500.0);
        this.setImagenUrl("sobreFutCodeOro.png");
    }
}
