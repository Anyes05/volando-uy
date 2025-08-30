package logica.servicios;

import dato.dao.CiudadDAO;
import dato.entidades.Aeropuerto;
import dato.entidades.Ciudad;
import logica.DataTypes.DTFecha;


public class CiudadServicio {
    private CiudadDAO ciudadDAO = new CiudadDAO();

    public void registrarCiudad(String nombre, String pais, Aeropuerto aeropuerto, DTFecha fechaAlta) {
        Ciudad c = new Ciudad(nombre, pais, fechaAlta);
        c.setAeropuerto(aeropuerto);
        ciudadDAO.guardar(c);  // Se guarda en la BD
    }

    public Ciudad obtenerCiudad(Long id) {
        return ciudadDAO.buscarPorId(id);
    }

    public Ciudad buscarCiudadPorNombre(String nombre) {
        return ciudadDAO.buscarCiudadPorNombre(nombre);
    }
}
