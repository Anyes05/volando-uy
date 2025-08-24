package dato.dao;

import dato.entidades.Aeropuerto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class AeropuertoDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miPU");

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
}
