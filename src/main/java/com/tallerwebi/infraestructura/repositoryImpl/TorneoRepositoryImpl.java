package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.repository.TorneoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public class TorneoRepositoryImpl implements TorneoRepository{

   private final SessionFactory sessionFactory;

   @Autowired
   public TorneoRepositoryImpl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }


   @Override
   public List<Torneo> findAll() {
      return getSession().createQuery("from Torneo",Torneo.class).list();
   }

   @Override
   public void save(Torneo torneo) {
      getSession().saveOrUpdate(torneo);
   }

   @Override
   public Torneo getById(Long id){

      return getSession().get(Torneo.class,id);
   }

   @Override
   public boolean existsById(Long torneoId){
      return this.getById(torneoId) != null;
   }

   private Session getSession() {
      return sessionFactory.getCurrentSession();
   }



   @Override @Transactional
   public Torneo obtenerTorneoConFechas(Long torneoId) {
      String hql = "SELECT DISTINCT t FROM Torneo t " +
              "LEFT JOIN FETCH t.fechas f " +
              "LEFT JOIN FETCH f.partidos p " +
              "LEFT JOIN FETCH p.equipoLocal " +
              "LEFT JOIN FETCH p.equipoVisitante " +
              "WHERE t.id = :id";

      return sessionFactory.getCurrentSession()
              .createQuery(hql, Torneo.class)
              .setParameter("id", torneoId)
              .uniqueResult();
   }
}
