package dato.dao;

import dato.entidades.Ciudad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CiudadDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

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
        try {
            TypedQuery<Ciudad> query = em.createQuery(
                "SELECT c FROM Ciudad c WHERE c.nombre = :nombre", Ciudad.class);
            query.setParameter("nombre", nombre);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
    
    // Buscar ciudad por nombre y país
    public Ciudad buscarCiudadPorNombreYPais(String nombre, String pais) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ciudad> query = em.createQuery(
                "SELECT c FROM Ciudad c WHERE LOWER (TRIM(c.nombre)) = :nombre AND LOWER (TRIM(c.pais)) = :pais", Ciudad.class);
            query.setParameter("nombre", nombre.trim().toLowerCase());
            query.setParameter("pais", pais.trim().toLowerCase());
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
    
    // Verificar si existe una ciudad por nombre y país
    public boolean existeCiudadPorNombreYPais(String nombre, String pais) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ciudad> query = em.createQuery(
                "SELECT c FROM Ciudad c WHERE LOWER (TRIM(c.nombre)) = :nombre AND LOWER(TRIM(c.pais)) = :pais", Ciudad.class);
            query.setParameter("nombre", nombre.trim().toLowerCase());
            query.setParameter("pais", pais.trim().toLowerCase());
            return !query.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }

    public List<Ciudad> listarCiudades() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Ciudad> query = em.createQuery("SELECT c FROM Ciudad c", Ciudad.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
