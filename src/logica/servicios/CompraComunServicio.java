package logica.servicios;

import dato.dao.CompraComunDAO;
import dato.entidades.CompraComun;

public class CompraComunServicio {
    private CompraComunDAO compraComunDAO;

    public CompraComunServicio() {
        this.compraComunDAO = new CompraComunDAO();
    }

    //public void crearCompraComun(CompraComun compraComun) throws Exception {
    //    compraComunDAO.crear(compraComun);
    //}

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
