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
@Transactional
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

   private Session getSession() {
      return sessionFactory.getCurrentSession();
   }
}
