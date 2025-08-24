package dato.dao;

import dato.entidades.Vuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class VueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

    // Guardar un vuelo en la Base de Datos
    public void guardar(Vuelo vuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(vuelo);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar vuelo por id
    public Vuelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Vuelo v = em.find(Vuelo.class, id);
        em.close();
        return v;
    }
}
