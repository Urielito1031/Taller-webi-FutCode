package com.tallerwebi.dominio.excepcion;

public class UsuarioNoEncontrado extends Exception{
    public UsuarioNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
