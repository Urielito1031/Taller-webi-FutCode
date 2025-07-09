package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.repository.PartidoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartidoRepositoryImpl implements PartidoRepository {

    private final SessionFactory sessionFactory;

    public PartidoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Partido partido) {
        sessionFactory.getCurrentSession().saveOrUpdate(partido);
    }

    @Override
    public List<Partido> obtenerPartidosPorTorneoId(Long idTorneo){
        return  getSession().createQuery(
                        "SELECT p FROM Partido p " +
                                "WHERE p.torneo.id = :idTorneo", Partido.class)
                .setParameter("idTorneo", idTorneo).getResultList();
    }

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

}
