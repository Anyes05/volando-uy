package dato.dao;

import dato.entidades.Cantidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CantidadDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar una cantidad en la Base de Datos
    public void guardar(Cantidad cantidad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cantidad);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar cantidad por id
    public Cantidad buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Cantidad c = em.find(Cantidad.class, id);
        em.close();
        return c;
    }
}
