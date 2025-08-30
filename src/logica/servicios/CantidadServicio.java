package logica.servicios;

import dato.entidades.Cantidad;
import dato.dao.CantidadDAO;

public class CantidadServicio {
    private CantidadDAO cantidadDAO = new CantidadDAO();

    public void registrarCantidad(int cant) {
        Cantidad c = new Cantidad(cant);
        cantidadDAO.guardar(c);
    }

    public Cantidad obtenerCantidad(Long id) {
        return cantidadDAO.buscarPorId(id);
    }
}
