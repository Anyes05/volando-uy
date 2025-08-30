package logica.servicios;

import dato.dao.AerolineaDAO;
import dato.entidades.Aerolinea;
import logica.DataTypes.DTRutaVuelo;
import dato.entidades.RutaVuelo;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTCiudad;
import java.util.List;
import java.util.ArrayList;

public class AerolineaServicio {
    private AerolineaDAO aerolineaDAO;

    public AerolineaServicio() {
        this.aerolineaDAO = new AerolineaDAO();
    }

    public void crearAerolinea(String nickname, String nombre, String correo,
                               String descripcion, String linkSitioWeb) throws Exception {
        if (aerolineaDAO.buscarPorNickname(nickname) != null) {
            throw new Exception("Ya existe una aerolínea con ese nickname.");
        }

        Aerolinea aerolinea = new Aerolinea(nickname, nombre, correo, descripcion, linkSitioWeb);
        aerolineaDAO.crear(aerolinea);
    }

    public Aerolinea buscarAerolineaPorId(Long id) {
        return aerolineaDAO.buscarPorId(id);
    }

    public Aerolinea buscarAerolineaPorNickname(String nickname) {
        return aerolineaDAO.buscarPorNickname(nickname);
    }

    public List<Aerolinea> listarAerolineas() {
        return aerolineaDAO.listarTodos();
    }

    public void actualizarAerolinea(Aerolinea aerolinea) {
        aerolineaDAO.actualizar(aerolinea);
    }

    public void eliminarAerolinea(Long id) {
        aerolineaDAO.eliminar(id);
    }

    // AerolineaServicio.java
    public List<DTRutaVuelo> obtenerRutasDeAerolinea(String nickname) {
        Aerolinea aerolinea = buscarAerolineaPorNickname(nickname);
        if (aerolinea == null) {
            throw new IllegalStateException("No se encontró una aerolínea con el nickname: " + nickname);
        }
        List<DTRutaVuelo> listaRutas = new ArrayList<>();
        for (RutaVuelo r : aerolinea.getRutasVuelo()) {
            listaRutas.add(new DTRutaVuelo(
                    r.getNombre(),
                    r.getDescripcion(),
                    r.getFechaAlta(),
                    r.getCostoBase(),
                    new DTAerolinea(aerolinea.getNickname(), aerolinea.getNombre(), aerolinea.getCorreo(), aerolinea.getDescripcion(), aerolinea.getLinkSitioWeb(), new ArrayList<>()),
                    new DTCiudad(r.getCiudadOrigen().getNombre(), r.getCiudadOrigen().getPais()),
                    new DTCiudad(r.getCiudadDestino().getNombre(), r.getCiudadDestino().getPais())
            ));
        }
        return listaRutas;
    }
}
