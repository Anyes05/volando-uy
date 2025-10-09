package dato.dao;

import dato.entidades.RutaVuelo;
import logica.DataTypes.EstadoRutaVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class RutaVueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar una ruta de vuelo en la Base de Datos
    public void guardar(RutaVuelo rutaVuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(rutaVuelo);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar ruta de vuelo por id
    public RutaVuelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        RutaVuelo rv = em.find(RutaVuelo.class, id);
        em.close();
        return rv;
    }

    // Buscar ruta de vuelo por nombre
    public RutaVuelo buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT rv FROM RutaVuelo rv WHERE rv.nombre = :nombre",
                            RutaVuelo.class
                    )
                    .setParameter("nombre", nombre)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    // Listar rutas de vuelo por estado
    public List<RutaVuelo> listarPorEstado(EstadoRutaVuelo estado) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT rv FROM RutaVuelo rv WHERE rv.estado = :estado",
                            RutaVuelo.class
                    )
                    .setParameter("estado", estado)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Listar rutas de vuelo por aerolínea y estado
    public List<RutaVuelo> listarPorAerolineaYEstado(String aerolineaNickname, EstadoRutaVuelo estado) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT rv FROM RutaVuelo rv JOIN rv.aerolineas a WHERE a.nickname = :aerolineaNickname AND rv.estado = :estado",
                            RutaVuelo.class
                    )
                    .setParameter("aerolineaNickname", aerolineaNickname)
                    .setParameter("estado", estado)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Actualizar estado de una ruta de vuelo
    public void actualizarEstado(Long id, EstadoRutaVuelo nuevoEstado) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RutaVuelo rutaVuelo = em.find(RutaVuelo.class, id);
            if (rutaVuelo != null) {
                rutaVuelo.setEstado(nuevoEstado);
                em.merge(rutaVuelo);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

