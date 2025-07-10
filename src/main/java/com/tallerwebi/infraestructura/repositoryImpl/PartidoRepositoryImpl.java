package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Partido;
import com.tallerwebi.dominio.model.enums.ResultadoPartido;
import com.tallerwebi.dominio.repository.PartidoRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PartidoRepositoryImpl implements PartidoRepository {

    private final SessionFactory sessionFactory;

    public PartidoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Partido partido) {
        sessionFactory.getCurrentSession().saveOrUpdate(partido);
    }

    @Override
    public List<Partido> obtenerPartidosPorTorneoId(Long idTorneo){
        return  getSession().createQuery(
                        "SELECT p FROM Partido p " +
                                "WHERE p.torneo.id = :idTorneo", Partido.class)
                .setParameter("idTorneo", idTorneo).getResultList();
    }

    @Override
    public Partido obtenerPartidoPorId(Long id) {
        return getSession().get(Partido.class, id);
    }


    @Override
    public List<Partido> obtenerPartidosPorEquipoId(Long idEquipo){
        return   getSession().createQuery(
          "SELECT p " +
            "FROM Partido p " +
            "WHERE p.equipoLocal.id = :idEquipo" +
            " OR p.equipoVisitante.id = :idEquipo",
          Partido.class).setParameter("idEquipo", idEquipo).getResultList();
    }

    @Override
    public List<Partido> obtenerPartidosJugadosPorEquipoId(Long idEquipo){
        return getSession().createQuery(
            "SELECT p " +
              "FROM Partido p " +
              "JOIN p.fecha f " +
              "WHERE (p.equipoLocal.id = :idEquipo OR p.equipoVisitante.id = :idEquipo) " +
              "AND p.resultado != :resultadoPendiente " +
              "ORDER BY f.numeroDeFecha DESC",
            Partido.class)
          .setParameter("idEquipo", idEquipo)
          .setParameter("resultadoPendiente", ResultadoPartido.PENDIENTE)
          .getResultList();
    }
    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }
}
