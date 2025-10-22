package dato.dao;

import dato.entidades.CompraPaquete;
import dato.entidades.PaqueteVuelo;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

import dato.entidades.Cliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;
import jakarta.persistence.EntityManager;
import logica.excepciones.PaqueteException;

public class CompraPaqueteDAO extends GenericDAO<CompraPaquete> {

    public CompraPaqueteDAO() {
        super(CompraPaquete.class);
    }

    // Algunos atributos por los que se puede buscar: id, cliente, vencimiento
    public CompraPaquete buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CompraPaquete.class, id);
        } finally {
            em.close();
        }
    }

    public List<CompraPaquete> buscarPorCliente(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CompraPaquete> query = em.createQuery(
                    "SELECT cp FROM CompraPaquete cp WHERE cp.cliente = :cliente", CompraPaquete.class);
            query.setParameter("cliente", cliente);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public CompraPaquete buscarPorVencimiento(String vencimiento) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<CompraPaquete> query = em.createQuery(
                    "SELECT cp FROM CompraPaquete cp WHERE cp.vencimiento = :vencimiento", CompraPaquete.class);
            query.setParameter("vencimiento", vencimiento);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    public CompraPaquete registrarCompra(Cliente clienteSeleccionado, DTFecha fechaCompra, DTFecha vencimiento, /*TipoAsiento tipoAsiento,*/ PaqueteVuelo paqueteSeleccionado) throws PaqueteException {
        EntityManager em = emf.createEntityManager();
        try {
            CompraPaquete compraPaquete = new CompraPaquete();
            compraPaquete.setCliente(clienteSeleccionado);
            compraPaquete.setFechaReserva(fechaCompra);
            compraPaquete.setVencimiento(vencimiento);
//        compraPaquete.setTipoAsiento(tipoAsiento);
            compraPaquete.setPaqueteVuelo(paqueteSeleccionado);

            em.getTransaction().begin();
            em.persist(compraPaquete);
            em.getTransaction().commit();
            return compraPaquete;
        } finally {
            em.close();
        }
    }
}
