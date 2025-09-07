package dato.dao;

import dato.entidades.CompraPaquete;
import dato.entidades.PaqueteVuelo;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import dato.entidades.Cliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraPaqueteDAO extends GenericDAO<CompraPaquete> {

    public CompraPaqueteDAO() {
        super(CompraPaquete.class);
    }

    // Algunos atributos por los que se puede buscar: id, cliente, vencimiento
    public CompraPaquete buscarPorId(Long id) {
        return em.find(CompraPaquete.class, id);
    }

    public List<CompraPaquete> buscarPorCliente(Cliente cliente) {
        TypedQuery<CompraPaquete> query = em.createQuery(
                "SELECT cp FROM CompraPaquete cp WHERE cp.cliente = :cliente", CompraPaquete.class);
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }

    public CompraPaquete buscarPorVencimiento(String vencimiento) {
        TypedQuery<CompraPaquete> query = em.createQuery(
                "SELECT cp FROM CompraPaquete cp WHERE cp.vencimiento = :vencimiento", CompraPaquete.class);
        query.setParameter("vencimiento", vencimiento);
        return query.getResultStream().findFirst().orElse(null);
    }

    public CompraPaquete registrarCompra (Cliente clienteSeleccionado, DTFecha fechaCompra, DTFecha vencimiento, /*TipoAsiento tipoAsiento,*/ PaqueteVuelo paqueteSeleccionado) throws Exception {
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
    }
}
