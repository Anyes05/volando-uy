package logica.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import logica.clase.Sistema;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;

import java.util.List;

/**
 * WebService principal que expone funcionalidad del Sistema.
 * Es el que tu profesor quiere ver funcionando para la Tarea 3.
 */
@WebService
public class CentralWS {

    private final Sistema sistema = Sistema.getInstance();

    // ===========================
    //      AEROLÍNEAS
    // ===========================

    @WebMethod
    public List<DTAerolinea> listarAerolineas() {
        return sistema.listarAerolineas();
    }

    // ===========================
    //         RUTAS
    // ===========================

    @WebMethod
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nickname) {
        return sistema.listarRutaVuelo(nickname);
    }

    // ===========================
    //         VUELOS
    // ===========================

    @WebMethod
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        return sistema.seleccionarRutaVuelo(nombreRuta);
    }

    // ===========================
    //     EJEMPLO EXTRA (TAREA)
    // ===========================

    @WebMethod
    public String ping() {
        return "Servicio CentralWS operativo";
    }
}
