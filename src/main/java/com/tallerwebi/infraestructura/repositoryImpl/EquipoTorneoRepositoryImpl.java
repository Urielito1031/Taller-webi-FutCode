package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.model.entities.EquipoTorneo;
import com.tallerwebi.dominio.model.entities.Torneo;
import com.tallerwebi.dominio.repository.EquipoTorneoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
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
             "WHERE et.torneo.id = :torneoId " +
             "ORDER BY et.posicion",
            EquipoTorneo.class
      ).setParameter("torneoId", torneoId).list();
   }

   @Override
   public void unirEquipoATorneo(Long torneoId,Long equipoId){
      EquipoTorneo equipoTorneo = new EquipoTorneo();

      equipoTorneo.setTorneo(getSession().get(Torneo.class, torneoId));
      equipoTorneo.setEquipo(getSession().get(Equipo.class, equipoId));

      getSession().save(equipoTorneo);
   }

   @Override
   public void save(EquipoTorneo equipoTorneo) {
      this.getSession().saveOrUpdate(equipoTorneo);
   }

   @Override
   public List<EquipoTorneo> getAllByEquipoId(Long equipoId) {
      return getSession().createQuery(
          "FROM EquipoTorneo et WHERE et.equipo.id = :equipoId ORDER BY et.torneo.id DESC",
          EquipoTorneo.class
      ).setParameter("equipoId", equipoId).list();
   }

   private Session getSession(){
      return this.sessionFactory.getCurrentSession();
   }
}
