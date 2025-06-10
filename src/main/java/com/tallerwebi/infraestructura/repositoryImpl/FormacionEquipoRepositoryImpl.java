package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.FormacionEquipo;
import com.tallerwebi.dominio.repository.FormacionEquipoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class FormacionEquipoRepositoryImpl implements FormacionEquipoRepository {

   private final SessionFactory sessionFactory;

   @Autowired
   public FormacionEquipoRepositoryImpl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public List<FormacionEquipo> findByEquipoId(Long equipoId){
      String hql = "SELECT fe FROM FormacionEquipo fe WHERE fe.equipo.id = :equipoId";
      Query<FormacionEquipo> query = getSession().createQuery(hql,FormacionEquipo.class);
      query.setParameter("equipoId",equipoId);
      return query.getResultList();
   }

   @Override
   public void deleteByEquipoId(Long equipoId){
      String hql = "DELETE FROM FormacionEquipo fe WHERE fe.equipo.id = :equipoId";
      Query<?> query = getSession().createQuery(hql);
      query.setParameter("equipoId", equipoId);
      query.executeUpdate();

   }

   @Override
   public void save(FormacionEquipo formacionEquipo){
      getSession().saveOrUpdate(formacionEquipo);
   }

   private Session getSession() {
      return sessionFactory.getCurrentSession();
   }
}
