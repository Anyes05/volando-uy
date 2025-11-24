package com.volandouy.central;

import java.util.List;

import logica.DataTypes.*;

/**
 * Interfaz que define los servicios que el Servidor Web necesita del Servidor
 * Central.
 *
 * La implementación se realiza mediante Web Services.
 */
public interface CentralService {

    // Aerolineas
    List<DTAerolinea> listarAerolineas();

    // Ciudades
    List<DTCiudad> listarCiudades();

    List<String> listarAeropuertos();

    // Rutas
    List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea);

    List<DTRutaVuelo> listarRutasDeVuelo();

    DTRutaVuelo seleccionarRutaVueloRet(String nombreRutaVuelo);

    // Reservas
    List<DTVueloReserva> listarReservasVuelo(String nombreVuelo);

    void seleccionarVueloParaReserva(String nombreVuelo);

    List<String> nombresPasajes(String nombre, List<String> nombresPasajeros);

    void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra,
            List<String> nombresPasajeros, DTFecha fechaReserva);

    void datosReservaConPaquete(TipoAsiento tipoAsiento, int cantidadPasaje,
            int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva,
            Long paqueteId);

    List<DTReserva> listarDTReservasCheck(String nicknameCliente);

    List<DTReserva> listarDTReservasNoCheck(String nicknameCliente);

    void realizarCheckIn(Long reservaId);

    void seleccionarAerolinea(String nickname);

    void ingresarDatosRuta(String nombreRuta, String descripcion, float costoTurista,
            float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen,
            String ciudadDestino, DTFecha fechaAlta, List<String> categorias,
            byte[] foto, String videoUrl);

    void registrarRuta();

    void EstadoFinalizarRutaVuelo(String nombreRuta);

    void incrementarVisitasRuta(String nombreRuta);

    // Vuelos
    List<DTVuelo> listarVuelosDeRuta(String nombreRuta);

    DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo,
            DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta,
            DTRutaVuelo ruta, byte[] foto);

    void darAltaVuelo();

    // Usuarios
    List<DTCliente> listarClientes();

    List<DTUsuario> consultarUsuarios();

    DTUsuario mostrarDatosUsuario(String nickname);

    DTUsuario mostrarDatosUsuarioMod(String nickname);

    void seleccionarUsuarioAMod(String nickname);

    void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac,
            String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento);

    void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb);

    void altaCliente(String nickname, String nombre, String correo, String apellido,
            DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento,
            String numeroDocumento, byte[] foto, String contrasena);

    void altaAerolinea(String nickname, String nombre, String correo, String descripcion,
            String linkSitioWeb, byte[] foto, String contrasena);

    // Reservas
    List<DTVueloReserva> listarReservasVuelo(String nombreVuelo);

    void seleccionarVueloParaReserva(String nombreVuelo);

    List<String> nombresPasajes(String nombre, List<String> nombresPasajeros);

    void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra,
            List<String> nombresPasajeros, DTFecha fechaReserva);

    void datosReservaConPaquete(TipoAsiento tipoAsiento, int cantidadPasaje,
            int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva,
            Long paqueteId);

    List<DTReserva> listarReservasCheck(String nicknameCliente);

    // Paquetes
    List<DTPaqueteVuelos> mostrarPaquetes();

    List<DTPaqueteVuelos> obtenerPaquetesNoComprados();

    void seleccionarPaquete(String nombrePaquete);

    DTPaqueteVuelos consultaPaqueteVuelo();

    List<DTRutaVuelo> consultaPaqueteVueloRutas();

    void seleccionarCliente(String nombreCliente);

    boolean clienteYaComproPaquete();

    void realizarCompra(DTFecha fechaCompra, float costo, DTFecha vencimiento);

    List<DTPaqueteVuelos> obtenerPaquetesClienteParaRuta(String nicknameCliente, String rutaNombre);

    // Seguidores
    void seguir(String nickSeguidor, String nickSeguido);

    void dejarDeSeguir(String nickSeguidor, String nickSeguido);

    List<String> listarSeguidores(String nickSeguido);

    List<String> listarSeguidos(String nickSeguidor);

    // Actualizar foto
    void actualizarFotoCliente(String nickname, byte[] foto);

    void actualizarFotoAerolinea(String nickname, byte[] foto);

    // Pasajeros
    List<DTPasajero> obtenerPasajerosReserva(Long reservaId);

    // Utilidades
    List<DTCategoria> getCategorias();

    void precargarSistemaCompleto();

    String ping();
}
