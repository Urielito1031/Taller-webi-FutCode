package com.tallerwebi.dominio.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ESPECIAL")
public class SobreEspecial extends Sobre{
    @Override
    protected void setearSobre() {
        this.setTitulo("Sobre Especial");
        this.setPrecio(10000.0);
        this.setImagenUrl("sobreFutCodeEspecial.png");
    }
}
