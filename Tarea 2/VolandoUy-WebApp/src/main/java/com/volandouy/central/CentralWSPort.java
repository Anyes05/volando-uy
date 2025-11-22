package com.volandouy.central;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import logica.DataTypes.*;

import java.util.List;

/**
 * Interfaz del Web Service CentralWS para consumo desde el cliente.
 * Esta interfaz debe coincidir con los métodos expuestos en logica.ws.CentralWS
 */
@WebService(name = "CentralWS", targetNamespace = "http://ws.logica/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface CentralWSPort {

    @WebMethod(operationName = "ping")
    @WebResult(name = "return")
    String ping();

    @WebMethod(operationName = "listarAerolineas")
    @WebResult(name = "aerolineas")
    List<DTAerolinea> listarAerolineas();

    @WebMethod(operationName = "listarCiudades")
    @WebResult(name = "ciudades")
    List<DTCiudad> listarCiudades();

    @WebMethod(operationName = "listarAeropuertos")
    @WebResult(name = "aeropuertos")
    List<String> listarAeropuertos();

    @WebMethod(operationName = "listarRutasPorAerolinea")
    @WebResult(name = "rutasVuelo")
    List<DTRutaVuelo> listarRutasPorAerolinea(
            @WebParam(name = "nicknameAerolinea") String nickname);

    @WebMethod(operationName = "listarRutasDeVuelo")
    @WebResult(name = "rutasVuelo")
    List<DTRutaVuelo> listarRutasDeVuelo();

    @WebMethod(operationName = "listarVuelosDeRuta")
    @WebResult(name = "vuelos")
    List<DTVuelo> listarVuelosDeRuta(
            @WebParam(name = "nombreRuta") String nombreRuta);

    @WebMethod(operationName = "listarClientes")
    @WebResult(name = "clientes")
    List<DTCliente> listarClientes();

    @WebMethod(operationName = "mostrarDatosUsuario")
    @WebResult(name = "usuario")
    DTUsuario mostrarDatosUsuario(
            @WebParam(name = "nickname") String nickname);

    @WebMethod(operationName = "consultarUsuarios")
    @WebResult(name = "usuarios")
    List<DTUsuario> consultarUsuarios();

    @WebMethod(operationName = "listarReservasVuelo")
    @WebResult(name = "reservas")
    List<DTVueloReserva> listarReservasVuelo(
            @WebParam(name = "nombreVuelo") String nombre);

    @WebMethod(operationName = "mostrarPaquetes")
    @WebResult(name = "paquetes")
    List<DTPaqueteVuelos> mostrarPaquetes();

    @WebMethod(operationName = "obtenerPaquetesNoComprados")
    @WebResult(name = "paquetes")
    List<DTPaqueteVuelos> obtenerPaquetesNoComprados();
}

