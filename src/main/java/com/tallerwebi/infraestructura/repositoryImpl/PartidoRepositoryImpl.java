package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.repository.PartidoRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

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
}
