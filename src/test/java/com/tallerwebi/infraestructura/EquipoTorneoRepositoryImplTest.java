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
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new com.tallerwebi.dominio.model.entities.Esquema();
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
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new com.tallerwebi.dominio.model.entities.Esquema();
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
   public void cuandoIntentamosUnirUnEquipoInexistenteAUnTorneoExistenteEntoncesNoSeCreaLaRelacion() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);

      Torneo torneo = new Torneo();
      torneo.setNombre("Torneo de Prueba");
      torneo.setFormatoTorneo(formatoLiga);
      torneo.setEstado(EstadoTorneoEnum.ABIERTO); // Usar un valor de tu enum
      session.save(torneo);

      Long equipoIdInexistente = 999L;

      // Ejecución (la operación no debería lanzar excepción, pero tampoco debería guardar)
      // Hibernate get() retorna null si no encuentra la entidad. save() en este caso no debería persistir si el Equipo es null.
      // En una aplicación real, el servicio debería validar la existencia de entidades antes de pasar IDs al repositorio.
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), equipoIdInexistente);

      // Validación
      List<EquipoTorneo> relaciones = session.createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty()); // No se debe haber creado ninguna relación
   }

   @Test
   @Rollback
   public void cuandoIntentamosUnirUnEquipoExistenteAUnTorneoInexistenteEntoncesNoSeCreaLaRelacion() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();

      // Guardar Esquema
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new com.tallerwebi.dominio.model.entities.Esquema();
      esquema433.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      session.save(esquema433);

      Equipo equipo = new Equipo();
      equipo.setNombre("Equipo de Prueba");
      equipo.setEsquema(esquema433);
      session.save(equipo);

      Long torneoIdInexistente = 999L;

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneoIdInexistente, equipo.getId());

      // Validación
      List<EquipoTorneo> relaciones = session.createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty());
   }

   @Test
   @Rollback
   public void cuandoIntentamosUnirEquipoYTorneoInexistentesEntoncesNoSeCreaLaRelacion() {
      // Preparación
      Long torneoIdInexistente = 999L;
      Long equipoIdInexistente = 888L;

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneoIdInexistente, equipoIdInexistente);

      // Validación
      List<EquipoTorneo> relaciones = sessionFactory.getCurrentSession().createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty());
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
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new com.tallerwebi.dominio.model.entities.Esquema();
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
   //endregion

   //region Tests para manejo de nulos en los parámetros de unirEquipoATorneo
   @Test
   @Rollback
   public void cuandoUnirEquipoATorneoRecibeIDDeTorneoNuloEntoncesNoSeCreaLaRelacion() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();
      // Guardar Esquema
      com.tallerwebi.dominio.model.entities.Esquema esquema433 = new com.tallerwebi.dominio.model.entities.Esquema();
      esquema433.setEsquema(FormacionEsquema.CUATRO_TRES_TRES);
      session.save(esquema433);

      Equipo equipo = new Equipo();
      equipo.setNombre("Equipo Solo");
      equipo.setEsquema(esquema433);
      session.save(equipo);

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(null, equipo.getId());

      // Validación
      List<EquipoTorneo> relaciones = session.createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty());
   }

   @Test
   @Rollback
   public void cuandoUnirEquipoATorneoRecibeIDDeEquipoNuloEntoncesNoSeCreaLaRelacion() {
      // Preparación
      Session session = sessionFactory.getCurrentSession();
      FormatoTorneo formatoLiga = new FormatoTorneo();
      formatoLiga.setTipo(TipoFormato.LIGA);
      session.save(formatoLiga);
      Torneo torneo = new Torneo();
      torneo.setNombre("Torneo Solo");
      torneo.setFormatoTorneo(formatoLiga);
      torneo.setEstado(EstadoTorneoEnum.ABIERTO); // Usar un valor de tu enum
      session.save(torneo);

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneo.getId(), null);

      // Validación
      List<EquipoTorneo> relaciones = session.createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty());
   }

   @Test
   @Rollback
   public void cuandoUnirEquipoATorneoRecibeAmbosIDsNulosEntoncesNoSeCreaLaRelacion() {
      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(null, null);

      // Validación
      List<EquipoTorneo> relaciones = sessionFactory.getCurrentSession().createQuery("FROM EquipoTorneo", EquipoTorneo.class).list();
      assertTrue(relaciones.isEmpty());
   }
   //endregion

   //region Tests con db_futcodeTest.sql
   @Test
   @Sql("/db_futcodeTest.sql") // ¡Importante: el nombre del archivo SQL actualizado!
   @Rollback
   public void cuandoPidoTodosLosEquiposDeUnTorneoExistenteConSqlEntoncesObtengoLaListaCorrecta() {
      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(101L); // ID de Torneo de db_futcodeTest.sql

      // Validación
      assertFalse(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, hasSize(3)); // Según el db_futcodeTest.sql de ejemplo (Los Invencibles, Fuerza Unida, Estrellas FC)
      assertThat(equiposTorneoObtenidos, containsInAnyOrder(
        hasProperty("equipo", hasProperty("nombre", is("Los Invencibles"))),
        hasProperty("equipo", hasProperty("nombre", is("Fuerza Unida"))),
        hasProperty("equipo", hasProperty("nombre", is("Estrellas FC")))
      ));
      // Verificamos que todos los elementos sean del torneo 101L
      assertThat(equiposTorneoObtenidos, everyItem(hasProperty("torneo", hasProperty("id", is(101L)))));
   }

   @Test
   @Sql("/db_futcodeTest.sql")
   @Rollback
   public void cuandoUnirEquipoATorneoConSqlYDatosExistentesEntoncesSePersisteCorrectamente() {
      // Preparación: Usamos un equipo existente y un torneo existente de db_futcodeTest.sql
      Long torneoId = 101L; // Liga Master
      Long equipoIdNuevo = 203L; // Rayos X (no está en el torneo 101L inicialmente)

      // Verificamos que el equipo no esté ya en el torneo
      Session session = sessionFactory.getCurrentSession();
      List<EquipoTorneo> antes = session.createQuery(
          "FROM EquipoTorneo et WHERE et.torneo.id = :torneoId AND et.equipo.id = :equipoId",
          EquipoTorneo.class)
        .setParameter("torneoId", torneoId)
        .setParameter("equipoId", equipoIdNuevo)
        .list();
      assertTrue(antes.isEmpty());

      // Ejecución
      equipoTorneoRepository.unirEquipoATorneo(torneoId, equipoIdNuevo);

      // Validación
      List<EquipoTorneo> despues = session.createQuery(
          "FROM EquipoTorneo et WHERE et.torneo.id = :torneoId AND et.equipo.id = :equipoId",
          EquipoTorneo.class)
        .setParameter("torneoId", torneoId)
        .setParameter("equipoId", equipoIdNuevo)
        .list();

      assertFalse(despues.isEmpty());
      assertThat(despues, hasSize(1));
      EquipoTorneo nuevaRelacion = despues.get(0);
      assertThat(nuevaRelacion.getTorneo().getId(), is(torneoId));
      assertThat(nuevaRelacion.getEquipo().getId(), is(equipoIdNuevo));

      // Opcional: Verifica que ahora hay un equipo más en el torneo 101L
      List<EquipoTorneo> todosEnTorneo = equipoTorneoRepository.getAllByTorneoId(torneoId);
      assertThat(todosEnTorneo, hasSize(4)); // Antes 3, ahora 4
   }

   @Test
   @Sql("/db_futcodeTest.sql")
   @Rollback
   public void cuandoPidoEquiposDeTorneoVacioConSqlEntoncesObtengoListaVacia() {
      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(103L); // Torneo Vacío de db_futcodeTest.sql

      // Validación
      assertTrue(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, is(empty()));
   }

   @Test
   @Sql("/db_futcodeTest.sql")
   @Rollback
   public void cuandoPidoEquiposDeTorneoFinalizadoEntoncesObtengoLosEquiposCorrectos() {
      // Ejecución
      List<EquipoTorneo> equiposTorneoObtenidos = equipoTorneoRepository.getAllByTorneoId(104L); // Torneo Finalizado de db_futcodeTest.sql

      // Validación
      assertFalse(equiposTorneoObtenidos.isEmpty());
      assertThat(equiposTorneoObtenidos, hasSize(1)); // Según db_futcodeTest.sql, solo Equipo 201 en Torneo 104
      assertThat(equiposTorneoObtenidos, contains(
        hasProperty("equipo", hasProperty("nombre", is("Los Invencibles")))
      ));
      assertThat(equiposTorneoObtenidos.get(0).getTorneo().getId(), is(104L));
   }
   //endregion
}