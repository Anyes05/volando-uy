package logica.servicios;

import dato.dao.AerolineaDAO;
import dato.entidades.Aerolinea;
import java.util.List;

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
}
