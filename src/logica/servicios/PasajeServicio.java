package logica.servicios;

import dato.entidades.*;
import dato.dao.PasajeDAO;
import logica.DataTypes.TipoAsiento;

public class PasajeServicio {
    private PasajeDAO pasajeDAO = new PasajeDAO();

    public void registrarPasaje(Cliente pasajero, Reserva reserva, TipoAsiento tipoAsiento, int costoPasaje) {
        Pasaje p = new Pasaje(pasajero, reserva, tipoAsiento, costoPasaje);
        pasajeDAO.guardar(p);  // Se guarda en la BD
    }

    public Pasaje obtenerPasaje(Long id) {
        return pasajeDAO.buscarPorId(id);
    }
}
