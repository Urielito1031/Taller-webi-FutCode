package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.service.SobreServiceImpl;
import com.tallerwebi.dominio.excepcion.MonedasInsuficientes;
import com.tallerwebi.dominio.excepcion.TipoDeSobreDesconocido;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class UsuarioServiceTest {

    private UsuarioServiceImpl usuarioService;
    private SobreServiceImpl sobreService;
    private RepositorioUsuarioImpl repositorioUsuario;

    @BeforeEach
    public void setUp() {
        this.repositorioUsuario = Mockito.mock(RepositorioUsuarioImpl.class);
        this.usuarioService = new UsuarioServiceImpl(repositorioUsuario);
        this.sobreService = Mockito.mock(SobreServiceImpl.class);
    }

    @Test
    public void dadoQueAgregoUnSobreAUnUsuarioElUsuarioTieneUnSobre() throws UsuarioNoEncontrado {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);
        sobre.setPrecio(2500.0);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Boolean agregado = this.usuarioService.agregarSobreAJugador(1L, sobre);

        assertThat(agregado, is(true));
        assertThat(usuario.getSobres(), hasSize(1));
//        assertThat(usuario.getSobres().get(0).getTipoSobre(), is(TipoSobre.BRONCE));
    }

    @Test
    public void dadoQueQuieroAgregarUnSobreAUnUsuarioQueNoExisteSeLanzaLaExcepcionUsuarioNoEncontrado() throws UsuarioNoEncontrado {
        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(null);

        Assertions.assertThrows(UsuarioNoEncontrado.class, () -> {
            usuarioService.agregarSobreAJugador(1L, sobre);
        });
    }

    @Test
    public void dadoQueQuieroAgregarUnSobreAunUsuarioQueNoTieneLasMonedasSuficientesSeLanzaLaExcepcionMonedasInsuficientes() throws MonedasInsuficientes{
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(1000.0);

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);
        sobre.setPrecio(2500.0);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Assertions.assertThrows(MonedasInsuficientes.class, () -> {
            this.usuarioService.agregarSobreAJugador(1L, sobre);
        });
    }

    @Test
    public void dadoQueUnUsuarioCon10000MonedasCompraUnSobreValoradoEn2500MonedasLeQuedan7500(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);
        sobre.setPrecio(2500.0);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        this.usuarioService.agregarSobreAJugador(1L, sobre);

        assertThat(usuario.getMonedas(), is(7500.0));
    }

    @Test
    public void dadoQueLlegaUnSobreConSuTipoEnNullSeLanzaLaExcepcionTipoDeSobreDesconocido() throws TipoDeSobreDesconocido {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setSobres(new ArrayList<>());
        usuario.setMonedas(10000.0);

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(null);
        sobre.setPrecio(2500.0);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);
        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Assertions.assertThrows(TipoDeSobreDesconocido.class, () -> {
            this.usuarioService.agregarSobreAJugador(1L, sobre);
        });
    }



}
