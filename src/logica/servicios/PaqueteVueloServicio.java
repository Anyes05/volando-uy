package logica.servicios;

import dato.entidades.PaqueteVuelo;
import dato.dao.PaqueteVueloDAO;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

import java.util.List;

public class PaqueteVueloServicio {
    private PaqueteVueloDAO paqueteVueloDAO = new PaqueteVueloDAO();

    public void registrarPaqueteVuelo(String nombre, String descripcion, TipoAsiento tipoAsiento,
                                      int diasValidos, float descuento, DTCostoBase DTCostoBase, float costoTotal, DTFecha fechaAlta) {
        PaqueteVuelo p = new PaqueteVuelo(nombre, descripcion, tipoAsiento, diasValidos, descuento, DTCostoBase, costoTotal, fechaAlta);
        paqueteVueloDAO.guardar(p);  // Se guarda en la BD
    }

    public PaqueteVuelo obtenerPaqueteVuelo(Long id) {
        return paqueteVueloDAO.buscarPorId(id);
    }

    public List<PaqueteVuelo> listarPaquetes() {
        return paqueteVueloDAO.listarPaquetes();
    }

    public PaqueteVuelo obtenerPaquetePorNombre(String nombre) {
        List<PaqueteVuelo> paquetes = paqueteVueloDAO.listarPaquetes();
        for (PaqueteVuelo p : paquetes) {
            if (p.getNombre().equals(nombre)) {
                return p;
            }
        }
        return null; // Si no se encuentra el paquete
    }
}
