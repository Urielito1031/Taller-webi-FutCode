package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.dominio.repository.SobreRepository;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SobreServiceImpl  implements SobreService {

    @Autowired
    private SobreRepository sobreRepository;

    private final JugadorLoader jugadorLoader;

    @Autowired
    public SobreServiceImpl(SobreRepository sobreRepository, JugadorLoader jugadorLoader) {
        this.sobreRepository = sobreRepository;
        this.jugadorLoader = jugadorLoader;
    }

    public SobreServiceImpl( JugadorLoader jugadorLoader) {
        this.jugadorLoader = jugadorLoader;
    }




    @Override
    public SobreDTO obtenerSobre(TipoSobre tipoSobre){
        List<JugadorDTO> jugadores = new ArrayList<>();

        jugadores.add(new JugadorDTO(1L,"Lionel", "Messi", "lionel_messi.png", 36, 10, 94.5, 95.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(2L,"Emiliano", "Martínez", "emiliano_martinez.png", 31, 23, 88.0, 92.0,
                List.of(PosicionEnum.ARQUERO), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(3L,"Cristian", "Romero", "cristian_romero.png", 25, 13, 84.5, 89.0,
                List.of(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(4L,"Rodrigo", "De Paul", "rodrigo_depaul.png", 29, 7, 86.0, 90.0,
                List.of(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(5L,"Enzo", "Fernández", "enzo_fernandez.png", 22, 24, 85.0, 88.0,
                List.of(PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(6L,"Julián", "Álvarez", "julian_alvarez.png", 23, 9, 86.5, 91.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(7L,"Nicolás", "Otamendi", "nicolas_otamendi.png", 35, 19, 83.0, 87.0,
                List.of(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(8L,"Alexis", "Mac Allister", "alexis_macallister.png", 24, 20, 84.0, 88.0,
                List.of(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));

        jugadores.add(new JugadorDTO(9L,"Lautaro", "Martínez", "lautaro_martinez.png", 26, 22, 89.0, 93.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO));
        // Cargar el DTO del sobre
        SobreDTO sobreDTO = new SobreDTO();

        sobreDTO.setTipoSobre(tipoSobre);
        sobreDTO.setJugadores(jugadores);

        return sobreDTO;
    }

    public List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema) {
        List<PosicionEnum> posiciones = new ArrayList<>();
        switch (esquema) {
            case CUATRO_TRES_TRES:
                posiciones.add(PosicionEnum.ARQUERO);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_OFENSIVO);
                posiciones.add(PosicionEnum.EXTREMO);
                posiciones.add(PosicionEnum.EXTREMO);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                break;
            case CUATRO_CUATRO_DOS:
                posiciones.add(PosicionEnum.ARQUERO);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                break;
            case TRES_CINCO_DOS:
                posiciones.add(PosicionEnum.ARQUERO);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_OFENSIVO);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                break;
            case CINCO_TRES_DOS:
                posiciones.add(PosicionEnum.ARQUERO);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.DEFENSOR_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                break;
            case TRES_CUATRO_TRES:
                posiciones.add(PosicionEnum.ARQUERO);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.DEFENSOR_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_CENTRAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.VOLANTE_LATERAL);
                posiciones.add(PosicionEnum.EXTREMO);
                posiciones.add(PosicionEnum.EXTREMO);
                posiciones.add(PosicionEnum.DELANTERO_CENTRAL);
                break;
        }
        return posiciones;
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

    public List<JugadorDTO> obtenerJugadoresRandomPorRareza(RarezaJugador rareza, int cantidad) {
        return this.jugadorLoader.cargarJugadoresDesdeJSON().stream()
                .filter(j -> j.getRarezaJugador().equals(rareza))
                .limit(cantidad)
                .collect(Collectors.toList());
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
