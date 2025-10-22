package dato.dao;

import dato.entidades.RutaVuelo;
import dato.entidades.Vuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class VueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar un vuelo en la Base de Datos
    public void guardar(Vuelo vuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Traer la ruta desde la BD para que JPA la maneje
        if (vuelo.getRutaVuelo() != null) {
            RutaVuelo ruta = em.find(RutaVuelo.class, vuelo.getRutaVuelo().getId());
            vuelo.setRutaVuelo(ruta);
        }
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

    public void actualizar(Vuelo vuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        // Traer la ruta desde la BD para que JPA la maneje
        if (vuelo.getRutaVuelo() != null) {
            RutaVuelo ruta = em.find(RutaVuelo.class, vuelo.getRutaVuelo().getId());
            vuelo.setRutaVuelo(ruta);
        }
        em.merge(vuelo);
        em.getTransaction().commit();
        em.close();
    }
}

