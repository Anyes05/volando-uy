package dato.dao;

import dato.entidades.Aeropuerto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AeropuertoDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar un aeropuerto en la Base de Datos
    public void guardar(Aeropuerto aeropuerto) {
        EntityManager em = emf.createEntityManager(); // entity manager para interactuar con la base de datos
        em.getTransaction().begin(); // iniciar transacción
        em.persist(aeropuerto);   // INSERT en la Base de Datos
        em.getTransaction().commit(); // confirmar la transacción
        em.close(); // cerrar el entity manager
    }

    // Buscar aeropuerto por id
    public Aeropuerto buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager(); // nuevo entity manager
        Aeropuerto a = em.find(Aeropuerto.class, id); // SELECT en la Base de Datos, busca el aeropuerto con el id asociado
        em.close(); // cerrar el entity manager
        return a; // retornar el aeropuerto encontrado
    }
    
    // Verificar si existe un aeropuerto por nombre
    public boolean existeAeropuertoPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Aeropuerto> query = em.createQuery(
                "SELECT a FROM Aeropuerto a WHERE a.nombre = :nombre", Aeropuerto.class);
            query.setParameter("nombre", nombre);
            return !query.getResultList().isEmpty();
        } finally {
            em.close();
        }
    }
    
    // Listar todos los aeropuertos
    public static List<Aeropuerto> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Aeropuerto> query = em.createQuery(
                "SELECT a FROM Aeropuerto a", Aeropuerto.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    // Buscar aeropuerto por nombre
    public Aeropuerto buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Aeropuerto> query = em.createQuery(
                "SELECT a FROM Aeropuerto a WHERE a.nombre = :nombre", Aeropuerto.class);
            query.setParameter("nombre", nombre);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }
}
