package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class VistaPlantilla extends VistaWeb{


   public VistaPlantilla(Page page){
      super(page);
      page.navigate("http://localhost:8080/plantilla");
   }

   public void hacerClickLimpiarCampo() {
      page.onceDialog(Dialog::accept);

      darClickEnElElemento("#clear-field-btn");

      page.waitForFunction("() => document.querySelectorAll('.position-marker.occupied').length === 0",5000);
   }


   public void esperarCargaMarcadores(){
      page.waitForFunction("() => document.querySelectorAll('.position-marker.occupied').length === 11" );
   }
   public void hacerClickGuardarFormacion(){
      darClickEnElElemento("#btn-save");
   }
   public String obtenerMensajeDeExito(){
      return obtenerTextoDelElemento("div.alert.alert-success");
   }

   public void arrastrarYSoltarJugador(String playerId, String markerId) {
      page.evaluate(

        "([pid, mid]) => ForzarAsignacionDeMarcadores(pid, mid)",
        new Object[]{playerId, markerId}
      );

      page.waitForFunction(
        "sel => document.querySelector(sel)?.classList.contains('occupied')",
        "#" + markerId
        );
      page.waitForTimeout(1000);


   }

   public void arrastrarJugadoresASusMarcadores(String[] jugadorIds) {
      for (int i = 0; i < jugadorIds.length; i++) {
         String playerId = jugadorIds[i];

         Locator marcadoresLibres = page.locator(".position-marker.default-marker:not(.occupied)");
         String markerId = marcadoresLibres.first().getAttribute("id");

         this.arrastrarYSoltarJugador(playerId, markerId);

         page.waitForFunction(
           "count => document.querySelectorAll('.position-marker.occupied').length === count",
           i + 1
         );
         page.waitForTimeout(1000);


      }

   }


}
