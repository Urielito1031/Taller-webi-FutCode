package com.tallerwebi.dominio.excepcion;

public class MonedasInsuficientes extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Tus monedas son insuficientes para comprar este sobre";

    public MonedasInsuficientes() {
        super(DEFAULT_MESSAGE);
    }

    public MonedasInsuficientes(String message) {
        super(message);
    }
}