package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.EquipoRepository;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.dominio.repository.JugadorRepository;
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
   private final JugadorRepository jugadorRepository;


   @Autowired
   public PlantillaServiceImpl(FormacionEquipoRepository formacionEquipoRepository, EquipoRepository equipoRepository, JugadorRepository jugadorRepository) {
      this.formacionEquipoRepository = formacionEquipoRepository;
      this.equipoRepository = equipoRepository;
       this.jugadorRepository = jugadorRepository;
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


//   @Override
//   public Boolean save(EsquemaDTO formacion) {
//      validateFormation(formacion);
//
//      Long equipoId = formacion.getEquipoId();
//      if( equipoId == null || equipoId == 0L){
//         return false;
//      }
//      if (!equipoExists(equipoId)) {
//         return false;
//      }
//
//      Equipo equipo = equipoRepository.getById(equipoId);
//
//      formacionEquipoRepository.deleteByEquipoId(equipoId);
//
//      for (PosicionJugadorDTO posicion : formacion.getAlineacion()) {
////         Jugador jugador = new Jugador();
////         jugador.setId(posicion.getJugadorId()); // Podrías obtenerlo de un repo si necesitás los datos completos
//
//         Jugador jugador = jugadorRepository.getById(posicion.getJugadorId());
//         formacionEquipo.setJugador(jugador);
//
//         FormacionEquipo formacionEquipo = new FormacionEquipo();
//         formacionEquipo.setEquipo(equipo); // asociación real
//         formacionEquipo.setJugador(jugador); // en este caso puede andar con solo el ID
//         formacionEquipo.setPosicionEnCampo(posicion.getPosicionEnCampo());
//
//
//
////         FormacionEquipo formacionEquipo = new FormacionEquipo();
////         formacionEquipo.setEquipo(new Equipo());
////         formacionEquipo.getEquipo().setId(equipoId);
////         formacionEquipo.setJugador(new Jugador());
////         formacionEquipo.getJugador().setId(posicion.getJugadorId());
////         formacionEquipo.setPosicionEnCampo(posicion.getPosicionEnCampo());
//         formacionEquipoRepository.save(formacionEquipo);
//      }
//
//      return true;
//   }

    @Override
    public Boolean save(EsquemaDTO formacion) {
       validateFormation(formacion);

       Long equipoId = formacion.getEquipoId();
       if (equipoId == null || equipoId == 0L) {
          return false;
       }
       if (!equipoExists(equipoId)) {
          return false;
       }

       Equipo equipo = equipoRepository.getById(equipoId);

       formacionEquipoRepository.deleteByEquipoId(equipoId);

       for (PosicionJugadorDTO posicion : formacion.getAlineacion()) {
          Jugador jugador = jugadorRepository.getById(posicion.getJugadorId());

          FormacionEquipo formacionEquipo = new FormacionEquipo();
          formacionEquipo.setEquipo(equipo);
          formacionEquipo.setJugador(jugador);
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

   @Override @Transactional
   public Double getRatingOnceTitular(Long idEquipo) {
      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(idEquipo);

      if (formaciones == null || formaciones.isEmpty()) {
         return 0.0;
      }

      double total = 0.0;
      int cantidad = 0;

      for (FormacionEquipo fe : formaciones) {
         Jugador jugador = fe.getJugador();

         if (jugador.getRating() != null) {
            total += jugador.getRating();
            cantidad++;
         }
      }

      if (cantidad == 0) {
         return 0.0;
      }

      return total / cantidad;
   }
}