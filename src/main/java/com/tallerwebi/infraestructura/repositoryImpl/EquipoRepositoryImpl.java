package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Equipo;
import com.tallerwebi.dominio.repository.EquipoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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
       getSession().saveOrUpdate(equipo);
   }


   @Override
   public Boolean existsById(Long equipoId){

      return this.getById(equipoId) != null;
   }

   @Override
   public void saveAndFlush(Equipo equipo){
      Session current = getSession();
      current.saveOrUpdate(equipo);
      current.flush(); // ← fuerza escritura en la DB, genera el ID
   }

   @Override
   public Equipo getEquipoConFormacion(Long id) {
      return getSession()
              .createQuery(" SELECT e FROM Equipo e LEFT JOIN FETCH e.formacion WHERE e.id = :id ", Equipo.class)
              .setParameter("id", id)
              .uniqueResult();
   }



   private Session getSession() {
      return session.getCurrentSession();
   }

}
