package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Narracion;
import com.tallerwebi.dominio.repository.NarracionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

    @Repository
    @Transactional
    public class NarracionRepositoryImpl implements NarracionRepository {

        @Autowired
        private SessionFactory sessionFactory;

        private Session getSession() {
            return sessionFactory.getCurrentSession();
        }

        @Override
        public void guardar(Narracion narracion) {
            getSession().save(narracion);
        }

        @Override
        public List<Narracion> obtenerPorPartidoId(Long partidoId) {
            return getSession().createQuery("FROM Narracion n WHERE n.partido.id = :partidoId", Narracion.class)
                    .setParameter("partidoId", partidoId)
                    .list();
        }
    }

