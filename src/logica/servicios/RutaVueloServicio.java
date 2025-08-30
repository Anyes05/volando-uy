package logica.servicios;

import dato.dao.RutaVueloDAO;
import dato.entidades.Aerolinea;
import dato.entidades.Categoria;
import dato.entidades.Ciudad;
import dato.entidades.RutaVuelo;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;

import java.util.List;

public class RutaVueloServicio {
    private RutaVueloDAO rutaVueloDAO = new RutaVueloDAO();

    public void registrarRutaVuelo(String nombre, String descripcion, DTCostoBase DTCostoBase, DTFecha fechaAlta, Aerolinea aerolinea, Ciudad ciudadOrigen, Ciudad ciudadDestino, List<Categoria> categorias) {
        RutaVuelo rv = new RutaVuelo(nombre, descripcion, DTCostoBase, fechaAlta);
        rv.setCiudadDestino(ciudadDestino);
        rv.setCiudadOrigen(ciudadOrigen);
        rv.getAerolineas().add(aerolinea); // Se asocia la aerolinea a la ruta de vuelo
        rv.setCategorias(categorias);
        rutaVueloDAO.guardar(rv);  // Se guarda en la BD
    }


    public RutaVuelo obtenerRutaVuelo(Long id) {
        return rutaVueloDAO.buscarPorId(id);
    }


}
