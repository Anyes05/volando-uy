package logica.servicios;

import dato.dao.VueloDAO;
import dato.entidades.Vuelo;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import java.util.List;
import java.util.ArrayList;

public class VueloServicio {
    private VueloDAO vueloDAO = new VueloDAO();

    public void registrarVuelo(String nombre, DTFecha fechaVuelo, DTHora horaVuelo, DTHora duracion, int asientosMaxTurista, int asientosMaxEjecutivo, DTFecha fechaAlta) {
        Vuelo v = new Vuelo(nombre, fechaVuelo, horaVuelo, duracion, asientosMaxTurista, asientosMaxEjecutivo, fechaAlta);
        vueloDAO.guardar(v);  // Se guarda en la BD
    }

    public Vuelo obtenerVuelo(Long id) {
        return vueloDAO.buscarPorId(id);
    }

    public Vuelo buscarVueloPorNombre(String nombre) {
        return vueloDAO.buscarPorNombre(nombre);
    }

    public List<Vuelo> listarVuelos() {
        return vueloDAO.listarVuelos();
    }

    public void actualizarVuelo(Vuelo vuelo) {
        vueloDAO.actualizar(vuelo);
    }

}
