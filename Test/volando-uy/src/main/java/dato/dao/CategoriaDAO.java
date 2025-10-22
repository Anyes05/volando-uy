package dato.dao;

import dato.entidades.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class CategoriaDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

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

    public Categoria buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        Categoria categoria = null;
        try {
            categoria = em.createQuery("SELECT c FROM Categoria c WHERE LOWER(c.nombre) = :nombre", Categoria.class)
                    .setParameter("nombre", nombre.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
        return categoria;
    }

    public List<Categoria> listarCategorias() {
        EntityManager em = emf.createEntityManager();
        List<Categoria> lista;
        try {
            lista = em.createQuery("SELECT c FROM Categoria c", Categoria.class).getResultList();
        } finally {
            em.close();
        }
        return lista;
    }
}
