package dato.dao;

import dato.entidades.PaqueteVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTCiudad;
import logica.DataTypes.DTRutaVuelo;

import java.util.List;
import java.util.ArrayList;

public class PaqueteVueloDAO {
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("volandouyPU");

    // Guardar un paquete de vuelo en la Base de Datos
    public void guardar(PaqueteVuelo paqueteVuelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(paqueteVuelo);
        em.getTransaction().commit();
        em.close();
    }

    // Buscar paquete de vuelo por id
    public PaqueteVuelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        PaqueteVuelo p = em.find(PaqueteVuelo.class, id);
        em.close();
        return p;
    }

    public List<PaqueteVuelo> listarPaquetes() {
        EntityManager em = emf.createEntityManager();
        List<PaqueteVuelo> paquetes = em.createQuery("SELECT p FROM PaqueteVuelo p", PaqueteVuelo.class).getResultList();
        em.close();
        return paquetes;
    }

    public PaqueteVuelo actualizar(PaqueteVuelo paquete) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        PaqueteVuelo merged = em.merge(paquete); // reatacha y sincroniza
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public List<DTRutaVuelo> obtenerRutasDePaquete(PaqueteVuelo paquete) {
        EntityManager em = emf.createEntityManager();
        PaqueteVuelo p = em.find(PaqueteVuelo.class, paquete.getId());
        List<DTRutaVuelo> rutas = new ArrayList<>();
        DTAerolinea aerolinea = null;
        if (p != null) {
            for (dato.entidades.Cantidad c : p.getCantidad()) {
                rutas.add(new DTRutaVuelo(
                        c.getRutaVuelo().getNombre(),
                        c.getRutaVuelo().getDescripcion(),
                        c.getRutaVuelo().getFechaAlta(),
                        c.getRutaVuelo().getCostoBase(),
                        aerolinea,
                        new DTCiudad(c.getRutaVuelo().getCiudadOrigen().getNombre(), c.getRutaVuelo().getCiudadOrigen().getPais()),
                        new DTCiudad(c.getRutaVuelo().getCiudadDestino().getNombre(), c.getRutaVuelo().getCiudadDestino().getPais()),
                        c.getRutaVuelo().getFoto(),
                        c.getRutaVuelo().getEstado()
                ));
            }
        }
        return rutas;
    }
}
