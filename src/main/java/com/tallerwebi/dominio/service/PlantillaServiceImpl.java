package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.enums.*;
import com.tallerwebi.presentacion.dto.ClubDTO;
import com.tallerwebi.presentacion.dto.FormacionDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlantillaServiceImpl implements PlantillaService {

   private final FormacionDTO formacion;
   private final List<FormacionDTO> formacionesGuardadas; // Lista para simular almacenamiento

   public PlantillaServiceImpl() {
      this.formacion = new FormacionDTO();
      this.formacionesGuardadas = new ArrayList<>();
   }

   @Override
   public FormacionDTO initPlantillaBase() {
      formacion.setId(1L);
      formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      formacion.setAlineacion(crearAlineacionBase());
      return formacion;
   }

   private List<PosicionJugadorDTO> crearAlineacionBase() {
      List<PosicionJugadorDTO> alineacion = new ArrayList<>();
      List<ClubDTO> clubes = crearClubes();
      List<JugadorDTO> jugadores = crearJugadores(clubes);
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.ARQUERO, jugadores.get(0)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_CENTRAL, jugadores.get(1)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_CENTRAL, jugadores.get(2)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_LATERAL, jugadores.get(3)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DEFENSOR_LATERAL, jugadores.get(4)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_CENTRAL, jugadores.get(5)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_CENTRAL, jugadores.get(6)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.VOLANTE_OFENSIVO, jugadores.get(7)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.EXTREMO, jugadores.get(8)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.EXTREMO, jugadores.get(9)));
      alineacion.add(new PosicionJugadorDTO(PosicionEnum.DELANTERO_CENTRAL, jugadores.get(10)));
      return alineacion;
   }

   private List<ClubDTO> crearClubes() {
      return Arrays.asList(
            new ClubDTO(2L, "River Plate", Pais.ARGENTINA, Estadio.MONUMENTAL, "river_plate_logo.png"),
            new ClubDTO(1L, "Boca Juniors", Pais.ARGENTINA, Estadio.BOMBONERA, "boca_juniors_logo.png"),
            new ClubDTO(3L, "Real Madrid", Pais.ESPANIA, Estadio.SANTIAGO_BERNABEU, "real_madrid_logo.png")
      );
   }

   private List<JugadorDTO> crearJugadores(List<ClubDTO> clubes) {
      return Arrays.asList(
            new JugadorDTO(1L, "Emiliano", "Martínez", "emiliano_martinez.png", 31, 1, 88.0, 95.0, Arrays.asList(PosicionEnum.ARQUERO), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(2L, "Cristian", "Romero", "cristian_romero.png", 26, 23, 87.0, 90.0, Arrays.asList(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(3L, "Nicolás", "Otamendi", "nicolas_otamendi.png", 36, 3, 85.0, 88.0, Arrays.asList(PosicionEnum.DEFENSOR_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(4L, "Marcos", "Acuña", "marcos_acuna.png", 32, 8, 84.0, 87.0, Arrays.asList(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(5L, "Gonzalo", "Montiel", "gonzalo_montiel.png", 27, 4, 83.0, 89.0, Arrays.asList(PosicionEnum.DEFENSOR_LATERAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(6L, "Rodrigo", "De Paul", "rodrigo_depaul.png", 29, 16, 86.0, 91.0, Arrays.asList(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(7L, "Alexis", "Mac Allister", "alexis_macallister.png", 25, 5, 85.0, 90.0, Arrays.asList(PosicionEnum.VOLANTE_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(8L, "Enzo", "Fernández", "enzo_fernandez.png", 23, 24, 87.0, 92.0, Arrays.asList(PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(9L, "Lionel", "Messi", "lionel_messi.png", 36, 10, 1094.0, 89.0, Arrays.asList(PosicionEnum.EXTREMO, PosicionEnum.VOLANTE_OFENSIVO), Pais.ARGENTINA, RarezaJugador.LEYENDA),
            new JugadorDTO(10L, "Julián", "Álvarez", "julian_alvarez.png", 24, 9, 86.0, 88.0, Arrays.asList(PosicionEnum.EXTREMO, PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO),
            new JugadorDTO(11L, "Lautaro", "Martínez", "lautaro_martinez.png", 26, 21, 88.0, 90.0, Arrays.asList(PosicionEnum.DELANTERO_CENTRAL), Pais.ARGENTINA, RarezaJugador.RARO)
      );
   }

   @Override
   public Boolean save(FormacionDTO formacion) {
      if (formacion == null || formacion.getEsquema() == null || formacion.getAlineacion() == null) {
         System.out.println("Error: No se puede guardar una formación inválida.");
         return false;
      }

      formacion.setId((long) (formacionesGuardadas.size() + 1));
      formacionesGuardadas.add(formacion);

      // debug
      System.out.println("Formación guardada exitosamente con ID: " + formacion.getId());
      System.out.println("Esquema: " + formacion.getEsquema().getFormacionTexto());
      formacion.getAlineacion().forEach(posicionJugador ->
            System.out.println("Jugador guardado: " + posicionJugador.getJugador().getId() +
                  ", Posición: " + posicionJugador.getPosicionEnCampo()));
      return true;
   }

   @Override
   public Boolean validarFormacion(FormacionDTO formacion) {
      return formacion.getAlineacion() != null && formacion.getAlineacion().size() == 11;
   }

   @Override
   public List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema) {
      List<PosicionEnum> posiciones = new ArrayList<>();
      switch (esquema) {
         case CUATRO_TRES_TRES:
            posiciones.addAll(Arrays.asList(
                  PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR_LATERAL, PosicionEnum.DEFENSOR_CENTRAL,
                  PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_LATERAL, PosicionEnum.VOLANTE_CENTRAL,
                  PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_OFENSIVO, PosicionEnum.EXTREMO,
                  PosicionEnum.EXTREMO, PosicionEnum.DELANTERO_CENTRAL));
            break;
         case CUATRO_CUATRO_DOS:
            posiciones.addAll(Arrays.asList(
                  PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR_LATERAL, PosicionEnum.DEFENSOR_CENTRAL,
                  PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_LATERAL, PosicionEnum.VOLANTE_CENTRAL,
                  PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_LATERAL, PosicionEnum.VOLANTE_LATERAL,
                  PosicionEnum.DELANTERO_CENTRAL, PosicionEnum.DELANTERO_CENTRAL));
            break;
         case TRES_CINCO_DOS:
            posiciones.addAll(Arrays.asList(
                  PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_CENTRAL,
                  PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.VOLANTE_LATERAL, PosicionEnum.VOLANTE_CENTRAL,
                  PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_LATERAL, PosicionEnum.VOLANTE_OFENSIVO,
                  PosicionEnum.DELANTERO_CENTRAL, PosicionEnum.DELANTERO_CENTRAL));
            break;
         case CINCO_TRES_DOS:
            posiciones.addAll(Arrays.asList(
                  PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_CENTRAL,
                  PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_LATERAL, PosicionEnum.DEFENSOR_LATERAL,
                  PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_CENTRAL,
                  PosicionEnum.DELANTERO_CENTRAL, PosicionEnum.DELANTERO_CENTRAL));
            break;
         case TRES_CUATRO_TRES:
            posiciones.addAll(Arrays.asList(
                  PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.DEFENSOR_CENTRAL,
                  PosicionEnum.DEFENSOR_CENTRAL, PosicionEnum.VOLANTE_CENTRAL, PosicionEnum.VOLANTE_CENTRAL,
                  PosicionEnum.VOLANTE_LATERAL, PosicionEnum.VOLANTE_LATERAL, PosicionEnum.EXTREMO,
                  PosicionEnum.EXTREMO, PosicionEnum.DELANTERO_CENTRAL));
            break;
         default:
            break;
      }
      return posiciones;
   }

   @Override
   public void asignarPosicionesYJugadores(FormacionDTO formacion) {
      List<PosicionEnum> posiciones = getPosicionesPorEsquema(formacion.getEsquema());
      List<PosicionJugadorDTO> alineacion = formacion.getAlineacion();
      for (int i = 0; i < alineacion.size(); i++) {
         PosicionJugadorDTO posicionJugador = alineacion.get(i);
         posicionJugador.setPosicionEnCampo(posiciones.get(i));
         Long jugadorId = posicionJugador.getJugadorId();
         if (jugadorId == null) {
            throw new IllegalArgumentException("ID de jugador es null para la posición " + i);
         }
         JugadorDTO jugador = new JugadorDTO();
         jugador.setId(jugadorId);
         posicionJugador.setJugador(jugador);
      }
   }


}