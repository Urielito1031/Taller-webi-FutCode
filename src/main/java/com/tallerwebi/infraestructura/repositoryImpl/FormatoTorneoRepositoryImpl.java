package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.FormatoTorneo;
import com.tallerwebi.dominio.repository.FormatoTorneoRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class FormatoTorneoRepositoryImpl implements FormatoTorneoRepository {

   private final SessionFactory sessionFactory;

   @Autowired
   public FormatoTorneoRepositoryImpl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void save(FormatoTorneo formatoTorneo) {
       sessionFactory.getCurrentSession().saveOrUpdate(formatoTorneo);
   }
}
