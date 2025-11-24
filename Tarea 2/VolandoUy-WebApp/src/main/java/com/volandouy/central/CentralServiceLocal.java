package com.volandouy.central;

import java.util.List;

import logica.DataTypes.*;

/**
 * Implementacion LOCAL, usa directamente Sistema.
 * NOTA: Esta clase ya no se usa en producción ya que ServiceFactory
 * está configurado para usar únicamente Web Services.
 * Se mantiene para evitar errores de compilación, pero está deshabilitada
 * porque Sistema no está disponible en el cliente.
 */
public class CentralServiceLocal implements CentralService {

    // Sistema no está disponible en el cliente (Tarea 2)
    // Esta implementación está deshabilitada
    // private final Sistema sistema = Sistema.getInstance();

    // Aerolineas
    @Override
    public List<DTAerolinea> listarAerolineas() {
        throw new UnsupportedOperationException("CentralServiceLocal no está disponible. Use CentralServiceWS.");
    }

    // Ciudades
    @Override
    public List<DTCiudad> listarCiudades() {
        throwUnsupported();
        return null;
    }

    @Override
    public List<String> listarAeropuertos() {
        throwUnsupported();
        return null;
    }

    // Rutas
    @Override
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea) {
        throwUnsupported();
        return null;
    }

    @Override
    public List<DTRutaVuelo> listarRutasDeVuelo() {
        throwUnsupported();
        return null;
    }

    @Override
    public DTRutaVuelo seleccionarRutaVueloRet(String nombreRutaVuelo) {
        throwUnsupported();
        return null;
    }

    @Override
    public void seleccionarAerolinea(String nickname) {
        throwUnsupported();
    }

    @Override
    public void ingresarDatosRuta(String nombreRuta, String descripcion, float costoTurista,
            float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen,
            String ciudadDestino, DTFecha fechaAlta, List<String> categorias,
            byte[] foto, String videoUrl) {
        throwUnsupported();
    }

    @Override
    public void registrarRuta() {
        throwUnsupported();
    }

    @Override
    public void EstadoFinalizarRutaVuelo(String nombreRuta) {
        throwUnsupported();
    }

    @Override
    public void incrementarVisitasRuta(String nombreRuta) {
        throw new UnsupportedOperationException("incrementarVisitasRuta no soportado en Local");
    }

    // Vuelos
    @Override
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        throwUnsupported();
        return null;
    }

    @Override
    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo,
            DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta,
            DTRutaVuelo ruta, byte[] foto) {
        throwUnsupported();
        return null;
    }

    @Override
    public void darAltaVuelo() {
        throwUnsupported();
    }

    // Usuarios
    @Override
    public List<DTCliente> listarClientes() {
        throwUnsupported();
        return null;
    }

    @Override
    public List<DTUsuario> consultarUsuarios() {
        throwUnsupported();
        return null;
    }

    @Override
    public DTUsuario mostrarDatosUsuario(String nickname) {
        throwUnsupported();
        return null;
    }

    @Override
    public DTUsuario mostrarDatosUsuarioMod(String nickname) {
        throwUnsupported();
        return null;
    }

    @Override
    public void seleccionarUsuarioAMod(String nickname) {
        throwUnsupported();
    }

    @Override
    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac,
            String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        throwUnsupported();
    }

    @Override
    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb) {
        throwUnsupported();
    }

    @Override
    public void altaCliente(String nickname, String nombre, String correo, String apellido,
            DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento,
            String numeroDocumento, byte[] foto, String contrasena) {
        throwUnsupported();
    }

    @Override
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion,
            String linkSitioWeb, byte[] foto, String contrasena) {
        throwUnsupported();
    }

    // Reservas
    @Override
    public List<DTVueloReserva> listarReservasVuelo(String nombreVuelo) {
        throwUnsupported();
        return null;
    }

    @Override
    public void seleccionarVueloParaReserva(String nombreVuelo) {
        throwUnsupported();
    }

    @Override
    public List<String> nombresPasajes(String nombre, List<String> nombresPasajeros) {
        throwUnsupported();
        return null;
    }

    @Override
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra,
            List<String> nombresPasajeros, DTFecha fechaReserva) {
        throwUnsupported();
    }

    @Override
    public void datosReservaConPaquete(TipoAsiento tipoAsiento, int cantidadPasaje,
            int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva,
            Long paqueteId) {
        throwUnsupported();
    }

    @Override
    public List<DTReserva> listarDTReservasCheck(String nicknameCliente) {
        throwUnsupported();
        return null;
    }

    @Override
    public List<DTReserva> listarDTReservasNoCheck(String nicknameCliente) {
        throwUnsupported();
        return null;
    }


    @Override
    public void realizarCheckIn(Long reservaId) {
        throwUnsupported();
    }

    // Paquetes
    @Override
    public List<DTPaqueteVuelos> mostrarPaquetes() {
        throwUnsupported();
        return null;
    }

    @Override
    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados() {
        throwUnsupported();
        return null;
    }

    @Override
    public void seleccionarPaquete(String nombrePaquete) {
        throwUnsupported();
    }

    @Override
    public DTPaqueteVuelos consultaPaqueteVuelo() {
        throwUnsupported();
        return null;
    }

    @Override
    public List<DTRutaVuelo> consultaPaqueteVueloRutas() {
        throwUnsupported();
        return null;
    }

    @Override
    public void seleccionarCliente(String nombreCliente) {
        throwUnsupported();
    }

    @Override
    public boolean clienteYaComproPaquete() {
        throwUnsupported();
        return false;
    }

    @Override
    public void realizarCompra(DTFecha fechaCompra, float costo, DTFecha vencimiento) {
        throwUnsupported();
    }

    @Override
    public List<DTPaqueteVuelos> obtenerPaquetesClienteParaRuta(String nicknameCliente, String rutaNombre) {
        throwUnsupported();
        return null;
    }

    // Utilidades
    @Override
    public List<DTCategoria> getCategorias() {
        throwUnsupported();
        return null;
    }

    @Override
    public void precargarSistemaCompleto() {
        throwUnsupported();
    }

    @Override
    public String ping() {
        throw new UnsupportedOperationException("CentralServiceLocal no está disponible. Use CentralServiceWS.");
    }

    // ===========================
    // SEGUIDORES
    // ===========================

    @Override
    public void seguir(String nickSeguidor, String nickSeguido) {
        throwUnsupported();
    }

    @Override
    public void dejarDeSeguir(String nickSeguidor, String nickSeguido) {
        throwUnsupported();
    }

    @Override
    public List<String> listarSeguidores(String nickSeguido) {
        return throwUnsupported();
    }

    @Override
    public List<String> listarSeguidos(String nickSeguidor) {
        return throwUnsupported();
    }

    // ===========================
    // ACTUALIZAR FOTO
    // ===========================

    @Override
    public void actualizarFotoCliente(String nickname, byte[] foto) {
        throwUnsupported();
    }

    @Override
    public void actualizarFotoAerolinea(String nickname, byte[] foto) {
        throwUnsupported();
    }

    @Override
    public List<DTPasajero> obtenerPasajerosReserva(Long reservaId) {
        throwUnsupported();
        return null;
    }

    // Todos los demás métodos también lanzan UnsupportedOperationException
    // Se mantienen para cumplir con la interfaz pero no se usan
    private <T> T throwUnsupported() {
        throw new UnsupportedOperationException("CentralServiceLocal no está disponible. Use CentralServiceWS.");
    }
}
