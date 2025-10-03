package logica.servicios;

import dato.dao.CiudadDAO;
import dato.dao.AeropuertoDAO;
import dato.entidades.Aeropuerto;
import dato.entidades.Ciudad;
import logica.DataTypes.DTCiudad;
import logica.DataTypes.DTFecha;

import java.util.ArrayList;
import java.util.List;

public class CiudadServicio {
    private CiudadDAO ciudadDAO = new CiudadDAO();
    private AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();

    public void registrarCiudad(String nombre, String pais, String nombreAeropuerto, String descripcion, String sitioWeb, DTFecha fechaAlta) {
        // Verificar si la ciudad ya existe
        if (ciudadDAO.existeCiudadPorNombreYPais(nombre, pais)) {
            throw new IllegalArgumentException("Ya existe una ciudad con el nombre '" + nombre + "' en el país '" + pais + "'.");
        }

        // Crear la ciudad
        Ciudad ciudad = new Ciudad(nombre, pais, fechaAlta);
        ciudadDAO.guardar(ciudad);

        // Si se proporciona un nombre de aeropuerto, crear el aeropuerto
        if (nombreAeropuerto != null && !nombreAeropuerto.trim().isEmpty()) {
            // Verificar si el aeropuerto ya existe
            if (!aeropuertoDAO.existeAeropuertoPorNombre(nombreAeropuerto)) {
                Aeropuerto aeropuerto = new Aeropuerto(nombreAeropuerto, ciudad);
                aeropuertoDAO.guardar(aeropuerto);
            }
        }
    }

    public Ciudad obtenerCiudad(Long id) {
        return ciudadDAO.buscarPorId(id);
    }

    public Ciudad buscarCiudadPorNombre(String nombre) {
        return ciudadDAO.buscarCiudadPorNombre(nombre);
    }

    public Ciudad buscarCiudadPorNombreYPais(String nombre, String pais) {
        return ciudadDAO.buscarCiudadPorNombreYPais(nombre, pais);
    }

    public List<DTCiudad> listarCiudades() {
        List<Ciudad> ciudades = ciudadDAO.listarCiudades();
        List<DTCiudad> dtCiudades = new ArrayList<>();
        for (Ciudad ciudad : ciudades) {
            dtCiudades.add(new DTCiudad(
                    ciudad.getNombre(),
                    ciudad.getPais()
            ));
        }
        return dtCiudades;
    }
}
