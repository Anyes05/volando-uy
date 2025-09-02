package logica.servicios;

import dato.dao.CompraComunDAO;
import dato.entidades.CompraComun;
import dato.entidades.Cliente;
import dato.entidades.Vuelo;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraComunServicio {
    private CompraComunDAO compraComunDAO;

    public CompraComunServicio() {
        this.compraComunDAO = new CompraComunDAO();
    }

    public CompraComun crearCompraComun(Cliente clientePrincipal, DTFecha fechaReserva, TipoAsiento tipoAsiento, int equipajeExtra, Vuelo vueloSeleccionado) throws Exception {
        return compraComunDAO.crear(clientePrincipal, fechaReserva, tipoAsiento, equipajeExtra, vueloSeleccionado);
    }

    public CompraComun buscarPorId(Long id) {
        return compraComunDAO.buscarPorId(id);
    }

    public void actualizarCompraComun(CompraComun compraComun) {
        compraComunDAO.actualizar(compraComun);
    }

    public void eliminarCompraComun(Long id) {
        compraComunDAO.eliminar(id);
    }
}
