package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {

       return getCurrentSession().createQuery("FROM Usuario u WHERE u.email = :email AND u.password = :password", Usuario.class)
             .setParameter("email",email)
             .setParameter("password",password).
             uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        getCurrentSession().saveOrUpdate(usuario);
    }

    @Override
    public Usuario buscar(String email) {

        return getCurrentSession().createQuery("FROM Usuario u WHERE u.email = :email", Usuario.class)
              .setParameter("email", email)
              .uniqueResult();
    }

    @Override
    public void modificar(Usuario usuario) {
        getCurrentSession().update(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return getCurrentSession().createQuery("FROM Usuario u WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    //para evitar instanciar en cada metodo el Session session = sessionFactory.getCurrentSession();
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
