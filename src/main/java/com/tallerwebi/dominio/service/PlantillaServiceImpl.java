package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantillaServiceImpl implements PlantillaService {

   private final FormacionEquipoRepository formacionEquipoRepository;

   @Autowired
   public PlantillaServiceImpl(FormacionEquipoRepository formacionEquipoRepository) {
      this.formacionEquipoRepository = formacionEquipoRepository;
   }

   @Override
   public EsquemaDTO initPlantillaBase() {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setId(1L);
      formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      formacion.setAlineacion(new ArrayList<>());


      Long equipoId = 1L;
      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoId);
      if (!formaciones.isEmpty()) {
         formacion.setAlineacion(convertFormacionesToAlineacion(formaciones));
         formacion.setEsquema(detectarEsquema(formaciones));
      }

      return formacion;
   }

   @Override
   public Boolean save(EsquemaDTO formacion) {
      validateFormation(formacion);


     // Long equipoId = serviceEquipo.getById(formacion.getEquipoId());
      formacionEquipoRepository.deleteByEquipoId(equipoId);

      for (PosicionJugadorDTO posicion : formacion.getAlineacion()) {
         FormacionEquipo formacionEquipo = new FormacionEquipo();
         formacionEquipo.setEquipo(new Equipo());
         formacionEquipo.getEquipo().setId(equipoId);
         formacionEquipo.setJugador(new Jugador());
         formacionEquipo.getJugador().setId(posicion.getJugadorId());
         formacionEquipo.setPosicionEnCampo(posicion.getPosicionEnCampo());
         formacionEquipoRepository.save(formacionEquipo);
      }

      System.out.println("Formación guardada exitosamente para equipo ID: " + equipoId);
      return true;
   }

   private void validateFormation(EsquemaDTO formacion){
      if (formacion == null || formacion.getEsquema() == null || formacion.getAlineacion() == null) {
         throw new IllegalArgumentException("Formación inválida: datos nulos.");
      }
      if (formacion.getAlineacion().size() != 11) {
         throw new IllegalArgumentException("La formación debe tener exactamente 11 jugadores.");
      }
   }



   @Override
   public void asignarPosicionesYJugadores(EsquemaDTO formacion) {
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

   @Override
   public List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema) {
      List<PosicionEnum> posiciones = new ArrayList<>();
      switch (esquema) {
         case CUATRO_TRES_TRES:
            posiciones.addAll(Arrays.asList(
                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.DELANTERO,
                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
            break;
         case CUATRO_CUATRO_DOS:
            posiciones.addAll(Arrays.asList(
                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
            break;
         case TRES_CINCO_DOS:
            posiciones.addAll(Arrays.asList(
                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
            break;
         case CINCO_TRES_DOS:
            posiciones.addAll(Arrays.asList(
                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
            break;
         case TRES_CUATRO_TRES:
            posiciones.addAll(Arrays.asList(
                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
                PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.DELANTERO,
                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
            break;
         default:
            break;
      }
      return posiciones;
   }

   private List<PosicionJugadorDTO> convertFormacionesToAlineacion(List<FormacionEquipo> formaciones) {
      return formaciones.stream()
          .map(fe -> {
             PosicionJugadorDTO dto = new PosicionJugadorDTO();
             dto.setJugadorId(fe.getJugador() != null ? fe.getJugador().getId() : null);
             dto.setPosicionEnCampo(fe.getPosicionEnCampo());
             if (fe.getJugador() != null) {
                JugadorDTO jugadorDTO = new JugadorDTO();
                jugadorDTO.setId(fe.getJugador().getId());
                jugadorDTO.setNombre(fe.getJugador().getNombre());
                jugadorDTO.setApellido(fe.getJugador().getApellido());
                jugadorDTO.setImagen(fe.getJugador().getImagen());
                jugadorDTO.setNumeroCamiseta(fe.getJugador().getNumeroCamiseta());
                jugadorDTO.setRating(fe.getJugador().getRating());
                jugadorDTO.setEstadoFisico(fe.getJugador().getEstadoFisico());
                jugadorDTO.setEquipo(fe.getEquipo().convertToDTO());
                dto.setJugador(jugadorDTO);
             }
             return dto;
          })
          .collect(Collectors.toList());
   }
   private FormacionEsquema detectarEsquema(List<FormacionEquipo> formaciones) {
      long defensores = formaciones.stream().filter(fe -> fe.getPosicionEnCampo() == PosicionEnum.DEFENSOR).count();
      long mediocampistas = formaciones.stream().filter(fe -> fe.getPosicionEnCampo() == PosicionEnum.MEDIOCAMPISTA).count();
      long delanteros = formaciones.stream().filter(fe -> fe.getPosicionEnCampo() == PosicionEnum.DELANTERO).count();
      long arqueros = formaciones.stream().filter(fe -> fe.getPosicionEnCampo() == PosicionEnum.ARQUERO).count();

      if (arqueros == 1 && defensores == 4 && mediocampistas == 3 && delanteros == 3) return FormacionEsquema.CUATRO_TRES_TRES;
      if (arqueros == 1 && defensores == 4 && mediocampistas == 4 && delanteros == 2) return FormacionEsquema.CUATRO_CUATRO_DOS;
      if (arqueros == 1 && defensores == 3 && mediocampistas == 5 && delanteros == 2) return FormacionEsquema.TRES_CINCO_DOS;
      if (arqueros == 1 && defensores == 5 && mediocampistas == 3 && delanteros == 2) return FormacionEsquema.CINCO_TRES_DOS;
      if (arqueros == 1 && defensores == 3 && mediocampistas == 4 && delanteros == 3) return FormacionEsquema.TRES_CUATRO_TRES;
      return FormacionEsquema.CUATRO_TRES_TRES;
   }
}