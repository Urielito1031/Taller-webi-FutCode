package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.SobreRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class SobreRepositoryImpl implements SobreRepository {
    private SessionFactory sessionFactory;

    public SobreRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Jugador getJugadorPorRareza(RarezaJugador rareza) {
        String hql = "FROM Jugador WHERE rarezaJugador = :rareza ORDER BY RANDOM()";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("rareza", rareza);
        query.setMaxResults(1);
        return (Jugador) query.getSingleResult();
    }


}
