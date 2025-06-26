package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.repository.JugadorRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JugadorRepositoryImpl implements JugadorRepository{


   private final SessionFactory sessionFactory;

   @Autowired
   public JugadorRepositoryImpl(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public List<Jugador> getAll(){
      return getSession().createQuery(
        "from Jugador", Jugador.class)
        .getResultList();
   }

   @Override
   public List<Jugador> getAllByEquipoId(Long equipoId){
      return getSession().createQuery(
        "select j from Jugador j " +
          "where j.equipo.id = :equipoId", Jugador.class)
        .setParameter("equipoId", equipoId)
        .getResultList();
   }

   @Override
   public Jugador getById(Long id){
      return null;
   }

   @Override
   public Jugador save(Jugador jugador){

      return jugador;
   }

   private Session getSession(){
      return sessionFactory.getCurrentSession();
   }


}
