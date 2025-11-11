package logica.servicios;

import dato.dao.GenericDAO;
import dato.entidades.Seguidores;
import dato.entidades.Usuario;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SeguidoresServicio {
    private GenericDAO<Seguidores> seguidoresDAO;
    private GenericDAO<Usuario> usuarioDAO;

    public SeguidoresServicio() {
        this.seguidoresDAO = new GenericDAO<>(Seguidores.class);
        this.usuarioDAO = new GenericDAO<>(Usuario.class);
    }

    // Crear relación de seguimiento
    public void seguir(String nickSeguidor, String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            Usuario seguidor = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickSeguidor)
                    .getSingleResult();

            Usuario seguido = em.createQuery(
                            "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class)
                    .setParameter("nick", nickSeguido)
                    .getSingleResult();

            // Validaciones
            if (seguidor.equals(seguido)) {
                throw new IllegalArgumentException("Un usuario no puede seguirse a sí mismo");
            }

            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                            Long.class)
                    .setParameter("seguidor", nickSeguidor)
                    .setParameter("seguido", nickSeguido)
                    .getSingleResult();

            if (count > 0) {
                throw new IllegalArgumentException("Ya existe la relación de seguimiento");
            }

            // Persistir
            em.getTransaction().begin();
            Seguidores s = new Seguidores();
            s.setSeguidor(seguidor);
            s.setSeguido(seguido);
            em.persist(s);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Eliminar relación de seguimiento
    public void dejarDeSeguir(String nickSeguidor, String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Seguidores relacion = em.createQuery(
                            "SELECT s FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                            Seguidores.class)
                    .setParameter("seguidor", nickSeguidor)
                    .setParameter("seguido", nickSeguido)
                    .getSingleResult();

            em.remove(relacion);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Listar a quién sigue un usuario
    public List<String> listarSeguidos(String nickSeguidor) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.seguido.nickname FROM Seguidores s WHERE s.seguidor.nickname = :nick",
                            String.class)
                    .setParameter("nick", nickSeguidor)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Listar quiénes siguen a un usuario
    public List<String> listarSeguidores(String nickSeguido) {
        EntityManager em = GenericDAO.emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s.seguidor.nickname FROM Seguidores s WHERE s.seguido.nickname = :nick",
                            String.class)
                    .setParameter("nick", nickSeguido)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}