package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.presentacion.dto.EsquemaDTO;
import com.tallerwebi.presentacion.dto.PosicionJugadorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PlantillaServiceImpl implements PlantillaService {


   private final FormacionEquipoRepository formacionEquipoRepository;
   private final EquipoRepository equipoRepository;


   @Autowired
   public PlantillaServiceImpl(FormacionEquipoRepository formacionEquipoRepository,EquipoRepository equipoRepository) {
      this.formacionEquipoRepository = formacionEquipoRepository;
      this.equipoRepository = equipoRepository;
   }

   @Override
   public EsquemaDTO initPlantillaBase(Long equipoId) {
      EsquemaDTO formacion = new EsquemaDTO();
      formacion.setEquipoId(equipoId);

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



      Long equipoId = formacion.getEquipoId();
      if( equipoId == null || equipoId == 0L){
         return false;
      }
      if (!equipoExists(equipoId)) {
         return false;
      }
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

      return true;
   }

   @Override
   public EsquemaDTO getFormacionPorEquipoId(Long equipoId) {
      EsquemaDTO formacion = new EsquemaDTO();

      formacion.setEquipoId(equipoId);

      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoId);
      if (!formaciones.isEmpty()) {
         formacion.setAlineacion(convertFormacionesToAlineacion(formaciones));
         formacion.setEsquema(detectarEsquema(formaciones));
      } else {
         formacion.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
         formacion.setAlineacion(new ArrayList<>());

      }

      return formacion;
   }


   private void validateFormation(EsquemaDTO formacion){
      if (formacion == null || formacion.getAlineacion() == null) {
         throw new IllegalArgumentException("Formación inválida: datos nulos.");
      }
      if (formacion.getAlineacion().size() != 11) {
         throw new IllegalArgumentException("La formación debe tener exactamente 11 jugadores.");
      }
   }


   //tenemos que llamar al repositorio de Equipo, solo para validar
   private boolean equipoExists(Long equipoId){
      return  equipoRepository.existsById(equipoId);

   }

   private List<PosicionJugadorDTO> convertFormacionesToAlineacion(
     List<FormacionEquipo> formaciones) {
      return formaciones.stream()
          .map(PosicionJugadorDTO::convertToDTO)
          .collect(Collectors.toList());
   }
   private FormacionEsquema detectarEsquema(List<FormacionEquipo> formaciones) {


      long defensores = formaciones.stream().filter(
        fe -> fe.getPosicionEnCampo() == PosicionEnum.DEFENSOR).count();

      long mediocampistas = formaciones.stream().filter(
        fe -> fe.getPosicionEnCampo() == PosicionEnum.MEDIOCAMPISTA).count();

      long delanteros = formaciones.stream().filter(
        fe -> fe.getPosicionEnCampo() == PosicionEnum.DELANTERO).count();

      //comparar con los atributos correspondientes del enum, ya que usan
      for (FormacionEsquema esquema : FormacionEsquema.values()) {
         if (esquema.getDefensas() == defensores &&
           esquema.getMediocampistas() == mediocampistas &&
           esquema.getDelanteros() == delanteros) {
            return esquema;
         }
      }
      return FormacionEsquema.CUATRO_TRES_TRES;
   }

//   @Override
//   public void asignarPosicionesYJugadores(EsquemaDTO formacion) {
//      List<PosicionEnum> posiciones = getPosicionesPorEsquema(formacion.getEsquema());
//      List<PosicionJugadorDTO> alineacion = formacion.getAlineacion();
//      for (int i = 0; i < alineacion.size(); i++) {
//         PosicionJugadorDTO posicionJugador = alineacion.get(i);
//         posicionJugador.setPosicionEnCampo(posiciones.get(i));
//         Long jugadorId = posicionJugador.getJugadorId();
//         if (jugadorId == null) {
//            throw new IllegalArgumentException("ID de jugador es null para la posición " + i);
//         }
//         JugadorDTO jugador = new JugadorDTO();
//         jugador.setId(jugadorId);
//         posicionJugador.setJugador(jugador);
//      }
//   }
//
//   @Override
//   public List<PosicionEnum> getPosicionesPorEsquema(FormacionEsquema esquema) {
//      List<PosicionEnum> posiciones = new ArrayList<>();
//      switch (esquema) {
//         case CUATRO_TRES_TRES:
//            posiciones.addAll(Arrays.asList(
//                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.DELANTERO,
//                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
//            break;
//         case CUATRO_CUATRO_DOS:
//            posiciones.addAll(Arrays.asList(
//                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
//            break;
//         case TRES_CINCO_DOS:
//            posiciones.addAll(Arrays.asList(
//                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
//            break;
//         case CINCO_TRES_DOS:
//            posiciones.addAll(Arrays.asList(
//                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
//            break;
//         case TRES_CUATRO_TRES:
//            posiciones.addAll(Arrays.asList(
//                PosicionEnum.ARQUERO, PosicionEnum.DEFENSOR, PosicionEnum.DEFENSOR,
//                PosicionEnum.DEFENSOR, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA,
//                PosicionEnum.MEDIOCAMPISTA, PosicionEnum.MEDIOCAMPISTA, PosicionEnum.DELANTERO,
//                PosicionEnum.DELANTERO, PosicionEnum.DELANTERO));
//            break;
//         default:
//            break;
//      }
//      return posiciones;
//   }
}