package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.dominio.repository.SobreRepository;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SobreServiceImpl implements SobreService {
   private final SobreRepository sobreRepository;

   @Autowired
   public SobreServiceImpl(SobreRepository sobreRepository) {
        this.sobreRepository = sobreRepository;
   }

   @Override
   public List<SobreDTO> obtenerSobresDTO(){
        List<SobreDTO> sobres = new ArrayList<>();

        sobres.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png"));
        sobres.add(new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png"));
        sobres.add(new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png"));
        sobres.add(new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png"));

        return sobres;
    }

   public List<JugadorDTO> obtenerJugadoresRandomPorRareza(RarezaJugador rareza, int cantidad) {
       List<JugadorDTO> jugadoresDTO = new ArrayList<>();

       for(Jugador jugador : this.sobreRepository.getJugadoresPorRareza(rareza, cantidad)){
           jugadoresDTO.add(jugador.convertToDTO());
       }

       return jugadoresDTO;
   }

    @Override
    public SobreDTO crearSobre(TipoSobre tipo) {
       SobreDTO sobre = new SobreDTO();
       sobre.setTipoSobre(TipoSobre.valueOf(tipo.toString()));

        switch (tipo) {
            case BRONCE:
                sobre.getJugadores().addAll(this.obtenerJugadoresRandomPorRareza(RarezaJugador.NORMAL, 5));
                break;
            case PLATA:
                sobre.getJugadores().addAll(this.obtenerJugadoresRandomPorRareza(RarezaJugador.RARO, 5));
                break;
            case ORO:
                sobre.getJugadores().addAll(this.obtenerJugadoresRandomPorRareza(RarezaJugador.EPICO, 5));
                break;
            case ESPECIAL:
                sobre.getJugadores().addAll(this.obtenerJugadoresRandomPorRareza(RarezaJugador.LEYENDA, 5));
                break;
        }

        return sobre;
    }
}
