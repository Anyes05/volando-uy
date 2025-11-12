package logica.servicios;

import dato.entidades.Cantidad;
import dato.dao.CantidadDAO;

public class CantidadServicio {
    private CantidadDAO cantidadDAO = new CantidadDAO();

    public Cantidad registrarCantidad(int cant) {
        Cantidad c = new Cantidad(cant);
        cantidadDAO.guardar(c);
        return c;
    }

    public Cantidad obtenerCantidad(Long id) {
        return cantidadDAO.buscarPorId(id);
    }

    public void eliminarCantidad(Cantidad cantidad) {
        cantidadDAO.eliminar(cantidad);
    }
}
