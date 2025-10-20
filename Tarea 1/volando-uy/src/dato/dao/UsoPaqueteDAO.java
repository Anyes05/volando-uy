package dato.dao;

import dato.entidades.UsoPaquete;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsoPaqueteDAO extends GenericDAO<UsoPaquete> {

    public UsoPaqueteDAO() {
        super(UsoPaquete.class);
    }

    public void registrarUso(UsoPaquete usoPaquete) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(usoPaquete);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<UsoPaquete> buscarPorCompraPaquete(dato.entidades.CompraPaquete compraPaquete) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<UsoPaquete> query = em.createQuery(
                    "SELECT up FROM UsoPaquete up WHERE up.compraPaquete = :compraPaquete", UsoPaquete.class);
            query.setParameter("compraPaquete", compraPaquete);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
