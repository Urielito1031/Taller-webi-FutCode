package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EquipoTorneoRepositoryImpl implements EquipoTorneoRepository{



   private final SessionFactory sessionFactory;

   @Autowired
   public EquipoTorneoRepositoryImpl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public List<EquipoTorneo> getAllByTorneoId(Long torneoId){

      return getSession().createQuery(
        "FROM EquipoTorneo et " +
          "WHERE et.torneo.id = :torneoId",
            EquipoTorneo.class
      ).setParameter("torneoId", torneoId).list();
   }

   @Override
   public void unirEquipoATorneo(Long torneoId,Long equipoId){
      String query = "INSERT INTO EquipoTorneo (torneo_id, equipo_id) " +
              "VALUES (:torneoId, :equipoId)";
      getSession().createNativeQuery(query)
        .setParameter("torneoId", torneoId)
        .setParameter("equipoId", equipoId);
   }

   private Session getSession(){
      return this.sessionFactory.getCurrentSession();
   }
}
