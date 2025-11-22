package logica.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import logica.clase.Sistema;
import logica.DataTypes.*;

import java.util.List;

/**
 * WebService principal que expone funcionalidad del Sistema Central.
 * 
 * Este servicio expone los métodos principales del sistema para que puedan
 * ser consumidos remotamente por el Servidor Web y el Dispositivo Móvil.
 * 
 * La configuración de IP, puerto y URL se lee desde config.properties.
 */
@WebService(name = "CentralWS", targetNamespace = "http://ws.logica/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class CentralWS {

    private final Sistema sistema = Sistema.getInstance();

    // ===========================
    //      MÉTODO DE PRUEBA
    // ===========================

    @WebMethod(operationName = "ping")
    public String ping() {
        return "Servicio CentralWS operativo - " + System.currentTimeMillis();
    }

    // ===========================
    //      AEROLÍNEAS
    // ===========================

    @WebMethod(operationName = "listarAerolineas")
    @WebResult(name = "aerolineas")
    public List<DTAerolinea> listarAerolineas() {
        return sistema.listarAerolineas();
    }

    // ===========================
    //         CIUDADES
    // ===========================

    @WebMethod(operationName = "listarCiudades")
    @WebResult(name = "ciudades")
    public List<DTCiudad> listarCiudades() {
        return sistema.listarCiudades();
    }

    @WebMethod(operationName = "listarAeropuertos")
    @WebResult(name = "aeropuertos")
    public List<String> listarAeropuertos() {
        return sistema.listarAeropuertos();
    }

    // ===========================
    //         RUTAS DE VUELO
    // ===========================

    @WebMethod(operationName = "listarRutasPorAerolinea")
    @WebResult(name = "rutasVuelo")
    public List<DTRutaVuelo> listarRutasPorAerolinea(
            @WebParam(name = "nicknameAerolinea") String nickname) {
        return sistema.listarRutaVuelo(nickname);
    }

    @WebMethod(operationName = "listarRutasDeVuelo")
    @WebResult(name = "rutasVuelo")
    public List<DTRutaVuelo> listarRutasDeVuelo() {
        return sistema.listarRutaVueloDeVuelo();
    }

    // ===========================
    //         VUELOS
    // ===========================

    @WebMethod(operationName = "listarVuelosDeRuta")
    @WebResult(name = "vuelos")
    public List<DTVuelo> listarVuelosDeRuta(
            @WebParam(name = "nombreRuta") String nombreRuta) {
        return sistema.seleccionarRutaVuelo(nombreRuta);
    }

    // ===========================
    //         CLIENTES
    // ===========================

    @WebMethod(operationName = "listarClientes")
    @WebResult(name = "clientes")
    public List<DTCliente> listarClientes() {
        return sistema.listarClientes();
    }

    @WebMethod(operationName = "mostrarDatosUsuario")
    @WebResult(name = "usuario")
    public DTUsuario mostrarDatosUsuario(
            @WebParam(name = "nickname") String nickname) {
        return sistema.mostrarDatosUsuario(nickname);
    }

    @WebMethod(operationName = "consultarUsuarios")
    @WebResult(name = "usuarios")
    public List<DTUsuario> consultarUsuarios() {
        return sistema.consultarUsuarios();
    }

    // ===========================
    //         RESERVAS
    // ===========================

    @WebMethod(operationName = "listarReservasVuelo")
    @WebResult(name = "reservas")
    public List<DTVueloReserva> listarReservasVuelo(
            @WebParam(name = "nombreVuelo") String nombre) {
        return sistema.listarReservasVuelo(nombre);
    }

    // ===========================
    //         PAQUETES
    // ===========================

    @WebMethod(operationName = "mostrarPaquetes")
    @WebResult(name = "paquetes")
    public List<DTPaqueteVuelos> mostrarPaquetes() {
        return sistema.mostrarPaquete();
    }

    @WebMethod(operationName = "obtenerPaquetesNoComprados")
    @WebResult(name = "paquetes")
    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados() {
        return sistema.obtenerPaquetesNoComprados();
    }
}
