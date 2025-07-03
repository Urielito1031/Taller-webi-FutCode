package com.tallerwebi.dominio.factory;

import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.TipoSobre;

public class SobreFactory {
    public static Sobre crearSobre(TipoSobre tipoSobre) {
        if (tipoSobre == null) throw new TipoDeSobreDesconocido();

        switch (tipoSobre) {
            case BRONCE: return new SobreBronce();
            case PLATA: return  new SobrePlata();
            case ORO: return  new SobreOro();
            case ESPECIAL: return new SobreEspecial();
            default: throw new TipoDeSobreDesconocido();
        }
    }
}
