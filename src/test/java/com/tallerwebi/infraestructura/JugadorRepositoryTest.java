package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.entities.Pais;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.JugadorRepository;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SpringWebTestConfig.class, HibernateTestConfig.class })
@Transactional
@Rollback
@Sql("/db_futcodeTest.sql")
public class JugadorRepositoryTest {

   @Autowired
   private JugadorRepository jugadorRepository;

   @Autowired
   private SessionFactory sessionFactory;

   @Test
   public void debeObtenerTodosLosJugadoresDeLaBaseDeDatos() {
      List<Jugador> jugadores = jugadorRepository.getAll();

      assertNotNull(jugadores);
      assertThat(jugadores, hasSize(21));
   }

   @Test
   public void debeRetornarListaVaciaSiNoHayJugadores() {
      // Usar HQL en lugar de SQL nativo para evitar problemas de persistencia
      sessionFactory.getCurrentSession().createQuery("DELETE FROM FormacionEquipo").executeUpdate();
      sessionFactory.getCurrentSession().createQuery("DELETE FROM Jugador").executeUpdate();
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      List<Jugador> jugadores = jugadorRepository.getAll();

      assertNotNull(jugadores);
      assertThat(jugadores, is(empty()));
   }

   @Test
   public void debeSortearJugadoresDeUnaRarezaYPosicionEspecifica() {
      RarezaJugador rareza = RarezaJugador.RARO;
      PosicionEnum posicion = PosicionEnum.ARQUERO;
      int cantidad = 2;

      List<Jugador> jugadoresSorteados = jugadorRepository.sortearJugadoresIniciales(rareza, posicion, cantidad);

      assertNotNull(jugadoresSorteados);
      assertThat(jugadoresSorteados, hasSize(cantidad));
      assertThat(jugadoresSorteados, everyItem(hasProperty("rarezaJugador", is(rareza))));
      assertThat(jugadoresSorteados, everyItem(hasProperty("posicion", is(posicion))));

      List<Long> expectedPlayerIds = List.of(5000L, 5001L, 5002L);
      for (Jugador jugador : jugadoresSorteados) {
         assertTrue(expectedPlayerIds.contains(jugador.getId()));
      }
   }

   @Test
   public void debeSortearTodosLosJugadoresSiLaCantidadEsMayorALosDisponibles() {
      RarezaJugador rareza = RarezaJugador.LEYENDA;
      PosicionEnum posicion = PosicionEnum.MEDIOCAMPISTA;
      int cantidad = 5;

      List<Jugador> jugadoresSorteados = jugadorRepository.sortearJugadoresIniciales(rareza, posicion, cantidad);

      assertNotNull(jugadoresSorteados);
      assertThat(jugadoresSorteados, hasSize(2));
      assertThat(jugadoresSorteados, everyItem(hasProperty("rarezaJugador", is(rareza))));
      assertThat(jugadoresSorteados, everyItem(hasProperty("posicion", is(posicion))));
   }

   @Test
   public void debeRetornarListaVaciaSiNoHayJugadoresConRarezaYPosicionEspecifica() {
      RarezaJugador rareza = RarezaJugador.EPICO;
      PosicionEnum posicion = PosicionEnum.DEFENSOR;
      int cantidad = 1;

      List<Jugador> jugadoresSorteados = jugadorRepository.sortearJugadoresIniciales(rareza, posicion, cantidad);

      assertNotNull(jugadoresSorteados);
      assertThat(jugadoresSorteados, is(empty()));
   }

   @Test
   public void debeObtenerTodosLosJugadoresDeUnEquipoExistente() {
      Long equipoId = 201L;

      List<Jugador> jugadores = jugadorRepository.getAllByEquipoId(equipoId);

      assertNotNull(jugadores);
      assertThat(jugadores, hasSize(2)); // Equipo 201 tiene 2 jugadores (5008, 5009)
      assertThat(jugadores.stream().anyMatch(j -> j.getId().equals(5008L)), is(true));
      assertThat(jugadores.stream().anyMatch(j -> j.getId().equals(5009L)), is(true));
      // En la relación many-to-many, los jugadores no tienen propiedad "equipo"
      // directa
      // Solo verificamos que los jugadores correctos están en la lista
   }

   @Test
   public void debeRetornarListaVaciaParaUnEquipoSinJugadores() {
      Long equipoId = 104L;

      List<Jugador> jugadores = jugadorRepository.getAllByEquipoId(equipoId);

      assertNotNull(jugadores);
      assertThat(jugadores, is(empty()));
   }

   @Test
   public void debeRetornarListaVaciaParaUnEquipoNoExistente() {
      Long equipoId = 9999L;

      List<Jugador> jugadores = jugadorRepository.getAllByEquipoId(equipoId);

      assertNotNull(jugadores);
      assertThat(jugadores, is(empty()));
   }

   @Test
   public void debeObtenerJugadorPorIdExistente() {
      Long jugadorId = 5012L;

      Jugador jugador = jugadorRepository.getById(jugadorId);

      assertNotNull(jugador);
      assertThat(jugador.getId(), is(jugadorId));
      assertThat(jugador.getNombre(), is("Jugador"));
      assertThat(jugador.getApellido(), is("Individual"));
   }

   @Test
   public void debeRetornarNullSiJugadorNoExistePorId() {
      Long jugadorIdNoExistente = 99999L;

      Jugador jugador = jugadorRepository.getById(jugadorIdNoExistente);

      assertNull(jugador);
   }

   @Test
   public void debeGuardarUnNuevoJugadorEnLaBaseDeDatos() {
      Jugador nuevoJugador = new Jugador();
      nuevoJugador.setNombre("Nuevo");
      nuevoJugador.setApellido("Jugador");
      nuevoJugador.setEdad(20);
      nuevoJugador.setNumeroCamiseta(30);
      nuevoJugador.setRating(70.0);
      nuevoJugador.setLesionado(false);
      nuevoJugador.setEstadoFisico(90.00);
      nuevoJugador.setRarezaJugador(RarezaJugador.NORMAL);
      nuevoJugador.setPosicion(PosicionEnum.MEDIOCAMPISTA);

      int initialCount = jugadorRepository.getAll().size();

      Jugador jugadorGuardado = jugadorRepository.save(nuevoJugador);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      assertNotNull(jugadorGuardado);
      assertNotNull(jugadorGuardado.getId());
      assertThat(jugadorGuardado.getNombre(), is("Nuevo"));

      Jugador retrievedJugador = jugadorRepository.getById(jugadorGuardado.getId());
      assertNotNull(retrievedJugador);
      assertThat(retrievedJugador.getNombre(), is("Nuevo"));
      assertThat(jugadorRepository.getAll().size(), is(initialCount + 1));
   }

   @Test
   public void debeActualizarUnJugadorExistenteEnLaBaseDeDatos() {
      Long jugadorId = 5013L;
      Jugador jugadorAActualizar = sessionFactory.getCurrentSession().get(Jugador.class, jugadorId);

      assertNotNull(jugadorAActualizar);
      String newName = "JugadorActualizado";
      jugadorAActualizar.setNombre(newName);
      jugadorAActualizar.setLesionado(true);

      Jugador jugadorActualizado = jugadorRepository.save(jugadorAActualizar);
      sessionFactory.getCurrentSession().flush();
      sessionFactory.getCurrentSession().clear();

      assertNotNull(jugadorActualizado);
      assertThat(jugadorActualizado.getId(), is(jugadorId));
      assertThat(jugadorActualizado.getNombre(), is(newName));
      assertTrue(jugadorActualizado.getLesionado());

      Jugador retrievedJugador = sessionFactory.getCurrentSession().get(Jugador.class, jugadorId);
      assertNotNull(retrievedJugador);
      assertThat(retrievedJugador.getNombre(), is(newName));
      assertTrue(retrievedJugador.getLesionado());
   }

}