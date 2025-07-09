package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.presentacion.dto.EquipoDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class JugadorServiceImpl implements JugadorService{


   private final JugadorRepository repository;

   @Autowired
   public JugadorServiceImpl(JugadorRepository repository) {
      this.repository = repository;
   }


   @Override
   public List<JugadorDTO> getAll(){

      List<Jugador> jugadoresEntities = repository.getAll();

      return jugadoresEntities.stream()
        .map(Jugador::convertToDTO)
        .collect(Collectors.toList());
   }

   @Override
   public List<JugadorDTO> getAllByEquipoId(Long equipoId){
   List<Jugador> jugadoresEntities = repository.getAllByEquipoId(equipoId);
   if (jugadoresEntities != null && !jugadoresEntities.isEmpty()) {
      return jugadoresEntities.stream()
        .map(Jugador::convertToDTO)
        .collect(Collectors.toList());
   }
      return null;
   }

//   @Override
//   public void cargarJugadoresAlEquipo(EquipoDTO equipo){
//      List<Jugador> listaJugadoresEntities = sortearJugadoresIniciales(14);
//
//      List<JugadorDTO> listaDeJugadoresDto = new ArrayList<>();
//
//      for(Jugador j : listaJugadoresEntities){
//         listaDeJugadoresDto.add(j.convertToDTO());
//      }
//
//      equipo.setJugadores(listaDeJugadoresDto);
//   }

   public List<Jugador> sortearJugadoresIniciales(int cantidad) {

      List<Jugador> jugadoresEquipo = new ArrayList<>();

      jugadoresEquipo.addAll(this.repository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.ARQUERO, 2));
      jugadoresEquipo.addAll(this.repository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.MEDIOCAMPISTA, 3));
      jugadoresEquipo.addAll(this.repository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DEFENSOR, 5));
      jugadoresEquipo.addAll(this.repository.sortearJugadoresIniciales(RarezaJugador.NORMAL, PosicionEnum.DELANTERO, 4));

      Collections.shuffle(jugadoresEquipo);
      return jugadoresEquipo;
   }

   @Override
   public void cargarJugadoresAlEquipo(EquipoDTO equipo){
      List<Jugador> listaJugadoresEntities = sortearJugadoresIniciales(14);

      List<JugadorDTO> listaDeJugadoresDto = new ArrayList<>();

      for(Jugador j : listaJugadoresEntities){
         listaDeJugadoresDto.add(j.convertToDTO());
      }

      equipo.setJugadores(listaDeJugadoresDto);

   }



}
