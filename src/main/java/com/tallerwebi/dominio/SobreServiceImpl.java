package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.Pais;
import com.tallerwebi.dominio.model.PosicionEnum;
import com.tallerwebi.dominio.model.TipoSobre;
import com.tallerwebi.dominio.service.SobreService;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SobreServiceImpl implements SobreService {
    @Override
    public SobreDTO obtenerSobre(TipoSobre tipoSobre){
        List<JugadorDTO> jugadores = new ArrayList<>();

        jugadores.add(new JugadorDTO("Lionel", "Messi", "lionel_messi.png", 36, 10, 94.5, 95.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Emiliano", "Martínez", "emiliano_martinez.png", 31, 23, 88.0, 92.0,
                List.of(PosicionEnum.ARQUERO), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Cristian", "Romero", "cristian_romero.png", 25, 13, 84.5, 89.0,
                List.of(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Rodrigo", "De Paul", "rodrigo_depaul.png", 29, 7, 86.0, 90.0,
                List.of(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Enzo", "Fernández", "enzo_fernandez.png", 22, 24, 85.0, 88.0,
                List.of(PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Julián", "Álvarez", "julian_alvarez.png", 23, 9, 86.5, 91.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Nicolás", "Otamendi", "nicolas_otamendi.png", 35, 19, 83.0, 87.0,
                List.of(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Alexis", "Mac Allister", "alexis_macallister.png", 24, 20, 84.0, 88.0,
                List.of(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA));

        jugadores.add(new JugadorDTO("Lautaro", "Martínez", "lautaro_martinez.png", 26, 22, 89.0, 93.0,
                List.of(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA));
        Long indice = 1L;
        for(JugadorDTO jugador : jugadores){
            jugador.setId(indice);
            indice++;
        }
        // Cargar el DTO del sobre
        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(tipoSobre);
        sobreDTO.setJugadores(jugadores);

        return sobreDTO;
    }
}
