package dato.dao;

import dato.entidades.Cliente;
import dato.entidades.CompraComun;
import dato.entidades.Vuelo;
import jakarta.persistence.TypedQuery;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class CompraComunDAO extends GenericDAO<CompraComun> {

    public CompraComunDAO() {
        super(CompraComun.class);
    }

    // Algunos atributos por los que se puede buscar.
    public CompraComun buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CompraComun.class, id);
        } finally {
            em.close();
        }
    }

    public List<CompraComun> buscarPorTipoAsiento(TipoAsiento tipoAsiento) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CompraComun> query = em.createQuery(
                    "SELECT cc FROM CompraComun cc WHERE cc.tipoAsiento = :tipoAsiento", CompraComun.class);
            query.setParameter("tipoAsiento", tipoAsiento);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<CompraComun> buscarPorClienteYVuelo(Cliente cliente, Vuelo vuelo) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CompraComun> query = em.createQuery(
                    "SELECT cc FROM CompraComun cc WHERE cc.cliente = :cliente AND cc.vuelo = :vuelo",
                    CompraComun.class);
            query.setParameter("cliente", cliente);
            query.setParameter("vuelo", vuelo);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public CompraComun crear(Cliente clientePrincipal, DTFecha fechaReserva, TipoAsiento tipoAsiento, int equipajeExtra, Vuelo vueloSeleccionado) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            CompraComun compraComun = new CompraComun(clientePrincipal, fechaReserva, tipoAsiento, equipajeExtra);
            compraComun.setVuelo(vueloSeleccionado); // Usa el método heredado de Reserva

            // Asegurar que las entidades estén en el contexto de persistencia
            Cliente clienteManaged = em.merge(clientePrincipal);
            Vuelo vueloManaged = em.merge(vueloSeleccionado);

            compraComun.setCliente(clienteManaged);
            compraComun.setVuelo(vueloManaged);

            em.persist(compraComun);
            em.flush(); // Forzar la escritura inmediata
            em.getTransaction().commit();
            return compraComun;
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


