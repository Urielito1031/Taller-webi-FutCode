package com.tallerwebi.dominio;

import com.tallerwebi.dominio.model.entities.Usuario;

import javax.transaction.Transactional;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    Usuario buscarUsuarioPorId(Long id);

    void borrarSobreAUsuario(Long idUsuario, Long idSobre);

    void actualizar(Usuario usuario);}

