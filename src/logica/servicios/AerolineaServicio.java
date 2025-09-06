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
    
    // Método para precargar aerolíneas
    public void precargarAerolineas() {
        try {
            // Datos de aerolíneas reales y ficticias
            Object[][] aerolineasData = {
                // {nickname, nombre, correo, descripcion, linkSitioWeb}
                {"pluna", "PLUNA", "info@pluna.com.uy", "Aerolínea nacional de Uruguay", "https://www.pluna.com.uy"},
                {"latam", "LATAM Airlines", "info@latam.com", "Aerolínea líder en América Latina", "https://www.latam.com"},
                {"aerolineas", "Aerolíneas Argentinas", "info@aerolineas.com.ar", "Aerolínea de bandera argentina", "https://www.aerolineas.com.ar"},
                {"gol", "GOL Linhas Aéreas", "info@gol.com.br", "Aerolínea brasileña de bajo costo", "https://www.voegol.com.br"},
                {"sky", "Sky Airline", "info@skyairline.com", "Aerolínea chilena de bajo costo", "https://www.skyairline.com"},
                {"avianca", "Avianca", "info@avianca.com", "Aerolínea colombiana", "https://www.avianca.com"},
                {"copa", "Copa Airlines", "info@copa.com", "Aerolínea panameña", "https://www.copa.com"},
                {"iberia", "Iberia", "info@iberia.com", "Aerolínea española", "https://www.iberia.com"},
                {"airfrance", "Air France", "info@airfrance.com", "Aerolínea francesa", "https://www.airfrance.com"},
                {"american", "American Airlines", "info@aa.com", "Aerolínea estadounidense", "https://www.aa.com"},
                {"delta", "Delta Air Lines", "info@delta.com", "Aerolínea estadounidense", "https://www.delta.com"},
                {"united", "United Airlines", "info@united.com", "Aerolínea estadounidense", "https://www.united.com"}
            };
            
            for (Object[] aerolineaData : aerolineasData) {
                String nickname = (String) aerolineaData[0];
                
                // Verificar si la aerolínea ya existe
                if (aerolineaDAO.buscarPorNickname(nickname) == null) {
                    try {
                        crearAerolinea(
                            nickname,
                            (String) aerolineaData[1], // nombre
                            (String) aerolineaData[2], // correo
                            (String) aerolineaData[3], // descripcion
                            (String) aerolineaData[4]  // linkSitioWeb
                        );
                        System.out.println("Aerolínea precargada: " + nickname);
                    } catch (Exception e) {
                        System.err.println("Error al precargar aerolínea " + nickname + ": " + e.getMessage());
                    }
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error en la precarga de aerolíneas: " + e.getMessage(), e);
        }
    }
}
