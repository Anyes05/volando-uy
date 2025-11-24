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

        // Métodos adicionales
        @WebMethod(operationName = "mostrarDatosUsuarioMod")
        @WebResult(name = "usuario")
        DTUsuario mostrarDatosUsuarioMod(@WebParam(name = "nickname") String nickname);

        @WebMethod(operationName = "seleccionarRutaVueloRet")
        @WebResult(name = "rutaVuelo")
        DTRutaVuelo seleccionarRutaVueloRet(@WebParam(name = "nombreRutaVuelo") String nombreRutaVuelo);

        @WebMethod(operationName = "getCategorias")
        @WebResult(name = "categorias")
        List<DTCategoria> getCategorias();

        @WebMethod(operationName = "precargarSistemaCompleto")
        void precargarSistemaCompleto();

        @WebMethod(operationName = "seleccionarAerolinea")
        void seleccionarAerolinea(@WebParam(name = "nickname") String nickname);

        @WebMethod(operationName = "ingresarDatosRuta")
        void ingresarDatosRuta(
                        @WebParam(name = "nombreRuta") String nombreRuta,
                        @WebParam(name = "descripcion") String descripcion,
                        @WebParam(name = "costoTurista") float costoTurista,
                        @WebParam(name = "costoEjecutivo") float costoEjecutivo,
                        @WebParam(name = "costoEquipajeExtra") float costoEquipajeExtra,
                        @WebParam(name = "ciudadOrigen") String ciudadOrigen,
                        @WebParam(name = "ciudadDestino") String ciudadDestino,
                        @WebParam(name = "fechaAlta") DTFecha fechaAlta,
                        @WebParam(name = "categorias") List<String> categorias,
                        @WebParam(name = "foto") byte[] foto,
                        @WebParam(name = "videoUrl") String videoUrl);

        @WebMethod(operationName = "registrarRuta")
        void registrarRuta();

        @WebMethod(operationName = "EstadoFinalizarRutaVuelo")
        void EstadoFinalizarRutaVuelo(@WebParam(name = "nombreRuta") String nombreRuta);

        @WebMethod(operationName = "incrementarVisitasRuta")
        void incrementarVisitasRuta(@WebParam(name = "nombreRuta") String nombreRuta);

        @WebMethod(operationName = "seleccionarUsuarioAMod")
        void seleccionarUsuarioAMod(@WebParam(name = "nickname") String nickname);

        @WebMethod(operationName = "modificarDatosCliente")
        void modificarDatosCliente(
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "apellido") String apellido,
                        @WebParam(name = "fechaNac") DTFecha fechaNac,
                        @WebParam(name = "nacionalidad") String nacionalidad,
                        @WebParam(name = "tipoDocumento") TipoDoc tipoDocumento,
                        @WebParam(name = "numeroDocumento") String numeroDocumento);

        @WebMethod(operationName = "modificarDatosAerolinea")
        void modificarDatosAerolinea(
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "descripcion") String descripcion,
                        @WebParam(name = "linkSitioWeb") String linkSitioWeb);

        @WebMethod(operationName = "altaCliente")
        void altaCliente(
                        @WebParam(name = "nickname") String nickname,
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "correo") String correo,
                        @WebParam(name = "apellido") String apellido,
                        @WebParam(name = "fechaNac") DTFecha fechaNac,
                        @WebParam(name = "nacionalidad") String nacionalidad,
                        @WebParam(name = "tipoDocumento") TipoDoc tipoDocumento,
                        @WebParam(name = "numeroDocumento") String numeroDocumento,
                        @WebParam(name = "foto") byte[] foto,
                        @WebParam(name = "contrasena") String contrasena);

        @WebMethod(operationName = "altaAerolinea")
        void altaAerolinea(
                        @WebParam(name = "nickname") String nickname,
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "correo") String correo,
                        @WebParam(name = "descripcion") String descripcion,
                        @WebParam(name = "linkSitioWeb") String linkSitioWeb,
                        @WebParam(name = "foto") byte[] foto,
                        @WebParam(name = "contrasena") String contrasena);

        @WebMethod(operationName = "ingresarDatosVuelo")
        @WebResult(name = "vuelo")
        DTVuelo ingresarDatosVuelo(
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "fecha") DTFecha fecha,
                        @WebParam(name = "horaVuelo") DTHora horaVuelo,
                        @WebParam(name = "duracion") DTHora duracion,
                        @WebParam(name = "maxTurista") int maxTurista,
                        @WebParam(name = "maxEjecutivo") int maxEjecutivo,
                        @WebParam(name = "fechaAlta") DTFecha fechaAlta,
                        @WebParam(name = "ruta") DTRutaVuelo ruta,
                        @WebParam(name = "foto") byte[] foto);

        @WebMethod(operationName = "darAltaVuelo")
        void darAltaVuelo();

        @WebMethod(operationName = "seleccionarVueloParaReserva")
        void seleccionarVueloParaReserva(@WebParam(name = "nombreVuelo") String nombreVuelo);

        @WebMethod(operationName = "nombresPasajes")
        @WebResult(name = "nombres")
        List<String> nombresPasajes(
                        @WebParam(name = "nombre") String nombre,
                        @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros);

        @WebMethod(operationName = "datosReserva")
        void datosReserva(
                        @WebParam(name = "tipoAsiento") TipoAsiento tipoAsiento,
                        @WebParam(name = "cantidadPasaje") int cantidadPasaje,
                        @WebParam(name = "equipajeExtra") int equipajeExtra,
                        @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros,
                        @WebParam(name = "fechaReserva") DTFecha fechaReserva);

        @WebMethod(operationName = "datosReservaConPaquete")
        void datosReservaConPaquete(
                        @WebParam(name = "tipoAsiento") TipoAsiento tipoAsiento,
                        @WebParam(name = "cantidadPasaje") int cantidadPasaje,
                        @WebParam(name = "equipajeExtra") int equipajeExtra,
                        @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros,
                        @WebParam(name = "fechaReserva") DTFecha fechaReserva,
                        @WebParam(name = "paqueteId") Long paqueteId);

        @WebMethod(operationName = "seleccionarPaquete")
        void seleccionarPaquete(@WebParam(name = "nombrePaquete") String nombrePaquete);

        @WebMethod(operationName = "consultaPaqueteVuelo")
        @WebResult(name = "paquete")
        DTPaqueteVuelos consultaPaqueteVuelo();

        @WebMethod(operationName = "consultaPaqueteVueloRutas")
        @WebResult(name = "rutas")
        List<DTRutaVuelo> consultaPaqueteVueloRutas();

        @WebMethod(operationName = "seleccionarCliente")
        void seleccionarCliente(@WebParam(name = "nombreCliente") String nombreCliente);

        @WebMethod(operationName = "clienteYaComproPaquete")
        @WebResult(name = "yaCompro")
        boolean clienteYaComproPaquete();

        @WebMethod(operationName = "realizarCompra")
        void realizarCompra(
                        @WebParam(name = "fechaCompra") DTFecha fechaCompra,
                        @WebParam(name = "costo") float costo,
                        @WebParam(name = "vencimiento") DTFecha vencimiento);

        @WebMethod(operationName = "obtenerPaquetesClienteParaRuta")
        @WebResult(name = "paquetes")
        List<DTPaqueteVuelos> obtenerPaquetesClienteParaRuta(
                        @WebParam(name = "nicknameCliente") String nicknameCliente,
                        @WebParam(name = "rutaNombre") String rutaNombre);

        @WebMethod(operationName = "listarReservasCheck")
        @WebResult(name = "reservas")
        List<DTReserva> listarReservasCheck(@WebParam(name = "nicknameCliente") String nicknameCliente);

        // Seguidores
        @WebMethod(operationName = "seguir")
        void seguir(
                        @WebParam(name = "nickSeguidor") String nickSeguidor,
                        @WebParam(name = "nickSeguido") String nickSeguido);

        @WebMethod(operationName = "dejarDeSeguir")
        void dejarDeSeguir(
                        @WebParam(name = "nickSeguidor") String nickSeguidor,
                        @WebParam(name = "nickSeguido") String nickSeguido);

        @WebMethod(operationName = "listarSeguidores")
        @WebResult(name = "seguidores")
        List<String> listarSeguidores(@WebParam(name = "nickSeguido") String nickSeguido);

        @WebMethod(operationName = "listarSeguidos")
        @WebResult(name = "seguidos")
        List<String> listarSeguidos(@WebParam(name = "nickSeguidor") String nickSeguidor);

        // Actualizar foto
        @WebMethod(operationName = "actualizarFotoCliente")
        void actualizarFotoCliente(
                        @WebParam(name = "nickname") String nickname,
                        @WebParam(name = "foto") byte[] foto);

        @WebMethod(operationName = "actualizarFotoAerolinea")
        void actualizarFotoAerolinea(
                        @WebParam(name = "nickname") String nickname,
                        @WebParam(name = "foto") byte[] foto);

        @WebMethod(operationName = "obtenerPasajerosReserva")
        @WebResult(name = "pasajeros")
        List<DTPasajero> obtenerPasajerosReserva(
                        @WebParam(name = "reservaId") Long reservaId);
}
