package dato.dao;

import dato.entidades.Ciudad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CiudadDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

    // Guardar una ciudad en la Base de Datos
    public void guardar(Ciudad ciudad) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ciudad);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar ciudad por id
    public Ciudad buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Ciudad c = em.find(Ciudad.class, id);
        em.close();
        return c;
    }

    public Ciudad buscarCiudadPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        Ciudad c = em.createQuery("SELECT c FROM Ciudad c WHERE c.nombre = :nombre", Ciudad.class)
                     .setParameter("nombre", nombre)
                     .getSingleResult();
        em.close();
        return c;
    }

}
