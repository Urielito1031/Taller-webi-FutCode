package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Fecha;
import com.tallerwebi.dominio.repository.FechaRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class FechaRepositoryImpl implements FechaRepository {
    private final SessionFactory sessionFactory;

    public FechaRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Fecha fecha) {
        sessionFactory.getCurrentSession().save(fecha);
    }

    @Override
    public Fecha getFechaByTorneoIdAndNumeroDeFecha(Long torneoId, Long numeroDeFecha) {
        String hql = "SELECT f FROM Fecha f WHERE f.torneo.id = :torneoId AND f.numeroDeFecha = :numeroDeFecha";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Fecha.class)
                .setParameter("torneoId", torneoId)
                .setParameter("numeroDeFecha", numeroDeFecha)
                .uniqueResult();
    }

}
