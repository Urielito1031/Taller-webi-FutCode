package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SobreServiceImpl implements SobreService {
   private final JugadorLoader jugadorLoader;

   @Autowired
   public SobreServiceImpl(JugadorLoader jugadorLoader){
       this.jugadorLoader = jugadorLoader;
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
        return this.jugadorLoader.cargarJugadoresDesdeJSON().stream()
                .filter(j -> j.getRarezaJugador().equals(rareza))
                .limit(cantidad)
                .collect(Collectors.toList());
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
        }

        return sobre;
    }

   @Override
   public SobreDTO obtenerSobre(TipoSobre tipoSobre){
      return null;
   }

    @Override
    public List<Jugador> getJugadoresPorTipoDeSobre(TipoSobre tipoSobre) {
        List<Jugador> jugadores = new ArrayList<Jugador>();

        for (int i = 0; i < 5 ; i++){
            double num = Math.random() * 100;



        }
        return jugadores;
    }
}
