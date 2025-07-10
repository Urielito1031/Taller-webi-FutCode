package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.model.entities.Sobre;
import com.tallerwebi.dominio.model.entities.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email) {

       return getCurrentSession().createQuery(
               "FROM Usuario u WHERE u.email = :email", Usuario.class)
               .setParameter("email", email)
               .uniqueResult();
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
    public Usuario buscarUsuarioPorId(Long id) {
        return sessionFactory.getCurrentSession().createQuery("FROM Usuario u WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .uniqueResult();
    }


    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void actualizarUsuario(Usuario usuario) {
        sessionFactory.getCurrentSession().save(usuario);
    }


    public List<Sobre> obtenerSobresDelUsuario(Long id){
        try {
            Usuario usuario = getCurrentSession().createQuery(
                            "FROM Usuario u LEFT JOIN FETCH u.sobres WHERE u.id = :id",
                            Usuario.class)
                    .setParameter("id", id)
                    .uniqueResult();

            if (usuario != null && usuario.getSobres() != null) {
                List<Sobre> sobres = new ArrayList<>(usuario.getSobres());
                return sobres;
            }
            return new ArrayList<>();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void borrarSobreAUsuario(Long idUsuario, Long idSobre) {
        getCurrentSession()
                .createQuery("DELETE FROM Sobre AS s WHERE s.usuario.id = :idUsuario AND s.id = :idSobre")
                .setParameter("idUsuario", idUsuario)
                .setParameter("idSobre", idSobre)
                .executeUpdate();
    }

    @Override
    public void actualizar(Usuario usuario) {
        getCurrentSession().update(usuario);
    }



}
