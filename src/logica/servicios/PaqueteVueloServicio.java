package logica.servicios;

import dato.entidades.PaqueteVuelo;
import dato.dao.PaqueteVueloDAO;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.TipoAsiento;

import java.util.List;

public class PaqueteVueloServicio {
    private PaqueteVueloDAO paqueteVueloDAO = new PaqueteVueloDAO();

    public PaqueteVuelo registrarPaqueteVuelo(String nombrePaquete, String descripcion/*TipoAsiento tipoAsiento*/, int diasValidos, float descuento, DTFecha fechaAlta) {
        PaqueteVuelo p = new PaqueteVuelo(nombrePaquete, descripcion/*null*/, diasValidos, descuento, fechaAlta);
        paqueteVueloDAO.guardar(p);  // Se guarda en la BD
        return p;
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

    public void actualizarPaquete(PaqueteVuelo paqueteVuelo) {
        paqueteVueloDAO.actualizar(paqueteVuelo);
    }

    public List<DTRutaVuelo> obtenerRutasDePaquete(String nombrePaquete) {
        PaqueteVuelo paquete = obtenerPaquetePorNombre(nombrePaquete);
        if (paquete == null) {
            throw new IllegalStateException("No se encontró un paquete con el nombre: " + nombrePaquete);
        }
        return paqueteVueloDAO.obtenerRutasDePaquete(paquete);
    }
}
