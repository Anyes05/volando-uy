package logica.servicios;

import dato.entidades.*;
import dato.dao.PasajeDAO;
import logica.DataTypes.TipoAsiento;

public class PasajeServicio {
    private PasajeDAO pasajeDAO = new PasajeDAO();

    public void registrarPasaje(Pasaje p) {
        pasajeDAO.guardar(p);  // Se guarda en la BD
    }

    public Pasaje crearPasaje (Cliente pasajero, Reserva reserva, TipoAsiento tipoAsiento) throws Exception {
        return pasajeDAO.crear(pasajero, reserva, tipoAsiento);
    }

    public Pasaje obtenerPasaje(Long id) {
        return pasajeDAO.buscarPorId(id);
    }
}
