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
         "FROM Usuario u " +
           "        WHERE u.email = :email", Usuario.class)
             .setParameter("email",email).
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
        System.out.println(id);
        return sessionFactory.getCurrentSession().createQuery("FROM Usuario u WHERE u.id = :id", Usuario.class)
                .setParameter("id", id)
                .uniqueResult();
    }


    //para evitar instanciar en cada metodo el Session session = sessionFactory.getCurrentSession();
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
            System.err.println("Error al obtener sobres del usuario: " + e.getMessage());
            return new ArrayList<>();
        }
    }

//    public Long getMonedas(Long id){
//        return (Long) getCurrentSession().
//                createQuery("SELECT monedas FROM Usuario  WHERE id = :id")
//                .setParameter("id", id)
//                .uniqueResult();
//    }


}
