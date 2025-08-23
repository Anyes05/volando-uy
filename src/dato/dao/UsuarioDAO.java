package dato.dao;

import dato.entidades.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UsuarioDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

    // Guardar un usuario en la BD
    public void guardar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);   // INSERT en la BD
        em.getTransaction().commit();
        em.close();
    }

    // Buscar usuario por id
    public Usuario buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Usuario u = em.find(Usuario.class, id); // SELECT en la BD
        em.close();
        return u;
    }
}
