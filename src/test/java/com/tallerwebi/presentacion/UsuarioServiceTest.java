package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.SobreServiceImpl;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.model.enums.TipoSobre;
import com.tallerwebi.dominio.service.UsuarioServiceImpl;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.presentacion.dto.SobreDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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

        Usuario usuario = new Usuario();
        usuario.setId(1L);
//        this.usuarioService.getJugadores().put(1L, usuario);
    }

    @Test
    public void agregarSobreAJugador() {
        Usuario usuario = new Usuario();

        usuario.setSobres(new ArrayList<>());
        usuario.setId(1L);

        SobreDTO sobre = new SobreDTO();
        sobre.setTipoSobre(TipoSobre.BRONCE);

        Mockito.when(repositorioUsuario.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Boolean agregado = this.usuarioService.agregarSobreAJugador(1L, sobre);

        assertThat(agregado, is(true));
        assertThat(usuario.getSobres(), hasSize(1));
    }
}
