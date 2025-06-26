package com.tallerwebi.dominio.excepcion;

public class TipoDeSobreDesconocido extends RuntimeException {
    private static final String message = "Tipo de sobre desconocido";

    public TipoDeSobreDesconocido() {
      super(message);
    }
}
