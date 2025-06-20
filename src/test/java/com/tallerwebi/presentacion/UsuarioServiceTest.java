package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontrado;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
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

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);

        Mockito.when(sobreService.crearSobre(TipoSobre.BRONCE)).thenReturn(sobre);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Boolean agregado = this.usuarioService.agregarSobreAJugador(1L, sobre);

        assertThat(agregado, is(true));
        assertThat(usuario.getSobres(), hasSize(1));
        assertThat(usuario.getSobres().get(0).getTipoSobre(), is(TipoSobre.BRONCE));
    }

//    @Test
//    public void dadoQueQuieroAgregarUnSobreAUnUsuarioQueNoExisteSeLanzaLaExcepcionUsuarioNoEncontrado() throws UsuarioNoEncontrado {
//        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(null);
//    }




}
