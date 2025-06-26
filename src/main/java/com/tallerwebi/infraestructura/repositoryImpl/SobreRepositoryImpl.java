package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Jugador;
import com.tallerwebi.dominio.model.enums.RarezaJugador;
import com.tallerwebi.dominio.repository.SobreRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SobreRepositoryImpl implements SobreRepository {
    private SessionFactory sessionFactory;

    public SobreRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Jugador> getJugadoresPorRareza(RarezaJugador rareza, int cantidad) {
        String hql = "FROM Jugador WHERE rarezaJugador = :rareza";
        List<Jugador> jugadores = sessionFactory.getCurrentSession()
                .createQuery(hql, Jugador.class)
                .setParameter("rareza", rareza)
                .list();

        Collections.shuffle(jugadores);
        return jugadores.stream().limit(cantidad).collect(Collectors.toList());
    }


}
