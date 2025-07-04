package com.tallerwebi.dominio.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PLATA")
public class SobrePlata extends Sobre{
    @Override
    protected void setearSobre() {
        this.setTitulo("Sobre de Plata");
        this.setPrecio(5000.0);
        this.setImagenUrl("sobreFutCodePlata.png");
    }
}
