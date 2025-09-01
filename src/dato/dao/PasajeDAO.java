package dato.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import dato.entidades.Pasaje;

public class PasajeDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar un pasaje en la Base de Datos
    public void guardar(Pasaje pasaje) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(pasaje);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar pasaje por id
    public Pasaje buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Pasaje p = em.find(Pasaje.class, id);
        em.close();
        return p;
    }
}
