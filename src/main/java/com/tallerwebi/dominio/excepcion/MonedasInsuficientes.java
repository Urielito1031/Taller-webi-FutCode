package com.tallerwebi.dominio.excepcion;

public class MonedasInsuficientes extends RuntimeException {
    public MonedasInsuficientes(String message) {
        super(message);
    }
}
