package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.model.entities.*;
import com.tallerwebi.dominio.service.SobreServiceImpl;
import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.enums.PosicionEnum; // Importar PosicionEnum
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.JugadorDTO;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
public class UsuarioServiceTest{

    private UsuarioServiceImpl usuarioService;
    private RepositorioUsuarioImpl repositorioUsuario;

    @BeforeEach
    public void setUp(){
        this.repositorioUsuario = Mockito.mock(RepositorioUsuarioImpl.class);
        this.usuarioService = new UsuarioServiceImpl(repositorioUsuario);
    }


    @Test
    public void dadoQueAgregoUnSobreAUnUsuarioExistenteConSuficientesMonedas_cuandoAgregoElSobre_entoncesElUsuarioTieneUnSobreYMonedasActualizadas() throws UsuarioNoEncontrado, MonedasInsuficientes, TipoDeSobreDesconocido{
        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(TipoSobre.BRONCE);
        sobreDTO.setPrecio(2500.0);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        // WHEN
        Boolean agregado = this.usuarioService.agregarSobreAJugador(1L,sobreDTO);

        // THEN
        assertThat(agregado,is(true));
        assertThat(usuario.getSobres(),hasSize(1));

        assertThat(usuario.getMonedas(),is(7500.0));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
        Mockito.verify(repositorioUsuario,times(1)).actualizarUsuario(usuario);
    }

    @Test
    public void dadoQueQuieroAgregarUnSobreAUnUsuarioInexistente_cuandoIntentoAgregarElSobre_entoncesSeLanzaLaExcepcionUsuarioNoEncontrado(){
        // GIVEN
        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(TipoSobre.BRONCE);
        sobreDTO.setPrecio(2500.0);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(null);

        // WHEN / THEN
        Assertions.assertThrows(UsuarioNoEncontrado.class,() -> {
            usuarioService.agregarSobreAJugador(1L,sobreDTO);
        });
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
        Mockito.verify(repositorioUsuario,never()).actualizarUsuario(any(Usuario.class));
    }

    @Test
    public void dadoQueQuieroAgregarUnSobreAUnUsuarioSinMonedasSuficientes_cuandoIntentoAgregarElSobre_entoncesSeLanzaLaExcepcionMonedasInsuficientes(){
        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(1000.0);

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(TipoSobre.BRONCE);
        sobreDTO.setPrecio(2500.0);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        // WHEN / THEN
        Assertions.assertThrows(MonedasInsuficientes.class,() -> {
            this.usuarioService.agregarSobreAJugador(1L,sobreDTO);
        });
        assertThat(usuario.getMonedas(),is(1000.0));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
        Mockito.verify(repositorioUsuario,never()).actualizarUsuario(any(Usuario.class));
    }

    @Test
    public void dadoQueUnUsuarioCon10000MonedasCompraUnSobreDe2500Monedas_cuandoAgregaElSobre_entoncesLeQuedan7500Monedas() throws UsuarioNoEncontrado, MonedasInsuficientes, TipoDeSobreDesconocido{
        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(TipoSobre.BRONCE);
        sobreDTO.setPrecio(2500.0);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        // WHEN
        this.usuarioService.agregarSobreAJugador(1L,sobreDTO);

        // THEN
        assertThat(usuario.getMonedas(),is(7500.0));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
        Mockito.verify(repositorioUsuario,times(1)).actualizarUsuario(usuario);
    }

    @Test
    public void dadoQueLlegaUnSobreConSuTipoEnNull_cuandoIntentoAgregarSobreAJugador_entoncesSeLanzaLaExcepcionTipoDeSobreDesconocido(){
        // GIVEN
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobreDTO = new SobreDTO();
        sobreDTO.setTipoSobre(null);
        sobreDTO.setPrecio(2500.0);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        // WHEN / THEN
        Assertions.assertThrows(TipoDeSobreDesconocido.class,() -> {
            this.usuarioService.agregarSobreAJugador(1L,sobreDTO);
        });
        assertThat(usuario.getMonedas(),is(10000.0));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
        Mockito.verify(repositorioUsuario,never()).actualizarUsuario(any(Usuario.class));
    }
    //endregion

    //region Tests para buscarUsuarioPorId
    @Test
    public void dadoQueBuscoUnUsuarioExistente_cuandoLoBuscoPorId_entoncesDevuelveElUsuarioCorrecto(){
        // GIVEN
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(1L);
        usuarioEsperado.setEmail("test@test.com");
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuarioEsperado);

        // WHEN
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(1L);

        // THEN
        assertThat(usuarioEncontrado,is(notNullValue()));
        assertThat(usuarioEncontrado.getId(),is(1L));
        assertThat(usuarioEncontrado.getEmail(),is("test@test.com"));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(1L);
    }

    @Test
    public void dadoQueBuscoUnUsuarioInexistente_cuandoLoBuscoPorId_entoncesDevuelveNull(){
        // GIVEN
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(99L)).thenReturn(null);

        // WHEN
        Usuario usuarioEncontrado = usuarioService.buscarUsuarioPorId(99L);

        // THEN
        assertThat(usuarioEncontrado,is(nullValue()));
        Mockito.verify(repositorioUsuario,times(1)).buscarUsuarioPorId(99L);
    }


    @Test
    public void dadoQueUnUsuarioNoTieneSobres_cuandoSolicitoSusSobres_entoncesObtengoUnaListaVaciaDeSobresDTO(){
        // GIVEN
        Mockito.when(repositorioUsuario.obtenerSobresDelUsuario(1L)).thenReturn(new ArrayList<>());

        // WHEN
        List<SobreDTO> sobresDTO = usuarioService.obtenerSobresDelUsuario(1L);

        // THEN
        assertThat(sobresDTO,is(notNullValue()));
        assertThat(sobresDTO,is(empty()));
        Mockito.verify(repositorioUsuario,times(1)).obtenerSobresDelUsuario(1L);
    }

    @Test
    public void dadoQueElIDDeUsuarioEsNuloAlObtenerSobres_cuandoSolicitoSusSobres_entoncesObtengoUnaListaVacia(){
        // GIVEN
        Mockito.when(repositorioUsuario.obtenerSobresDelUsuario(null)).thenReturn(new ArrayList<>());

        // WHEN
        List<SobreDTO> sobresDTO = usuarioService.obtenerSobresDelUsuario(null);

        // THEN
        assertThat(sobresDTO,is(notNullValue()));
        assertThat(sobresDTO,is(empty()));
        Mockito.verify(repositorioUsuario,times(1)).obtenerSobresDelUsuario(null);
    }
    //endregion

    //region Tests para borrarSobreAUsuario
    @Test
    public void dadoQueBorroUnSobreExistente_cuandoLoBorroAUsuario_entoncesSeInvocaElMetodoDelRepositorio(){
        // GIVEN
        Long idUsuario = 1L;
        Long idSobre = 10L;
        Mockito.doNothing().when(repositorioUsuario).borrarSobreAUsuario(idUsuario,idSobre);

        // WHEN
        usuarioService.borrarSobreAUsuario(idUsuario,idSobre);

        // THEN
        Mockito.verify(repositorioUsuario,times(1)).borrarSobreAUsuario(idUsuario,idSobre);
    }

    @Test
    public void dadoQueIntentoBorrarUnSobreInexistente_cuandoLoBorroAUsuario_entoncesSeInvocaElMetodoDelRepositorioSinErrores(){
        // GIVEN
        Long idUsuario = 1L;
        Long idSobreInexistente = 99L;
        Mockito.doNothing().when(repositorioUsuario).borrarSobreAUsuario(idUsuario,idSobreInexistente);

        // WHEN
        usuarioService.borrarSobreAUsuario(idUsuario,idSobreInexistente);

        // THEN
        Mockito.verify(repositorioUsuario,times(1)).borrarSobreAUsuario(idUsuario,idSobreInexistente);
    }
    //endregion

    //region Tests para convertirEntidadADTO
    @Test
    public void dadoQueConviertoUnSobreDeBronceA_DTO_entoncesObtengoUnSobreDTOConTipoBronce(){
        // GIVEN
        Sobre sobreBronce = new SobreBronce();
        sobreBronce.setId(1L);
        sobreBronce.setTitulo("Sobre Bronce");
        sobreBronce.setPrecio(2500.0);
        sobreBronce.setImagenUrl("bronce.png");
        sobreBronce.setJugadores(new ArrayList<>());

        // WHEN
        SobreDTO sobreDTO = usuarioService.convertirEntidadADTO(sobreBronce);

        // THEN
        assertThat(sobreDTO,notNullValue());
        assertThat(sobreDTO.getId(),is(1L));
        assertThat(sobreDTO.getTitulo(),is("Sobre Bronce"));
        assertThat(sobreDTO.getPrecio(),is(2500.0));
        assertThat(sobreDTO.getTipoSobre(),is(TipoSobre.BRONCE));
        assertThat(sobreDTO.getImagenUrl(),is("bronce.png"));
    }



    @Test
    public void dadoQueConviertoUnSobreConJugadoresA_DTO_entoncesObtengoSobreDTOConJugadoresConvertidos(){
        // GIVEN
        Sobre sobreConJugadores = new SobreBronce();
        sobreConJugadores.setId(1L);
        sobreConJugadores.setTitulo("Sobre con Jugadores");
        sobreConJugadores.setPrecio(5000.0);
        sobreConJugadores.setImagenUrl("plata.png");

        // Crear Jugador entidad con datos mínimos para la conversión a DTO
        // Asumiendo que el constructor de Jugador entidad o setters permiten esto
        Jugador jugador1 = new Jugador(); // Usar constructor por defecto
        jugador1.setId(100L);
        jugador1.setNombre("Messi");
        jugador1.setApellido("Lionel");
        jugador1.setImagen("messi.png");
        jugador1.setRating(95.0);
        jugador1.setPosicion(PosicionEnum.DELANTERO); // Usar PosicionEnum
        jugador1.setRarezaJugador(RarezaJugador.LEYENDA);
        jugador1.setEdad(35);
        jugador1.setNumeroCamiseta(10);
        jugador1.setEstadoFisico(90.0);
        jugador1.setLesionado(false);
        jugador1.setPais(new Pais()); // Asumir que Pais tiene constructor por defecto

        Jugador jugador2 = new Jugador();
        jugador2.setId(101L);
        jugador2.setNombre("Cristiano");
        jugador2.setApellido("Ronaldo");
        jugador2.setImagen("ronaldo.png");
        jugador2.setRating(92.0);
        jugador2.setPosicion(PosicionEnum.DELANTERO); // Usar PosicionEnum
        jugador2.setRarezaJugador(RarezaJugador.LEYENDA);
        jugador2.setEdad(38);
        jugador2.setNumeroCamiseta(7);
        jugador2.setEstadoFisico(85.0);
        jugador2.setLesionado(false);
        jugador2.setPais(new Pais());

        sobreConJugadores.setJugadores(Arrays.asList(jugador1,jugador2));

        // WHEN
        SobreDTO sobreDTO = usuarioService.convertirEntidadADTO(sobreConJugadores);

        // THEN
        assertThat(sobreDTO,notNullValue());
        assertThat(sobreDTO.getJugadores(),hasSize(2));
        assertThat(sobreDTO.getJugadores().get(0).getNombre(),is("Messi"));
        assertThat(sobreDTO.getJugadores().get(1).getApellido(),is("Ronaldo"));
        assertThat(sobreDTO.getJugadores().get(0).getRarezaJugador(),is(RarezaJugador.LEYENDA));
        assertThat(sobreDTO.getJugadores().get(1).getRating(),is(92.0));
        assertThat(sobreDTO.getJugadores().get(0).getPosicionNatural(),is(PosicionEnum.DELANTERO));
    }
    //endregion

    //region Tests para convertirJugadorEntidadADTO


    //region Tests para convertirJugadoresEntidad
    @Test
    public void dadoQueConviertoUnaListaDeJugadoresEntidadA_DTO_entoncesObtengoUnaListaDeJugadoresDTO(){
        // GIVEN
        Jugador jugador1 = new Jugador(); // Usar constructor por defecto y setters
        jugador1.setNombre("Messi");
        jugador1.setApellido("Lionel");
        jugador1.setImagen("messi.png");
        jugador1.setRating(95.0);
        jugador1.setPosicion(PosicionEnum.DELANTERO); // Usar PosicionEnum
        jugador1.setRarezaJugador(RarezaJugador.LEYENDA);
        jugador1.setEdad(35);
        jugador1.setNumeroCamiseta(10);
        jugador1.setEstadoFisico(90.0);
        jugador1.setLesionado(false);
        jugador1.setPais(new Pais()); // Asumir que Pais tiene constructor por defecto

        Jugador jugador2 = new Jugador();
        jugador2.setNombre("Cristiano");
        jugador2.setApellido("Ronaldo");
        jugador2.setImagen("ronaldo.png");
        jugador2.setRating(92.0);
        jugador2.setPosicion(PosicionEnum.DELANTERO); // Usar PosicionEnum
        jugador2.setRarezaJugador(RarezaJugador.LEYENDA);
        jugador2.setEdad(38);
        jugador2.setNumeroCamiseta(7);
        jugador2.setEstadoFisico(85.0);
        jugador2.setLesionado(false);
        jugador2.setPais(new Pais());

        List<Jugador> jugadoresEntidad = Arrays.asList(jugador1,jugador2);

        // WHEN
        List<JugadorDTO> jugadoresDTO = usuarioService.convertirJugadoresEntidad(jugadoresEntidad);

        // THEN
        assertThat(jugadoresDTO,notNullValue());
        assertThat(jugadoresDTO,hasSize(2));
        assertThat(jugadoresDTO.get(0).getNombre(),is("Messi"));
        assertThat(jugadoresDTO.get(0).getRating(),is(95.0));
        assertThat(jugadoresDTO.get(1).getApellido(),is("Ronaldo"));
        assertThat(jugadoresDTO.get(1).getPosicionNatural(),is(PosicionEnum.DELANTERO)); // Usar PosicionEnum
    }

    @Test
    public void dadoQueConviertoUnaListaVaciaDeJugadoresEntidadA_DTO_entoncesObtengoUnaListaVaciaDeJugadoresDTO(){
        // GIVEN
        List<Jugador> jugadoresEntidad = new ArrayList<>();

        // WHEN
        List<JugadorDTO> jugadoresDTO = usuarioService.convertirJugadoresEntidad(jugadoresEntidad);

        // THEN
        assertThat(jugadoresDTO,notNullValue());
        assertThat(jugadoresDTO,is(empty()));
    }
    //endregion

    //region Tests para convertirJugadoresDtoToEntity
    @Test
    public void dadoQueConviertoUnaListaDeJugadoresDTOAEntidad_entoncesObtengoUnaListaDeJugadoresEntidad(){
        // GIVEN
        // Usar el constructor completo de JugadorDTO
        // public JugadorDTO(Long id,String  nombre, String apellido, String imagen, Integer edad,Integer numeroCamiseta, Double rating, Double estadoFisico, PosicionEnum posicion, Pais paisOrigen,RarezaJugador rarezaJugador)
        JugadorDTO jugadorDTO1 = new JugadorDTO(
          1L,"Lionel","Messi","messi.png",35,10,95.0,90.0,PosicionEnum.DELANTERO,new Pais(),RarezaJugador.LEYENDA);
        JugadorDTO jugadorDTO2 = new JugadorDTO(
          2L,"Cristiano","Ronaldo","ronaldo.png",38,7,92.0,85.0,PosicionEnum.DELANTERO,new Pais(),RarezaJugador.LEYENDA);

        List<JugadorDTO> jugadoresDTO = Arrays.asList(jugadorDTO1,jugadorDTO2);

        // WHEN
        List<Jugador> jugadoresEntidad = usuarioService.convertirJugadoresDtoToEntity(jugadoresDTO);

        // THEN
        assertThat(jugadoresEntidad,notNullValue());
        assertThat(jugadoresEntidad,hasSize(2));
        assertThat(jugadoresEntidad.get(0).getNombre(),is("Lionel")); // Nombre en DTO es "Lionel"
        assertThat(jugadoresEntidad.get(0).getApellido(),is("Messi"));
        assertThat(jugadoresEntidad.get(0).getRating(),is(95.0));
        assertThat(jugadoresEntidad.get(0).getPosicion(),is(PosicionEnum.DELANTERO)); // Usar PosicionEnum
        assertThat(jugadoresEntidad.get(1).getNombre(),is("Cristiano"));
        assertThat(jugadoresEntidad.get(1).getApellido(),is("Ronaldo"));
        assertThat(jugadoresEntidad.get(1).getRating(),is(92.0));
        assertThat(jugadoresEntidad.get(1).getPosicion(),is(PosicionEnum.DELANTERO)); // Usar PosicionEnum
    }

    @Test
    public void dadoQueConviertoUnaListaVaciaDeJugadoresDTOAEntidad_entoncesObtengoUnaListaVaciaDeJugadoresEntidad(){
        // GIVEN
        List<JugadorDTO> jugadoresDTO = new ArrayList<>();

        // WHEN
        List<Jugador> jugadoresEntidad = usuarioService.convertirJugadoresDtoToEntity(jugadoresDTO);

        // THEN
        assertThat(jugadoresEntidad,notNullValue());
        assertThat(jugadoresEntidad,is(empty()));
    }
    //endregion

    //region Tests para actualizar
    @Test
    public void dadoQueActualizoUnUsuario_cuandoLlamoAlMetodoActualizar_entoncesSeInvocaElMetodoActualizarDelRepositorio(){
        // GIVEN
        Usuario usuarioAActualizar = new Usuario();
        usuarioAActualizar.setId(1L);
        usuarioAActualizar.setEmail("nuevo@test.com");
        Mockito.doNothing().when(repositorioUsuario).actualizar(usuarioAActualizar);

        // WHEN
        usuarioService.actualizar(usuarioAActualizar);

        // THEN
        Mockito.verify(repositorioUsuario,times(1)).actualizar(usuarioAActualizar);
    }
}