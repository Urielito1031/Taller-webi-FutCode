package com.tallerwebi.dominio;

import com.tallerwebi.dominio.service.TorneoService;
import com.tallerwebi.presentacion.dto.EquipoTorneo;
import com.tallerwebi.presentacion.dto.PartidoDTO;
import com.tallerwebi.presentacion.dto.TablaGeneralDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TorneoServiceImpl implements TorneoService {

    @Override
    public TablaGeneralDTO obtenerTablaGeneral(Long id) {
        List<EquipoTorneo> equipos = new ArrayList<>();

        equipos.add(new EquipoTorneo(1,"FARENSE", "farense.jpg",1,1,0,0,3,0,3));
        equipos.add(new EquipoTorneo(2,"OXFORD","oxford.jpg",1,1,0,0,2,0,3));
        equipos.add(new EquipoTorneo(3,"HEERENVEEN","heerenveen.jpg",1,1,0,0,2,0,3));
        equipos.add(new EquipoTorneo(4,"BOAVISTA","boavista.jpg",1,1,0,0,1,0,3));
        equipos.add(new EquipoTorneo(5,"PORTSMOUTH","portsmouth.jpg",1,0,1,0,2,2,1));
        equipos.add(new EquipoTorneo(6,"BRISTOL","bristol.jpg",1,0,1,0,2,2,1));
        equipos.add(new EquipoTorneo(7,"ROTTERDAM","rotterdam.jpg",1,0,1,0,2,2,1));
        equipos.add(new EquipoTorneo(8,"AVES","aves.jpg",1,0,1,0,2,2,1));
        equipos.add(new EquipoTorneo(9,"DERBY","derby.jpg",1,0,1,0,1,1,1));
        equipos.add(new EquipoTorneo(10,"ESTORIL","estoril.jpg",1,0,1,0,1,1,1));
        equipos.add(new EquipoTorneo(11,"BARCELOS","barcelos.jpg",1,0,1,0,0,0,1));

        TablaGeneralDTO tablaGeneralDTO = new TablaGeneralDTO();
        tablaGeneralDTO.setNombreTorneo("Champions League");
        tablaGeneralDTO.setEquipos(equipos);

        return tablaGeneralDTO;
    }

    @Override
    public List<PartidoDTO> obtenerCalendario(Long id) {
        List<PartidoDTO> partidos = new ArrayList<>();


        partidos.add(crearPartido("FARENSE", "farense.jpg", 3, "ESTORIL", "estoril.jpg", 0, 1, true));
        partidos.add(crearPartido("OXFORD", "oxford.jpg", 2, "DERBY", "derby.jpg", 0, 1, true));
        partidos.add(crearPartido("HEERENVEEN", "heerenveen.jpg", 1, "AVES", "aves.jpg", 0, 1, true));
        partidos.add(crearPartido("BOAVISTA", "boavista.jpg", 1, "ROTTERDAM", "rotterdam.jpg", 1, 1, true));
        partidos.add(crearPartido("PORTSMOUTH", "portsmouth.jpg", 0, "BRISTOL", "bristol.jpg", 0, 1, true));


        return partidos;
    }

    private PartidoDTO crearPartido(String local, String escudoLocal, Integer golesLocal,
                                    String visitante, String escudoVisitante, Integer golesVisitante,
                                    int numeroFecha, boolean jugado) {

        PartidoDTO partidoDTO = new PartidoDTO();
        partidoDTO.setEquipoLocal(local);
        partidoDTO.setEscudoLocal(escudoLocal);
        partidoDTO.setGolesLocal(golesLocal);

        partidoDTO.setEquipoVisitante(visitante);
        partidoDTO.setEscudoVisitante(escudoVisitante);
        partidoDTO.setGolesVisitante(golesVisitante);

        partidoDTO.setNumeroFecha(numeroFecha);
        partidoDTO.setJugado(jugado);
        return partidoDTO;
    }
}
