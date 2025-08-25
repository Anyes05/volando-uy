package dato.dao;

import dato.entidades.PaqueteVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PaqueteVueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

    // Guardar un paquete de vuelo en la Base de Datos
    public void guardar(PaqueteVuelo paqueteVuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(paqueteVuelo);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar paquete de vuelo por id
    public PaqueteVuelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        PaqueteVuelo p = em.find(PaqueteVuelo.class, id);
        em.close();
        return p;
    }
}
