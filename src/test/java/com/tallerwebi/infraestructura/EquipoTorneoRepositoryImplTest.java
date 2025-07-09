package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.EstadoTorneoEnum;
import com.tallerwebi.dominio.model.enums.FormacionEsquema;
import com.tallerwebi.dominio.model.enums.TipoFormato;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import com.tallerwebi.infraestructura.repositoryImpl.EquipoTorneoRepositoryImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class EquipoTorneoRepositoryImplTest {

   @Autowired
   private SessionFactory sessionFactory;

   private EquipoTorneoRepository equipoTorneoRepository;

   @BeforeEach
   public void init() {
      this.equipoTorneoRepository = new EquipoTorneoRepositoryImpl(sessionFactory);
   }

   //region Tests para getAllByTorneoId
   @Test
   @Rollback
   public void cuandoPidoTodosLosEquiposDeUnTorneoExistenteEntoncesObtengoUnaListaDeEquiposTorneoParaEseTorneo(){
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      // 1. Guardar FormatoTorneo
      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);

      // 2. Guardar Torneo
      Torneo torneo1 = new Torneo();
      torneo1.setNombre("Torneo Ejemplo 1");
      torneo1.setDescripcion("Descripción del Torneo 1");
      torneo1.setFormatoTorneo(formatoLiga);
      // Usamos ABIERTO o EN_CURSO para los tests, ya que CREADO no está en tu enum final.
      // Si tu EstadoTorneoEnum solo tiene ABIERTO, EN_CURSO, FINALIZADO, ajusta esto.
      torneo1.setEstado(EstadoTorneoEnum.ABIERTO);
      session.save(torneo1); // ID será 1L

      Torneo torneo2 = new Torneo();
      torneo2.setNombre("Torneo Ejemplo 2");
      torneo2.setDescripcion("Descripción del Torneo 2");
      torneo2.setFormatoTorneo(formatoLiga);
      torneo2.setEstado(EstadoTorneoEnum.EN_CURSO);
      session.save(torneo2); // ID será 2L

      // 3. Guardar Equipos (asumiendo que Esquema es necesario para Equipo)
      // Necesitamos una instancia de Esquema para asociarla a Equipo
      // Tu DDL para Esquema es (id, esquema enum)
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new Esquema();
      esquema433.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      session.save(esquema433); // ID será 1L

      Equipo equipoA = new Equipo();
      equipoA.setNombre("Equipo A");
      equipoA.setEsquema(esquema433); // Asigna el esquema
      session.save(equipoA); // ID será 1L

      Equipo equipoB = new Equipo();
      equipoB.setNombre("Equipo B");
      equipoB.setEsquema(esquema433);
      session.save(equipoB); // ID será 2L

      Equipo equipoC = new Equipo();
      equipoC.setNombre("Equipo C");
      equipoC.setEsquema(esquema433);
      session.save(equipoC); // ID será 3L

      // 4. Guardar EquipoTorneo
      EquipoTorneo et1 = new EquipoTorneo(equipoA, torneo1);
      et1.setPosicion(1);
      session.save(et1);

      EquipoTorneo et2 = new EquipoTorneo(equipoB, torneo1);
      et2.setPosicion(2);
      session.save(et2);

      EquipoTorneo et3 = new EquipoTorneo(equipoC, torneo2); // Este no debería aparecer en el resultado
      et3.setPosicion(1);
      session.save(et3);

      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(torneo1.getId());

      // Validación
      assertFalse(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, hasSize(2));
      assertThat(equiposTorneoObtenidos, containsInAnyOrder(
        hasProperty("equipo", hasProperty("nombre", is("Equipo A"))),
        hasProperty("equipo", hasProperty("nombre", is("Equipo B")))
      ));
      // Verificamos que los IDs de torneo coincidan, y que no se incluyan equipos del torneo2
      assertThat(equiposTorneoObtenidos.get(0).getTorneo().getId(), is(torneo1.getId()));
      assertThat(equiposTorneoObtenidos.get(1).getTorneo().getId(), is(torneo1.getId()));
      assertThat(equiposTorneoObtenidos, not(hasItem(hasProperty("equipo", hasProperty("nombre", is("Equipo C"))))));
   }

   @Test
   @Rollback
   public void cuandoPidoTodosLosEquiposDeUnTorneoInexistenteEntoncesObtengoUnaListaVacia() {
      // Preparación: No se guardan torneos ni equipos-torneo
      Long idTorneoInexistente = 999L;

      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(idTorneoInexistente);

      // Validación
      assertTrue(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, is(empty()));
   }

   @Test
   @Rollback
   public void cuandoPidoTodosLosEquiposDeUnTorneoSinEquiposAsociadosEntoncesObtengoUnaListaVacia() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      // Guardar FormatoTorneo
      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);

      // Guardar Torneo sin equipos asociados
      Torneo torneoVacio = new Torneo();
      torneoVacio.setNombre("Torneo Vacio");
      torneoVacio.setDescripcion("Este torneo no tiene equipos");
      torneoVacio.setFormatoTorneo(formatoLiga);
      torneoVacio.setEstado(EstadoTorneoEnum.ABIERTO); // Usar un valor de tu enum
      session.save(torneoVacio);

      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(torneoVacio.getId());

      // Validación
      assertTrue(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, is(empty()));
   }
   //endregion

   //region Tests para unirEquipoATorneo
   @Test
   @Rollback
   public void cuandoUnimosUnEquipoAUnTorneoExistenteEntoncesSeCreaUnaNuevaRelacionEquipoTorneo() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      // Guardar FormatoTorneo
      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);

      // Guardar Torneo
      Torneo torneo = new Torneo();
      torneo.setNombre("Torneo de Prueba");
      torneo.setFormatoTorneo(formatoLiga);
      torneo.setEstado(EstadoTorneoEnum.ABIERTO); // Usar un valor de tu enum
      session.save(torneo); // torneo.getId() será 1L

      // Guardar Esquema
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new Esquema();
      esquema433.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      session.save(esquema433);

      // Guardar Equipo
      Equipo equipo = new Equipo();
      equipo.setNombre("Equipo de Prueba");
      equipo.setEsquema(esquema433);
      session.save(equipo); // equipo.getId() será 1L

      // Verificamos que no existe la relación antes de la operación
      List<EquipoTorneo> antes = session.createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertThat(antes, is(empty()));

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), equipo.getId());

      // Validación
      List<EquipoTorneo> despues = session.createQuery(
          "FROM EquipoTorneo et WHERE et.torneo.id = :torneoId AND et.equipo.id = :equipoId",
          EquipoTorneo.class
        )
        .setParameter("torneoId", torneo.getId())
        .setParameter("equipoId", equipo.getId())
        .list();

      assertFalse(despues.isEmpty());
      assertThat(despues, hasSize(1));
      EquipoTorneo equipoTorneoGuardado = despues.get(0);
      assertThat(equipoTorneoGuardado.getTorneo().getId(), is(torneo.getId()));
      assertThat(equipoTorneoGuardado.getEquipo().getId(), is(equipo.getId()));
   }



   @Test
   @Rollback
   public void cuandoSeUnenMultiplesEquiposAlMismoTorneoEntoncesTodosSonRegistrados() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);

      Torneo torneo = new Torneo();
      torneo.setNombre("Torneo con multiples equipos");
      torneo.setFormatoTorneo(formatoLiga);
      torneo.setEstado(EstadoTorneoEnum.ABIERTO); // Usar un valor de tu enum
      session.save(torneo); // ID 1L

      // Guardar Esquema
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new Esquema();
      esquema433.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      session.save(esquema433);

      Equipo equipo1 = new Equipo();
      equipo1.setNombre("Equipo Alpha");
      equipo1.setEsquema(esquema433);
      session.save(equipo1); // ID 1L

      Equipo equipo2 = new Equipo();
      equipo2.setNombre("Equipo Beta");
      equipo2.setEsquema(esquema433);
      session.save(equipo2); // ID 2L

      Equipo equipo3 = new Equipo();
      equipo3.setNombre("Equipo Gamma");
      equipo3.setEsquema(esquema433);
      session.save(equipo3); // ID 3L

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), equipo1.getId());
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), equipo2.getId());
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), equipo3.getId());

      // Validación
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(torneo.getId());

      assertFalse(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, hasSize(3));
      assertThat(equiposTorneoObtenidos, containsInAnyOrder(
        hasProperty("equipo", hasProperty("nombre", is("Equipo Alpha"))),
        hasProperty("equipo", hasProperty("nombre", is("Equipo Beta"))),
        hasProperty("equipo", hasProperty("nombre", is("Equipo Gamma")))
      ));
   }

//   @Test
//   @Sql("/db_futcodeTest.sql")
//   @Rollback
//   public void cuandoPidoTodosLosEquiposDeUnTorneoExistenteConSqlEntoncesObtengoLaListaCorrecta() {
//      // Ejecución
//      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(101L); // ID de Torneo de db_futcodeTest.sql
//
//      // Validación
//      assertFalse(equiposTorneoObtenidos.isEmpty());
//      assertThat(equiposTorneoObtenidos, hasSize(3));
//      assertThat(equiposTorneoObtenidos, containsInAnyOrder(
//        hasProperty("equipo", hasProperty("nombre", is("Los Invencibles"))),
//        hasProperty("equipo", hasProperty("nombre", is("Fuerza Unida"))),
//        hasProperty("equipo", hasProperty("nombre", is("Estrellas FC")))
//      ));
//      assertThat(equiposTorneoObtenidos, everyItem(hasProperty("torneo", hasProperty("id", is(101L)))));
//   }




}