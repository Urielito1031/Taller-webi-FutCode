package com.tallerwebi.infraestructura.repositoryImpl;

import com.tallerwebi.dominio.model.entities.Narracion;
import com.tallerwebi.dominio.repository.NarracionRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class NarracionRepositoryImpl implements NarracionRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void guardar(Narracion narracion) {
        try {
            // Verificar si la tabla existe, si no, crearla
            verificarYCrearTablaNarracion();

            System.out.println("Guardando narración: " + narracion);
            getSession().save(narracion);
            getSession().flush();
            System.out.println("Narración guardada con ID: " + narracion.getId());
        } catch (Exception e) {
            System.err.println("Error guardando narración: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void verificarYCrearTablaNarracion() {
        try {
            // Intentar crear la tabla si no existe
            String createTableSQL = "CREATE TABLE IF NOT EXISTS narracion (" +
                    "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY, " +
                    "texto LONGVARCHAR, " +
                    "partido_id BIGINT)";

            getSession().createNativeQuery(createTableSQL).executeUpdate();
            System.out.println("Tabla narracion verificada/creada correctamente");
        } catch (Exception e) {
            System.err.println("Error verificando/creando tabla narracion: " + e.getMessage());
            // No lanzar excepción aquí, solo log
        }
    }

    @Override
    public List<Narracion> obtenerPorPartidoId(Long partidoId) {
        return getSession().createQuery("FROM Narracion n WHERE n.partido.id = :partidoId", Narracion.class)
                .setParameter("partidoId", partidoId)
                .list();
    }
}
