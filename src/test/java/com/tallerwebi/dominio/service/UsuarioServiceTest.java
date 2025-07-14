package com.tallerwebi.dominio.service;

import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.model.enums.PosicionEnum;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

  @Mock
  private RepositorioUsuarioImpl repositorioUsuario;

  @InjectMocks
  private UsuarioServiceImpl usuarioService;

  private Usuario usuario;
  private SobreDTO sobreDTO;
  private SobreBronce sobreBronce;
  private List<Jugador> jugadores;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);

    // Configurar datos de prueba
    usuario = new Usuario();
    usuario.setId(1L);
    usuario.setEmail("test@test.com");
    usuario.setMonedas(10000.0);
    usuario.setSobres(new ArrayList<>());

    sobreDTO = new SobreDTO();
    sobreDTO.setId(1L);
    sobreDTO.setTitulo("Sobre de Bronce");
    sobreDTO.setPrecio(2500.0);
    sobreDTO.setTipoSobre(TipoSobre.BRONCE);
    sobreDTO.setImagenUrl("sobreFutCodeBronce.png");

    sobreBronce = new SobreBronce();
    sobreBronce.setId(1L);
    sobreBronce.setTitulo("Sobre de Bronce");
    sobreBronce.setPrecio(2500.0);
    sobreBronce.setImagenUrl("sobreFutCodeBronce.png");

    // Crear jugadores de prueba
    jugadores = new ArrayList<>();
    Jugador jugador1 = new Jugador();
    jugador1.setId(1L);
    jugador1.setNombre("Jugador 1");
    jugador1.setApellido("Apellido 1");
    jugador1.setRating(80.0);
    jugador1.setPosicion(PosicionEnum.DELANTERO);
    jugador1.setRarezaJugador(RarezaJugador.NORMAL);
    jugadores.add(jugador1);

    Jugador jugador2 = new Jugador();
    jugador2.setId(2L);
    jugador2.setNombre("Jugador 2");
    jugador2.setApellido("Apellido 2");
    jugador2.setRating(75.0);
    jugador2.setPosicion(PosicionEnum.MEDIOCAMPISTA);
    jugador2.setRarezaJugador(RarezaJugador.NORMAL);
    jugadores.add(jugador2);

    sobreBronce.setJugadores(jugadores);
  }

  @Test
  public void deberiaAgregarSobreAJugadorExitosamente()
      throws UsuarioNoEncontrado, MonedasInsuficientes, TipoDeSobreDesconocido {
    // Preparación
    when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);
    doNothing().when(repositorioUsuario).actualizarUsuario(any(Usuario.class));

    // Ejecución
    Boolean resultado = usuarioService.agregarSobreAJugador(1L, sobreDTO);

    // Verificación
    assertThat(resultado, is(true));
    assertThat(usuario.getSobres(), hasSize(1));
    assertThat(usuario.getMonedas(), is(7500.0)); // 10000 - 2500
    verify(repositorioUsuario).buscarUsuarioPorId(1L);
    verify(repositorioUsuario).actualizarUsuario(usuario);
  }

  @Test
  public void deberiaLanzarExcepcionSiUsuarioNoExiste() {
    // Preparación
    when(repositorioUsuario.buscarUsuarioPorId(999L)).thenReturn(null);

    // Ejecución y Verificación
    UsuarioNoEncontrado exception = assertThrows(UsuarioNoEncontrado.class, () -> {
      usuarioService.agregarSobreAJugador(999L, sobreDTO);
    });

    assertThat(exception.getMessage(), is("El usuario con ID 999 no fue encontrado."));
    verify(repositorioUsuario).buscarUsuarioPorId(999L);
    verify(repositorioUsuario, never()).actualizarUsuario(any(Usuario.class));
  }

  @Test
  public void deberiaLanzarExcepcionSiMonedasInsuficientes() {
    // Preparación
    usuario.setMonedas(1000.0); // Menos monedas que el precio del sobre
    when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

    // Ejecución y Verificación
    MonedasInsuficientes exception = assertThrows(MonedasInsuficientes.class, () -> {
      usuarioService.agregarSobreAJugador(1L, sobreDTO);
    });

    verify(repositorioUsuario).buscarUsuarioPorId(1L);
    verify(repositorioUsuario, never()).actualizarUsuario(any(Usuario.class));
  }

  @Test
  public void deberiaBuscarUsuarioPorId() {
    // Preparación
    when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

    // Ejecución
    Usuario resultado = usuarioService.buscarUsuarioPorId(1L);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getEmail(), is("test@test.com"));
    verify(repositorioUsuario).buscarUsuarioPorId(1L);
  }

  @Test
  public void deberiaObtenerSobresDelUsuario() {
    // Preparación
    List<Sobre> sobres = Arrays.asList(sobreBronce);
    when(repositorioUsuario.obtenerSobresDelUsuario(1L)).thenReturn(sobres);

    // Ejecución
    List<SobreDTO> resultado = usuarioService.obtenerSobresDelUsuario(1L);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(1));
    assertThat(resultado.get(0).getTitulo(), is("Sobre de Bronce"));
    assertThat(resultado.get(0).getTipoSobre(), is(TipoSobre.BRONCE));
    verify(repositorioUsuario).obtenerSobresDelUsuario(1L);
  }

  @Test
  public void deberiaBorrarSobreAUsuario() {
    // Preparación
    doNothing().when(repositorioUsuario).borrarSobreAUsuario(1L, 1L);

    // Ejecución
    usuarioService.borrarSobreAUsuario(1L, 1L);

    // Verificación
    verify(repositorioUsuario).borrarSobreAUsuario(1L, 1L);
  }

  @Test
  public void deberiaConvertirSobreBronceADTO() {
    // Ejecución
    SobreDTO resultado = usuarioService.convertirEntidadADTO(sobreBronce);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getTitulo(), is("Sobre de Bronce"));
    assertThat(resultado.getPrecio(), is(2500.0));
    assertThat(resultado.getTipoSobre(), is(TipoSobre.BRONCE));
    assertThat(resultado.getImagenUrl(), is("sobreFutCodeBronce.png"));
    assertThat(resultado.getJugadores(), notNullValue());
    assertThat(resultado.getJugadores(), hasSize(2));
  }

  @Test
  public void deberiaConvertirSobrePlataADTO() {
    // Preparación
    SobrePlata sobrePlata = new SobrePlata();
    sobrePlata.setId(2L);
    sobrePlata.setTitulo("Sobre de Plata");
    sobrePlata.setPrecio(5000.0);
    sobrePlata.setImagenUrl("sobreFutCodePlata.png");

    // Ejecución
    SobreDTO resultado = usuarioService.convertirEntidadADTO(sobrePlata);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getTitulo(), is("Sobre de Plata"));
    assertThat(resultado.getTipoSobre(), is(TipoSobre.PLATA));
  }

  @Test
  public void deberiaConvertirSobreOroADTO() {
    // Preparación
    SobreOro sobreOro = new SobreOro();
    sobreOro.setId(3L);
    sobreOro.setTitulo("Sobre de Oro");
    sobreOro.setPrecio(7500.0);
    sobreOro.setImagenUrl("sobreFutCodeOro.png");

    // Ejecución
    SobreDTO resultado = usuarioService.convertirEntidadADTO(sobreOro);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getTitulo(), is("Sobre de Oro"));
    assertThat(resultado.getTipoSobre(), is(TipoSobre.ORO));
  }

  @Test
  public void deberiaConvertirSobreEspecialADTO() {
    // Preparación
    SobreEspecial sobreEspecial = new SobreEspecial();
    sobreEspecial.setId(4L);
    sobreEspecial.setTitulo("Sobre Especial");
    sobreEspecial.setPrecio(10000.0);
    sobreEspecial.setImagenUrl("sobreFutCodeEspecial.png");

    // Ejecución
    SobreDTO resultado = usuarioService.convertirEntidadADTO(sobreEspecial);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getTitulo(), is("Sobre Especial"));
    assertThat(resultado.getTipoSobre(), is(TipoSobre.ESPECIAL));
  }

  @Test
  public void deberiaLanzarExcepcionSiTipoDeSobreEsDesconocido() {
    // Preparación
    Sobre sobreDesconocido = new Sobre() {
      @Override
      public void setearSobre() {
        // Implementación vacía para el test
      }
    };
    sobreDesconocido.setId(5L);
    sobreDesconocido.setTitulo("Sobre Desconocido");
    sobreDesconocido.setPrecio(1000.0);
    sobreDesconocido.setImagenUrl("sobreDesconocido.png");

    // Ejecución y Verificación
    TipoDeSobreDesconocido exception = assertThrows(TipoDeSobreDesconocido.class, () -> {
      usuarioService.convertirEntidadADTO(sobreDesconocido);
    });
  }

  @Test
  public void deberiaConvertirJugadorEntidadADTO() {
    // Preparación
    Jugador jugador = jugadores.get(0);

    // Ejecución
    JugadorDTO resultado = usuarioService.convertirJugadorEntidadADTO(jugador);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado.getId(), is(1L));
    assertThat(resultado.getNombre(), is("Jugador 1"));
    assertThat(resultado.getApellido(), is("Apellido 1"));
    assertThat(resultado.getRating(), is(80.0));
    assertThat(resultado.getPosicionNatural(), is(PosicionEnum.DELANTERO));
    assertThat(resultado.getRarezaJugador(), is(RarezaJugador.NORMAL));
  }

  @Test
  public void deberiaConvertirJugadoresEntidad() {
    // Ejecución
    List<JugadorDTO> resultado = usuarioService.convertirJugadoresEntidad(jugadores);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(2));
    assertThat(resultado.get(0).getNombre(), is("Jugador 1"));
    assertThat(resultado.get(1).getNombre(), is("Jugador 2"));
  }

  @Test
  public void deberiaConvertirJugadoresDtoToEntity() {
    // Preparación
    List<JugadorDTO> jugadoresDTO = new ArrayList<>();
    JugadorDTO jugadorDTO = new JugadorDTO();
    jugadorDTO.setId(1L);
    jugadorDTO.setNombre("Jugador DTO");
    jugadoresDTO.add(jugadorDTO);

    // Ejecución
    List<Jugador> resultado = usuarioService.convertirJugadoresDtoToEntity(jugadoresDTO);

    // Verificación
    assertThat(resultado, notNullValue());
    assertThat(resultado, hasSize(1));
  }

  @Test
  public void deberiaActualizarUsuario() {
    // Preparación
    doNothing().when(repositorioUsuario).actualizar(usuario);

    // Ejecución
    usuarioService.actualizar(usuario);

    // Verificación
    verify(repositorioUsuario).actualizar(usuario);
  }
}