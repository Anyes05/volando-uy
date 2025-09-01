package dato.dao;

import dato.entidades.RutaVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class RutaVueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar una ruta de vuelo en la Base de Datos
    public void guardar(RutaVuelo rutaVuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rutaVuelo);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar ruta de vuelo por id
    public RutaVuelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        RutaVuelo rv = em.find(RutaVuelo.class, id);
        em.close();
        return rv;
    }
}
