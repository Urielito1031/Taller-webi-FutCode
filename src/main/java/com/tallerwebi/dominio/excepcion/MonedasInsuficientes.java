package com.tallerwebi.dominio.excepcion;

public class MonedasInsuficientes extends RuntimeException {
    private static final String message = "Tus monedas son insuficientes para comprar este sobre";

    public MonedasInsuficientes() {
        super(message);
    }
}
