package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.PosicionEnum;
import com.tallerwebi.dominio.model.RarezaJugador;
import com.tallerwebi.infraestructura.JugadorLoader;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SorteoServiceImpl {

    private final JugadorLoader jugadorLoader;

    public SorteoServiceImpl(JugadorLoader jugadorLoader) {
        this.jugadorLoader = jugadorLoader;
    }

    public List<JugadorDTO> sortearEquipoInicial(){
        List<JugadorDTO> equipoNuevoDeJugadores = this.jugadorLoader.cargarJugadoresDesdeJSON();
        List<JugadorDTO> equipoFinal = new ArrayList<>();

        int numeroDeArqueros = 2;
        int numeroDeDefensorCentral = 3;
        int numeroDeDefensorLateral = 2;
        int numeroDeMedioCampistaCentral = 2;
        int numeroDeMedioCampistaOfensivo = 1;
        int numeroDeMedioCampistaExtremo = 1;
        int numeroDeDelanteros = 3;

        for(JugadorDTO j : equipoNuevoDeJugadores){
            if(equipoFinal.size() <= 14){
                if(j.getPosicionNatural().contains(PosicionEnum.ARQUERO) && numeroDeArqueros != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeArqueros--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.DEFENSOR_CENTRAL) && numeroDeDefensorCentral != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeDefensorCentral--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.DEFENSOR_LATERAL) && numeroDeDefensorLateral != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeDefensorLateral--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.VOLANTE_CENTRAL) && numeroDeMedioCampistaCentral != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeMedioCampistaCentral--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.VOLANTE_OFENSIVO) && numeroDeMedioCampistaOfensivo != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeMedioCampistaOfensivo--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.EXTREMO) && numeroDeMedioCampistaExtremo != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeMedioCampistaExtremo--;
                }

                if(j.getPosicionNatural().contains(PosicionEnum.DELANTERO_CENTRAL) && numeroDeDelanteros != 0 && j.getRarezaJugador().equals(RarezaJugador.NORMAL) && !buscarJugadorEnElEquipo(j, equipoNuevoDeJugadores)){
                    equipoFinal.add(j);
                    numeroDeDelanteros--;
                }
            } else {
                break;
            }
        }
        return equipoFinal;
    }

    private Boolean buscarJugadorEnElEquipo(JugadorDTO j, List<JugadorDTO> listaDeJugadores){
        for (JugadorDTO jugador : listaDeJugadores) {
            if(jugador.getId().equals(j.getId())){
                return true;
            }
            break;
        }
        return false;
    }

}


