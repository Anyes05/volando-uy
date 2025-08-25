package logica.servicios;

import dato.entidades.PaqueteVuelo;
import dato.dao.PaqueteVueloDAO;
import logica.DataTypes.CostoBase;
import logica.DataTypes.TipoAsiento;

public class PaqueteVueloServicio {
    private PaqueteVueloDAO paqueteVueloDAO = new PaqueteVueloDAO();

    public void registrarPaqueteVuelo(String nombre, String descripcion, TipoAsiento tipoAsiento,
                                      int diasValidos, float descuento, CostoBase costoBase, float costoTotal) {
        PaqueteVuelo p = new PaqueteVuelo(nombre, descripcion, tipoAsiento, diasValidos, descuento, costoBase, costoTotal);
        paqueteVueloDAO.guardar(p);  // Se guarda en la BD
    }

    public PaqueteVuelo obtenerPaqueteVuelo(Long id) {
        return paqueteVueloDAO.buscarPorId(id);
    }
}
