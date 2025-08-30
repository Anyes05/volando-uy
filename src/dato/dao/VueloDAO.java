package dato.dao;

import dato.entidades.Vuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.ArrayList;

public class VueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

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

    public Vuelo buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        Vuelo vuelo = em.createQuery(
                        "SELECT v FROM Vuelo v WHERE v.nombre = :nombre", Vuelo.class)
                .setParameter("nombre", nombre)
                .getResultStream()
                .findFirst()
                .orElse(null);
        em.close();
        return vuelo;
    }

    public List<Vuelo> listarVuelos() {
        EntityManager em = emf.createEntityManager();
        List<Vuelo> vuelos = em.createQuery("SELECT v FROM Vuelo v", Vuelo.class).getResultList();
        em.close();
        return vuelos;
    }
}
