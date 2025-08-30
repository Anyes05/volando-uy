package logica.servicios;

import dato.dao.CompraPaqueteDAO;
import dato.entidades.CompraPaquete;

public class CompraPaqueteServicio {
    private CompraPaqueteDAO compraPaqueteDAO;

    public CompraPaqueteServicio() {
        this.compraPaqueteDAO = new CompraPaqueteDAO();
    }

    //public void crearCompraPaquete(CompraPaquete compraPaquete) throws Exception {
    //    compraPaqueteDAO.crear(compraPaquete);
    //}

    public CompraPaquete buscarPorId(Long id) {
        return compraPaqueteDAO.buscarPorId(id);
    }

    public void actualizarCompraPaquete(CompraPaquete compraPaquete) {
        compraPaqueteDAO.actualizar(compraPaquete);
    }

    public void eliminarCompraPaquete(Long id) {
        compraPaqueteDAO.eliminar(id);
    }
}
