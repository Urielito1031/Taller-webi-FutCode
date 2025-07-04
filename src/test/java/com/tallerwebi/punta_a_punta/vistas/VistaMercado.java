package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;

public class VistaMercado extends VistaWeb{
    public VistaMercado(Page page) {
        super(page);
        page.navigate("http://localhost:8080/spring/jugador/comprar-sobres");
    }

    public void hacerClickEnSobreDeBronce() {
        page.onceDialog(Dialog::accept);
        darClickEnElElemento(".sobres > article:nth-of-type(1) form button");

    }

    @Override
    public String obtenerTextoDelElemento(String selectorCSS) {
        return super.obtenerTextoDelElemento(selectorCSS);
    }

    public String obtenerUrlActual() {
        return page.url();
    }
}
