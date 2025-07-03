package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.model.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private final RepositorioUsuario repositorioUsuario;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario,BCryptPasswordEncoder passwordEncoder){
        this.repositorioUsuario = repositorioUsuario;
       this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario consultarUsuario (String email, String rawPassword) {
        Usuario usuario=  repositorioUsuario.buscarUsuario(email);
        if (usuario != null && passwordEncoder.matches(rawPassword, usuario.getPassword())) {
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario registrar(Usuario usuario) throws UsuarioExistente{
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }


        String hashedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(hashedPassword);
        usuario.setRol("USER");
        usuario.setActivo(true);

        repositorioUsuario.guardar(usuario);
        return usuario;
    }

}

