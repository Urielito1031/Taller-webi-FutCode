package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.model.entities.Usuario;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    Usuario consultarUsuarioPorEmail(String email);
    Usuario registrar(Usuario usuario) throws UsuarioExistente;

}
