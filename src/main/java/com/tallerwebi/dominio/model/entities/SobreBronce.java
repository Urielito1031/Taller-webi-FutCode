package com.tallerwebi.dominio.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BRONCE")
public class SobreBronce extends Sobre{
    @Override
    protected void setearSobre() {
        this.setTitulo("Sobre de Bronce");
        this.setPrecio(2500.0);
        this.setImagenUrl("sobreFutCodeBronce.png");
    }
}
