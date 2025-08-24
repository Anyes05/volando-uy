package logica.servicios;

import dato.dao.RutaVueloDAO;
import dato.entidades.RutaVuelo;
import logica.DataTypes.CostoBase;
import logica.DataTypes.DTFecha;

public class RutaVueloServicio {
    private RutaVueloDAO rutaVueloDAO = new RutaVueloDAO();

    public void registrarRutaVuelo(String nombre, String descripcion, CostoBase costoBase, DTFecha fechaAlta) {
        RutaVuelo rv = new RutaVuelo(nombre, descripcion, costoBase, fechaAlta);
        rutaVueloDAO.guardar(rv);  // Se guarda en la BD
    }

    public RutaVuelo obtenerRutaVuelo(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }
}
