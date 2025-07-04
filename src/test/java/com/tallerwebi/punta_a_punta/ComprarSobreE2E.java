package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.dominio.model.entities.Usuario;
import com.tallerwebi.dominio.service.UsuarioService;
import com.tallerwebi.punta_a_punta.vistas.VistaMercado;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;




public class ComprarSobreE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaMercado vistaMercado;

    private UsuarioService usuarioService;


    @BeforeAll
    static void abrirNavegador(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1400));
    }

    @AfterAll
    static void cerrarNavegador(){
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina(){
        context = browser.newContext();
        Page page = context.newPage();

        page.navigate("http://localhost:8080/spring/login");
        page.fill("input[name='email']", "diego@mail.com");
        page.fill("input[name='password']", "diego1234");
        page.click("button[type='submit']");


        vistaMercado = new VistaMercado(page);
    }

    @AfterEach
    void cerrarContexto(){
        browser.close();
    }


    @Test
    public void queSePuedaComprarUnSobreDeBronceExitosamente() {
//        Usuario usuario = usuarioService.buscarUsuarioPorId(1L);
//        usuario.setMonedas(2500.0);

        vistaMercado.hacerClickEnSobreDeBronce();
        String texto = vistaMercado.obtenerTextoDelElemento(".text-danger");

        assertThat(texto, isEmptyString());
        Assertions.assertTrue(vistaMercado.obtenerUrlActual().contains("localhost:8080/spring/jugador/mis-sobres"));
    }

    @Test
    public void queAlComprarUnSobreSinTenerLasMonedasNecesariasSeArrojeLaExceptionMonedasInsuficientes() {
//        Usuario usuario = usuarioService.buscarUsuarioPorId(1L);
//        usuario.setMonedas(0.0);

        vistaMercado.hacerClickEnSobreDeBronce();
        String texto = vistaMercado.obtenerTextoDelElemento(".text-danger");

        assertThat(texto, containsString("Tus monedas son insuficientes para comprar este sobre"));
        Assertions.assertTrue(vistaMercado.obtenerUrlActual().contains("localhost:8080/spring/jugador/agregarSobre"));
    }

}