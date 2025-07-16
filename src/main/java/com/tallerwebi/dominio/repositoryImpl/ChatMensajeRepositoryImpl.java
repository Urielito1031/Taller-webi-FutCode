package com.tallerwebi.dominio.repositoryImpl;

import com.tallerwebi.dominio.model.entities.ChatMensaje;
import com.tallerwebi.dominio.repository.ChatMensajeRepository;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMensajeRepositoryImpl implements ChatMensajeRepository {

  @Autowired
  private SessionFactory sessionFactory;

  @Override
  public List<ChatMensaje> obtenerMensajesEntre(Long usuario1, Long usuario2) {
    String hql = "FROM ChatMensaje WHERE (remitenteId = :u1 AND destinatarioId = :u2) OR (remitenteId = :u2 AND destinatarioId = :u1)";
    Query<ChatMensaje> query = sessionFactory.getCurrentSession().createQuery(hql, ChatMensaje.class);
    query.setParameter("u1", usuario1);
    query.setParameter("u2", usuario2);
    return query.getResultList();
  }

  @Override
  public void guardar(ChatMensaje mensaje) {
    sessionFactory.getCurrentSession().save(mensaje);
  }
}