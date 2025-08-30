package logica.servicios;

import dato.dao.AeropuertoDAO;
import dato.entidades.Aeropuerto;
import dato.entidades.Ciudad;

public class AeropuertoServicio {
    private AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();

    public void registrarAeropuerto(String nombre, Ciudad ciudad) {
        Aeropuerto a = new Aeropuerto(nombre, ciudad);
        aeropuertoDAO.guardar(a);  // Se guarda en la BD
    }

    public Aeropuerto obtenerAeropuerto(Long id) {
        return aeropuertoDAO.buscarPorId(id);
    }
}
