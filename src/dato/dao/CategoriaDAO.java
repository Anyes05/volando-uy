package dato.dao;

import dato.entidades.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CategoriaDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

    // Guardar una categoria en la Base de Datos
    public void guardar(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(categoria);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar categoria por id
    public Categoria buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Categoria c = em.find(Categoria.class, id);
        em.close();
        return c;
    }
}
