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
      System.out.println("Estado de equipo en repository equipo: " + equipo);
       getSession().saveOrUpdate(equipo);
   }


   @Override
   public Boolean existsById(Long equipoId){

      return this.getById(equipoId) != null;
   }

   private Session getSession() {
      return session.getCurrentSession();
   }

}
