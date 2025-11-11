package dato.dao;

import dato.dao.GenericDAO;
import dato.entidades.Seguidores;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SeguidoresDAO extends GenericDAO<Seguidores> {

    public SeguidoresDAO() {
        super(Seguidores.class);
    }

    public List<Seguidores> listarSeguidos(String nicknameSeguidor) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Seguidores s WHERE s.seguidor.nickname = :nick",
                            Seguidores.class
                    ).setParameter("nick", nicknameSeguidor)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Seguidores> listarSeguidores(String nicknameSeguido) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT s FROM Seguidores s WHERE s.seguido.nickname = :nick",
                            Seguidores.class
                    ).setParameter("nick", nicknameSeguido)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public boolean existeRelacion(String seguidor, String seguido) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Seguidores s WHERE s.seguidor.nickname = :seguidor AND s.seguido.nickname = :seguido",
                            Long.class
                    ).setParameter("seguidor", seguidor)
                    .setParameter("seguido", seguido)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}