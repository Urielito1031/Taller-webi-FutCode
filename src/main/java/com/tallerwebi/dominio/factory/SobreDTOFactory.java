package com.tallerwebi.dominio.factory;

import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.presentacion.dto.SobreDTO;

import java.util.ArrayList;
import java.util.List;

public class SobreDTOFactory {
    public static SobreDTO crearSobreDTO(TipoSobre tipo) {

        List<Jugador> jugadores = new ArrayList<Jugador>();
        switch (tipo) {
            case BRONCE:
                return new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png", jugadores);
            case PLATA:
                return new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png", jugadores);
            case ORO:
                return new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png", jugadores);
            case ESPECIAL:
                return new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png", jugadores);
            default:
                throw new TipoDeSobreDesconocido();
        }

    }
}
