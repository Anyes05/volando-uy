package logica.servicios;

import dato.dao.CompraPaqueteDAO;
import dato.entidades.Cliente;
import dato.entidades.CompraPaquete;
import dato.entidades.PaqueteVuelo;
import logica.DataTypes.DTFecha;
import logica.excepciones.PaqueteException;
import java.util.List;

public class CompraPaqueteServicio {
    private CompraPaqueteDAO compraPaqueteDAO;

    public CompraPaqueteServicio() {
        this.compraPaqueteDAO = new CompraPaqueteDAO();
    }


    public CompraPaquete buscarPorId(Long id) {
        return compraPaqueteDAO.buscarPorId(id);
    }

    public void actualizarCompraPaquete(CompraPaquete compraPaquete) {
        compraPaqueteDAO.actualizar(compraPaquete);
    }

    public void eliminarCompraPaquete(Long id) {
        compraPaqueteDAO.eliminar(id);
    }

    public CompraPaquete registrarCompraPaquete(Cliente clienteSeleccionado, DTFecha fechaCompra, DTFecha vencimiento /*TipoAsiento tipoAsiento*/, PaqueteVuelo paqueteSeleccionado) throws PaqueteException {
        PaqueteVueloServicio servicioPaqueteVuelo = new PaqueteVueloServicio();
        paqueteSeleccionado.setComprado(true);
        servicioPaqueteVuelo.actualizarPaquete(paqueteSeleccionado);
        return compraPaqueteDAO.registrarCompra(clienteSeleccionado, fechaCompra, vencimiento /*tipoAsiento*/, paqueteSeleccionado); // Se guarda en la BD
    }

    public List<CompraPaquete> buscarPorCliente(Cliente cliente) {
        return compraPaqueteDAO.buscarPorCliente(cliente);
    }

}
