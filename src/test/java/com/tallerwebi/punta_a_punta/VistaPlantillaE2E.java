package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.VistaPlantilla;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaPlantillaE2E{

   static Playwright playwright;
   static Browser browser;
   VistaPlantilla vistaPlantilla;

   @BeforeAll
   static void abrirNavegador(){
      playwright = Playwright.create();
      browser = playwright.chromium().launch();
      // browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1400));
   }

   @AfterAll
   static void cerrarNavegador(){
      playwright.close();
   }
   @BeforeEach
   void crearContextoYPagina(){

      vistaPlantilla = new VistaPlantilla(browser.newContext().newPage());
   }
   @AfterEach
   void cerrarContexto(){
      browser.close();
   }

   @Test
   void deberiaGuardarFormacionCon11Jugadores() {

      //estado inicial
      Assertions.assertEquals(11, vistaPlantilla.page.locator(".position-marker.occupied").count(), "El campo debería empezar con 11 jugadores");

      //limpiar el campo
      vistaPlantilla.hacerClickLimpiarCampo();
      //verificamos que esta vacio
      Assertions.assertEquals(0, vistaPlantilla.page.locator(".position-marker.occupied").count(), "El campo debería estar vacío después de limpiar");

      // ids de jugadores en la base de datos
      String[] jugadorIds = {
                  "16", "2", "3", "4", "5", "6", "10", "118", "20", "21", "17"};

      vistaPlantilla.arrastrarJugadoresASusMarcadores(jugadorIds);
      vistaPlantilla.esperarCargaMarcadores();

      vistaPlantilla.hacerClickGuardarFormacion();
      String texto = vistaPlantilla.obtenerMensajeDeExito().trim();


      assertThat("Formación guardada con éxito!", equalToIgnoringCase(texto));

      String url = vistaPlantilla.obtenerURLActual();
      assertThat(url, containsStringIgnoringCase("/spring/guardar-formacion"));
   }

}
