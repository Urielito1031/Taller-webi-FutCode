package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
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
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
@Transactional
@Rollback
@Sql("/db_futcodeTest.sql")
public class FormacionEquipoRepositoryTest {

   @Autowired
   private FormacionEquipoRepository formacionEquipoRepository;

   @Autowired
   private org.hibernate.SessionFactory sessionFactory;


   @Test
   public void dadoUnEquipoConFormacionDebeEncontrarSusFormaciones() {
      Long equipoIdConFormaciones = 100L;

      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdConFormaciones);

      assertNotNull(formaciones);
      assertThat(formaciones, hasSize(3));
      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(500L) && fe.getPosicionEnCampo() == PosicionEnum.ARQUERO), is(true));
      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(501L) && fe.getPosicionEnCampo() == PosicionEnum.DEFENSOR), is(true));
      assertThat(formaciones.stream().anyMatch(fe -> fe.getJugador().getId().equals(502L) && fe.getPosicionEnCampo() == PosicionEnum.MEDIOCAMPISTA), is(true));
   }

   @Test
   public void dadoUnEquipoExistenteSinFormacionDebeRetornarListaVacia() {
      Long equipoIdSinFormaciones = 101L;

      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones);

      assertNotNull(formaciones);
      assertThat(formaciones, is(empty()));
   }

   @Test
   public void dadoUnEquipoNoExistenteDebeRetornarListaVacia() {
      Long equipoIdNoExistente = 9999L;

      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoIdNoExistente);

      assertNotNull(formaciones);
      assertThat(formaciones, is(empty()));
   }

   @Test
   public void dadoUnEquipoConFormacionesDebeEliminarTodasSusFormaciones() {
      Long equipoIdParaEliminar = 102L;
      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar), hasSize(2));

      formacionEquipoRepository.deleteByEquipoId(equipoIdParaEliminar);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      List<FormacionEquipo> formacionesDespuesDeEliminar = formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar);
      assertThat(formacionesDespuesDeEliminar, is(empty()));
   }

   @Test
   public void dadoUnEquipoSinFormacionDebeNoHacerNadaAlIntentarEliminar() {
      Long equipoIdSinFormaciones = 101L;
      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones), is(empty()));

      formacionEquipoRepository.deleteByEquipoId(equipoIdSinFormaciones);
      sessionFactory.getCurrentSession().flush();

      List<FormacionEquipo> formacionesDespuesDeEliminar = formacionEquipoRepository.findByEquipoId(equipoIdSinFormaciones);
      assertThat(formacionesDespuesDeEliminar, is(empty()));
   }

   @Test
   public void dadoUnEquipoNoExistenteDebeNoHacerNadaAlIntentarEliminarFormaciones() {
      Long equipoIdNoExistente = 9998L;

      formacionEquipoRepository.deleteByEquipoId(equipoIdNoExistente);
      sessionFactory.getCurrentSession().flush();
   }

   @Test
   public void dadoUnEquipoYJugadorDebeGuardarNuevaFormacionEquipo() {
      Equipo equipoParaGuardar = sessionFactory.getCurrentSession().get(Equipo.class, 103L);
      Jugador jugadorNuevoEnFormacion = sessionFactory.getCurrentSession().get(Jugador.class, 506L);
      PosicionEnum posicion = PosicionEnum.DELANTERO;

      FormacionEquipo nuevaFormacion = new FormacionEquipo();
      nuevaFormacion.setEquipo(equipoParaGuardar);
      nuevaFormacion.setJugador(jugadorNuevoEnFormacion);
      nuevaFormacion.setPosicionEnCampo(posicion);

      assertThat(formacionEquipoRepository.findByEquipoId(103L), hasSize(2));

      formacionEquipoRepository.save(nuevaFormacion);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      List<FormacionEquipo> formacionesActualizadas = formacionEquipoRepository.findByEquipoId(103L);
      assertThat(formacionesActualizadas, hasSize(3));
      assertThat(formacionesActualizadas.stream().anyMatch(fe -> fe.getJugador().getId().equals(jugadorNuevoEnFormacion.getId()) && fe.getPosicionEnCampo() == posicion), is(true));
      assertNotNull(nuevaFormacion.getId());
   }

   @Test
   public void dadaUnaFormacionExistenteDebeActualizarSuPosicionEnCampo() {
      Long formacionExistenteId = 1000L;
      FormacionEquipo formacionAActualizar = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
      PosicionEnum nuevaPosicion = PosicionEnum.DEFENSOR;

      assertNotNull(formacionAActualizar);
      assertThat(formacionAActualizar.getPosicionEnCampo(), is(PosicionEnum.ARQUERO));

      formacionAActualizar.setPosicionEnCampo(nuevaPosicion);
      formacionEquipoRepository.save(formacionAActualizar);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      FormacionEquipo formacionActualizada = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
      assertNotNull(formacionActualizada);
      assertThat(formacionActualizada.getPosicionEnCampo(), is(nuevaPosicion));
      assertThat(formacionActualizada.getEquipo().getId(), is(100L));
      assertThat(formacionActualizada.getJugador().getId(), is(500L));
   }

   @Test
   public void dadaUnaFormacionExistenteDebeActualizarSuJugador() {
      Long formacionExistenteId = 1000L;
      FormacionEquipo formacionAActualizar = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
      Jugador nuevoJugador = sessionFactory.getCurrentSession().get(Jugador.class, 506L);

      assertNotNull(formacionAActualizar);
      assertThat(formacionAActualizar.getJugador().getId(), is(500L));

      formacionAActualizar.setJugador(nuevoJugador);
      formacionEquipoRepository.save(formacionAActualizar);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      FormacionEquipo formacionActualizada = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionExistenteId);
      assertNotNull(formacionActualizada);
      assertNotNull(formacionActualizada.getJugador());
      assertThat(formacionActualizada.getJugador().getId(), is(nuevoJugador.getId()));
      assertThat(formacionActualizada.getPosicionEnCampo(), is(PosicionEnum.ARQUERO));
   }

   @Test
   public void dadoUnEquipoConFormacionesDebeMantenerOtrosEquiposIntactosAlEliminar() {
      Long equipoIdParaEliminar = 102L;
      Long equipoIdConFormaciones = 100L;

      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar), hasSize(2));
      assertThat(formacionEquipoRepository.findByEquipoId(equipoIdConFormaciones), hasSize(3));

      formacionEquipoRepository.deleteByEquipoId(equipoIdParaEliminar);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      List<FormacionEquipo> formacionesDespuesDeEliminar = formacionEquipoRepository.findByEquipoId(equipoIdParaEliminar);
      assertThat(formacionesDespuesDeEliminar, is(empty()));

      List<FormacionEquipo> formacionesDeOtroEquipo = formacionEquipoRepository.findByEquipoId(equipoIdConFormaciones);
      assertThat(formacionesDeOtroEquipo, hasSize(3));
   }

   @Test
   public void dadoUnEquipoSinJugadorEnFormacionDebePoderGuardarFormacionValida() {
      Equipo equipoParaGuardar = sessionFactory.getCurrentSession().get(Equipo.class, 103L);
      Jugador jugadorValido = sessionFactory.getCurrentSession().get(Jugador.class, 506L);
      PosicionEnum posicion = PosicionEnum.DELANTERO;

      FormacionEquipo nuevaFormacion = new FormacionEquipo();
      nuevaFormacion.setEquipo(equipoParaGuardar);
      nuevaFormacion.setJugador(jugadorValido);
      nuevaFormacion.setPosicionEnCampo(posicion);

      assertThat(formacionEquipoRepository.findByEquipoId(103L), hasSize(2));

      formacionEquipoRepository.save(nuevaFormacion);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      List<FormacionEquipo> formacionesActualizadas = formacionEquipoRepository.findByEquipoId(103L);
      assertThat(formacionesActualizadas, hasSize(3));
      assertThat(formacionesActualizadas.stream().anyMatch(fe -> fe.getJugador().getId().equals(jugadorValido.getId()) && fe.getPosicionEnCampo() == posicion), is(true));
      assertNotNull(nuevaFormacion.getId());
   }

   @Test
   public void dadoUnEquipoConMultiplesFormacionesDebeListarTodasCorrectamente() {
      Long equipoId = 100L;

      List<FormacionEquipo> formaciones = formacionEquipoRepository.findByEquipoId(equipoId);

      assertNotNull(formaciones);
      assertThat(formaciones, hasSize(3));
      assertThat(formaciones, everyItem(hasProperty("equipo", hasProperty("id", is(equipoId)))));
   }

   @Test
   public void dadaUnaFormacionDeEquipoDebePoderRecuperarSuJugadorYEquipo() {
      Long formacionId = 1000L;
      Long jugadorIdEsperado = 500L;
      Long equipoIdEsperado = 100L;
      PosicionEnum posicionEsperada = PosicionEnum.ARQUERO;

      FormacionEquipo formacion = sessionFactory.getCurrentSession().get(FormacionEquipo.class, formacionId);

      assertNotNull(formacion);
      assertNotNull(formacion.getJugador());
      assertNotNull(formacion.getEquipo());
      assertThat(formacion.getJugador().getId(), is(jugadorIdEsperado));
      assertThat(formacion.getEquipo().getId(), is(equipoIdEsperado));
      assertThat(formacion.getPosicionEnCampo(), is(posicionEsperada));
   }
}