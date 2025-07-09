package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
@Transactional
@Rollback
public class FormacionEquipoRepositoryTest {

   @Autowired
   private FormacionEquipoRepository formacionEquipoRepository;

   @Autowired
   private org.hibernate.SessionFactory sessionFactory; // Para obtener el session y persistir entidades de prueba



//   @Test
//   public void deberiaEncontrarFormacionesDeUnEquipoExistenteConFormacion() {
//      // given: Un equipo con ID 100 que tiene 3 formaciones en db_futcodeTest.sql
//      Long equipoIdConFormaciones = 100L;
//
//      // when: Se buscan las formaciones de ese equipo
//      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdConFormaciones);
//
//      // then: Se deberían encontrar 3 formaciones
//      assertNotNull(formaciones);
//      assertThat(formaciones, hasSize(3));
//
//      // Opcional: Verificar que las formaciones recuperadas son las esperadas
//      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(500L) && fe.getPosicionEnCampo() == PosicionEnum.ARQUERO), is(true));
//      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(501L) && fe.getPosicionEnCampo() == PosicionEnum.DEFENSOR), is(true));
//      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(502L) && fe.getPosicionEnCampo() == PosicionEnum.MEDIOCAMPISTA), is(true));
//   }

   @Test
   public void deberiaRetornarListaVaciaParaEquipoExistenteSinFormacion() {
      // given: Un equipo con ID 101 que no tiene formaciones en db_futcodeTest.sql
      Long equipoIdSinFormaciones = 101L;

      // when: Se buscan las formaciones de ese equipo
      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones);

      // then: Se debería retornar una lista vacía
      assertNotNull(formaciones);
      assertThat(formaciones, is(empty()));
   }

   @Test
   public void deberiaRetornarListaVaciaParaEquipoNoExistente() {
      // given: Un ID de equipo que no existe en la base de datos
      Long equipoIdNoExistente = 9999L;

      // when: Se buscan las formaciones de ese equipo
      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdNoExistente);

      // then: Se debería retornar una lista vacía
      assertNotNull(formaciones);
      assertThat(formaciones, is(empty()));
   }

   // TESTS PARA deleteByEquipoId

//   @Test
//   @Sql("/db_futcodeTest.sql") // Cargar datos de prueba antes de ejecutar el test
//   public void deberiaEliminarTodasLasFormacionesDeUnEquipo() {
//      // given: Un equipo con ID 102 que tiene 2 formaciones en db_futcodeTest.sql
//      Long equipoIdParaEliminar = 102L;
//      // Verificamos que existen antes de eliminar
//      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar), hasSize(2));
//
//      // when: Se eliminan las formaciones de ese equipo
//      formacionEquipoRepository.deleteByEquipoId(equipoIdParaEliminar);
//      sessionFactory.getCurrentSession().flush(); // Forzar la ejecución de la eliminación
//
//      // then: No debería haber formaciones para ese equipo
//      List<FormacionEquipo> formacionesDespuesDeEliminar = formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar);
//      assertThat(formacionesDespuesDeEliminar, is(empty()));
//   }

   @Test
   public void deberiaNoHacerNadaAlIntentarEliminarFormacionesDeEquipoSinFormacion() {
      // given: Un equipo con ID 101 que no tiene formaciones
      Long equipoIdSinFormaciones = 101L;
      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones), is(empty()));

      // when: Se intenta eliminar sus formaciones
      formacionEquipoRepository.deleteByEquipoId(equipoIdSinFormaciones);
      sessionFactory.getCurrentSession().flush(); // Forzar la ejecución de la eliminación

      // then: La lista de formaciones sigue estando vacía (no se lanzó excepción, ni error)
      List<FormacionEquipo> formacionesDespuesDeEliminar = formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones);
      assertThat(formacionesDespuesDeEliminar, is(empty()));
   }

   @Test
   public void deberiaNoHacerNadaAlIntentarEliminarFormacionesDeEquipoNoExistente() {
      // given: Un ID de equipo que no existe
      Long equipoIdNoExistente = 9998L;

      // when: Se intenta eliminar sus formaciones
      formacionEquipoRepository.deleteByEquipoId(equipoIdNoExistente);
      sessionFactory.getCurrentSession().flush(); // Forzar la ejecución de la eliminación

      // then: No se produce ningún error y la operación se completa
      // No hay una forma directa de verificar que "no pasó nada" si no hubo error,
      // pero el test pasa si no se lanza una excepción.
      // Podríamos verificar que otras formaciones no se vieron afectadas si tuviéramos IDs específicos.
   }

//   // TESTS PARA save
//
//   @Test
//   public void deberiaGuardarUnaNuevaFormacionEquipo() {
//      // given: Un equipo existente (ID 103) y un jugador existente que no está en la formación de ese equipo (Jugador ID 505)
//      Equipo equipoParaGuardar = sessionFactory.getCurrentSession().get(Equipo.class, 103L);
//      Jugador jugadorNuevoEnFormacion = sessionFactory.getCurrentSession().get(Jugador.class, 505L);
//      PosicionEnum posicion = PosicionEnum.MEDIOCAMPISTA;
//
//      FormacionEquipo nuevaFormacion = new FormacionEquipo();
//      nuevaFormacion.setEquipo(equipoParaGuardar);
//      nuevaFormacion.setJugador(jugadorNuevoEnFormacion);
//      nuevaFormacion.setPosicionEnCampo(posicion);
//
//      // Verificamos que inicialmente hay 2 formaciones para el equipo 103
//      assertThat(formacionEquipoRepository.findByEquipoId(103L), hasSize(2));
//
//      // when: Se guarda la nueva formación
//      formacionEquipoRepository.save(nuevaFormacion);
//      sessionFactory.getCurrentSession().flush(); // Forzar la persistencia
//      sessionFactory.getCurrentSession().clear(); // Limpiar cache para asegurar que se lee de DB
//
//      // then: La nueva formación debería haber sido guardada y el equipo 103 ahora tiene 3 formaciones
//      List<FormacionEquipo> formacionesActualizadas = formacionEquipoRepository.findByEquipoId(103L);
//      assertThat(formacionesActualizadas, hasSize(3));
//      assertThat(formacionesActualizadas.stream().anyMatch(fe -> fe.getJugador().getId().equals(jugadorNuevoEnFormacion.getId()) && fe.getPosicionEnCampo() == posicion), is(true));
//      assertNotNull(nuevaFormacion.getId()); // Debería tener un ID asignado
//   }
//
//   @Test
//   public void deberiaActualizarUnaFormacionEquipoExistente() {
//      // given: Una formación de equipo existente (ID 1000) y un cambio de posición
//      Long formacionExistenteId = 1000L; // ID de formacion_equipo del ARQUERO 500 para Equipo 100
//      FormacionEquipo formacionAActualizar = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
//      PosicionEnum nuevaPosicion = PosicionEnum.DEFENSOR; // Cambiamos su posición
//
//      assertNotNull(formacionAActualizar);
//      assertThat(formacionAActualizar.getPosicionEnCampo(), is(PosicionEnum.ARQUERO)); // Posicion inicial
//
//      // when: Se actualiza la posición y se guarda
//      formacionAActualizar.setPosicionEnCampo(nuevaPosicion);
//      formacionEquipoRepository.save(formacionAActualizar);
//      sessionFactory.getCurrentSession().flush(); // Forzar la actualización
//      sessionFactory.getCurrentSession().clear(); // Limpiar cache para asegurar que se lee de DB
//
//      // then: La formación debería tener la nueva posición
//      FormacionEquipo formacionActualizada = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
//      assertNotNull(formacionActualizada);
//      assertThat(formacionActualizada.getPosicionEnCampo(), is(nuevaPosicion));
//      assertThat(formacionActualizada.getEquipo().getId(), is(100L)); // Verificar que no se cambió el equipo
//      assertThat(formacionActualizada.getJugador().getId(), is(500L)); // Verificar que no se cambió el jugador
//   }
}