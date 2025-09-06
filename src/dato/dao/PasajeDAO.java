package dato.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import dato.entidades.Pasaje;

public class PasajeDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar un pasaje en la Base de Datos
    public void guardar(Pasaje pasaje) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(pasaje);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    // Buscar pasaje por id
    public Pasaje buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Pasaje.class, id);
        } finally {
            em.close();
        }
    }

    public Pasaje crear(dato.entidades.Cliente pasajero, dato.entidades.Reserva reserva, logica.DataTypes.TipoAsiento tipoAsiento) throws Exception {
        Pasaje pasaje = new Pasaje();
        pasaje.setPasajero(pasajero);
        pasaje.setReserva(reserva);
        pasaje.setTipoAsiento(tipoAsiento);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Asegurar que las entidades estén en el contexto de persistencia
            dato.entidades.Cliente clienteManaged = em.merge(pasajero);
            dato.entidades.Reserva reservaManaged = em.merge(reserva);
            
            pasaje.setPasajero(clienteManaged);
            pasaje.setReserva(reservaManaged);
            
            em.persist(pasaje);
            em.flush(); // Forzar la escritura inmediata
            em.getTransaction().commit();
            return pasaje;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}

