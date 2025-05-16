package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.*;
import com.tallerwebi.presentacion.dto.ClubDTO;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import lombok.experimental.StandardException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class PlantillaServiceImpl {

   private FormacionDTO formacion;

   public PlantillaServiceImpl() {

   //a futuro para inyectar dependencias con el repository;
   }
   public FormacionDTO initPlantillaBase() {
      // Crear una nueva formación 4-3-3
      formacion = new FormacionDTO();
      formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      formacion.setId(1L);

      // Lista de posiciones y jugadores para una formación 4-3-3
      List<PosicionJugadorDTO> alineacion = new ArrayList<>();

      // Crear clubes
      ClubDTO riverPlate = new ClubDTO();
      riverPlate.setId(2L);
      riverPlate.setNombre("River Plate");
      riverPlate.setPais(Pais.ARGENTINA);
      riverPlate.setEstadio(Estadio.MONUMENTAL);
      riverPlate.setImagen("river_plate_logo.png");

      ClubDTO bocaJuniors = new ClubDTO();
      bocaJuniors.setId(1L);
      bocaJuniors.setNombre("Boca Juniors");
      bocaJuniors.setPais(Pais.ARGENTINA);
      bocaJuniors.setEstadio(Estadio.BOMBONERA);
      bocaJuniors.setImagen("boca_juniors_logo.png");


      ClubDTO realMadrid = new ClubDTO();
      realMadrid.setId(3L);
      realMadrid.setNombre("Real Madrid");
      realMadrid.setPais(Pais.ESPANIA);
      realMadrid.setEstadio(Estadio.SANTIAGO_BERNABEU);
      realMadrid.setImagen("real_madrid_logo.png");

      // Crear jugadores para la formación 4-3-3
      // Arquero
      JugadorDTO arquero = new JugadorDTO("Emiliano", "Martínez", "emiliano_martinez.png", 31,1, 88.0, 95.0,
            Arrays.asList(PosicionEnum.ARQUERO), Pais.ARGENTINA, RarezaJugador.RARO);

      // Defensores
      JugadorDTO defensor1 = new JugadorDTO("Cristian", "Romero", "cristian_romero.png", 26,23, 87.0, 90.0,
            Arrays.asList(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO);
      defensor1.setClubActual(bocaJuniors);

      JugadorDTO defensor2 = new JugadorDTO("Nicolás", "Otamendi", "nicolas_otamendi.png", 36,3, 85.0, 88.0,
            Arrays.asList(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO);
      defensor2.setClubActual(riverPlate);

      JugadorDTO defensor3 = new JugadorDTO("Marcos", "Acuña", "marcos_acuna.png", 32,8, 84.0, 87.0,
            Arrays.asList(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA, RarezaJugador.RARO);
      defensor3.setClubActual(realMadrid);

      JugadorDTO defensor4 = new JugadorDTO("Gonzalo", "Montiel", "gonzalo_montiel.png", 27,4, 83.0, 89.0,
            Arrays.asList(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA, RarezaJugador.RARO);
      defensor4.setClubActual(bocaJuniors);

      // Mediocampistas
      JugadorDTO volante1 = new JugadorDTO("Rodrigo", "De Paul", "rodrigo_depaul.png", 29,16, 86.0, 91.0,
            Arrays.asList(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.EPICO);
      volante1.setClubActual(realMadrid);

      JugadorDTO volante2 = new JugadorDTO("Alexis", "Mac Allister", "alexis_macallister.png", 25,5, 85.0, 90.0,
            Arrays.asList(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO);
      volante2.setClubActual(riverPlate);

      JugadorDTO volante3 = new JugadorDTO("Enzo", "Fernández", "enzo_fernandez.png", 23,24, 87.0, 92.0,
            Arrays.asList(PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA, RarezaJugador.EPICO);
      volante3.setClubActual(bocaJuniors);

      // Delanteros
      JugadorDTO extremo1 = new JugadorDTO("Lionel", "Messi", "lionel_messi.png", 36, 10,1094.0, 89.0,
            Arrays.asList(PosicionEnum.EXTREMO, PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA, RarezaJugador.LEYENDA);
      extremo1.setClubActual(realMadrid);

      JugadorDTO extremo2 = new JugadorDTO("Julián", "Álvarez", "julian_alvarez.png", 24,9, 86.0, 88.0,
            Arrays.asList(PosicionEnum.EXTREMO, PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.EPICO);
      extremo2.setClubActual(riverPlate);

      JugadorDTO delantero = new JugadorDTO("Lautaro", "Martínez", "lautaro_martinez.png", 26,21, 88.0, 90.0,
            Arrays.asList(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.EPICO);
      delantero.setClubActual(bocaJuniors);

      // Asignar jugadores a posiciones
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.ARQUERO, arquero));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_CENTRAL, defensor1));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_CENTRAL, defensor2));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_LATERAL, defensor3));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_LATERAL, defensor4));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_CENTRAL, volante1));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_CENTRAL, volante2));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_OFENSIVO, volante3));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.EXTREMO, extremo1));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.EXTREMO, extremo2));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DELANTERO_CENTRAL, delantero));

      // Asignar la alineación a la formación
      formacion.setAlineacion(alineacion);

      return formacion;
   }
}
