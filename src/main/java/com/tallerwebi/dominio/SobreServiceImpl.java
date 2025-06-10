package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SobreServiceImpl implements SobreService {


   public SobreServiceImpl(JugadorLoader jugadorLoader){
   }

   public List<SobreDTO> obtenerSobresDTO(){
        List<SobreDTO> sobres = new ArrayList<>();

        sobres.add(new SobreDTO("Sobre de Bronce", 2500.0, TipoSobre.BRONCE, "sobreFutCodeBronce.png"));
        sobres.add(new SobreDTO("Sobre de Plata", 5000.0, TipoSobre.PLATA, "sobreFutCodePlata.png"));
        sobres.add(new SobreDTO("Sobre de Oro", 7500.0, TipoSobre.ORO, "sobreFutCodeOro.png"));
        sobres.add(new SobreDTO("Sobre Especial", 10000.0, TipoSobre.ESPECIAL, "sobreFutCodeEspecial.png"));

        return sobres;
    }

   @Override
   public SobreDTO crearSobre(TipoSobre tipo){
      return null;
   }

   @Override
   public SobreDTO obtenerSobre(TipoSobre tipoSobre){
      return null;
   }

   @Override
   public List<Jugador> getJugadoresPorTipoDeSobre(TipoSobre tipoSobre){
      return List.of();
   }
}
