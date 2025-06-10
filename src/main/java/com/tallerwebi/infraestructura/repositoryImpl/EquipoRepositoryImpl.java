package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.repository.EquipoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class EquipoRepositoryImpl implements EquipoRepository{

   private final SessionFactory session;


   @Autowired
   public EquipoRepositoryImpl(SessionFactory session) {
      this.session = session;
   }

   @Override
   public List<Equipo> getAll(){
      return getSession().createQuery("FROM Equipo",Equipo.class).list();
   }

   @Override
   public Equipo getById(Long id){
      return getSession().get(Equipo.class,id);
   }

   @Override
   public void save(Equipo equipo){
       getSession().saveOrUpdate(Equipo.class);
   }

   private Session getSession() {
      return session.getCurrentSession();
   }

}
