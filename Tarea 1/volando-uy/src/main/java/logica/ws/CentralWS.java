package logica.ws;

import dato.entidades.Reserva;
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

    // ===========================
    //      MÉTODOS ADICIONALES
    // ===========================

    @WebMethod(operationName = "mostrarDatosUsuarioMod")
    @WebResult(name = "usuario")
    public DTUsuario mostrarDatosUsuarioMod(
            @WebParam(name = "nickname") String nickname) {
        return sistema.mostrarDatosUsuarioMod(nickname);
    }

    @WebMethod(operationName = "seleccionarRutaVueloRet")
    @WebResult(name = "rutaVuelo")
    public DTRutaVuelo seleccionarRutaVueloRet(
            @WebParam(name = "nombreRutaVuelo") String nombreRutaVuelo) {
        return sistema.seleccionarRutaVueloRet(nombreRutaVuelo);
    }

    @WebMethod(operationName = "getCategorias")
    @WebResult(name = "categorias")
    public List<DTCategoria> getCategorias() {
        return sistema.getDTCategorias();
    }

    @WebMethod(operationName = "precargarSistemaCompleto")
    public void precargarSistemaCompleto() {
        sistema.precargarSistemaCompleto();
    }

    @WebMethod(operationName = "seleccionarAerolinea")
    public void seleccionarAerolinea(
            @WebParam(name = "nickname") String nickname) {
        sistema.seleccionarAerolinea(nickname);
    }

    @WebMethod(operationName = "ingresarDatosRuta")
    public void ingresarDatosRuta(
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
            @WebParam(name = "videoUrl") String videoUrl) {
        sistema.ingresarDatosRuta(nombreRuta, descripcion, costoTurista, costoEjecutivo, 
                costoEquipajeExtra, ciudadOrigen, ciudadDestino, fechaAlta, categorias, foto, videoUrl);
    }

    @WebMethod(operationName = "registrarRuta")
    public void registrarRuta() {
        sistema.registrarRuta();
    }

    @WebMethod(operationName = "EstadoFinalizarRutaVuelo")
    public void EstadoFinalizarRutaVuelo(
            @WebParam(name = "nombreRuta") String nombreRuta) {
        sistema.EstadoFinalizarRutaVuelo(nombreRuta);
    }

    @WebMethod(operationName = "seleccionarUsuarioAMod")
    public void seleccionarUsuarioAMod(
            @WebParam(name = "nickname") String nickname) {
        sistema.seleccionarUsuarioAMod(nickname);
    }

    @WebMethod(operationName = "modificarDatosCliente")
    public void modificarDatosCliente(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "fechaNac") DTFecha fechaNac,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "tipoDocumento") logica.DataTypes.TipoDoc tipoDocumento,
            @WebParam(name = "numeroDocumento") String numeroDocumento) {
        sistema.modificarDatosCliente(nombre, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento);
    }

    @WebMethod(operationName = "modificarDatosAerolinea")
    public void modificarDatosAerolinea(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "linkSitioWeb") String linkSitioWeb) {
        sistema.modificarDatosAerolinea(nombre, descripcion, linkSitioWeb);
    }

    @WebMethod(operationName = "altaCliente")
    public void altaCliente(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "apellido") String apellido,
            @WebParam(name = "fechaNac") DTFecha fechaNac,
            @WebParam(name = "nacionalidad") String nacionalidad,
            @WebParam(name = "tipoDocumento") logica.DataTypes.TipoDoc tipoDocumento,
            @WebParam(name = "numeroDocumento") String numeroDocumento,
            @WebParam(name = "foto") byte[] foto,
            @WebParam(name = "contrasena") String contrasena) {
        sistema.altaCliente(nickname, nombre, correo, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento, foto, contrasena);
    }

    @WebMethod(operationName = "altaAerolinea")
    public void altaAerolinea(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "linkSitioWeb") String linkSitioWeb,
            @WebParam(name = "foto") byte[] foto,
            @WebParam(name = "contrasena") String contrasena) {
        sistema.altaAerolinea(nickname, nombre, correo, descripcion, linkSitioWeb, foto, contrasena);
    }

    @WebMethod(operationName = "ingresarDatosVuelo")
    @WebResult(name = "vuelo")
    public DTVuelo ingresarDatosVuelo(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "fecha") DTFecha fecha,
            @WebParam(name = "horaVuelo") DTHora horaVuelo,
            @WebParam(name = "duracion") DTHora duracion,
            @WebParam(name = "maxTurista") int maxTurista,
            @WebParam(name = "maxEjecutivo") int maxEjecutivo,
            @WebParam(name = "fechaAlta") DTFecha fechaAlta,
            @WebParam(name = "ruta") DTRutaVuelo ruta,
            @WebParam(name = "foto") byte[] foto) {
        return sistema.ingresarDatosVuelo(nombre, fecha, horaVuelo, duracion, maxTurista, maxEjecutivo, fechaAlta, ruta, foto);
    }

    @WebMethod(operationName = "darAltaVuelo")
    public void darAltaVuelo() {
        sistema.darAltaVuelo();
    }

    @WebMethod(operationName = "seleccionarVueloParaReserva")
    public void seleccionarVueloParaReserva(
            @WebParam(name = "nombreVuelo") String nombreVuelo) {
        sistema.seleccionarVueloParaReserva(nombreVuelo);
    }

    @WebMethod(operationName = "nombresPasajes")
    @WebResult(name = "nombres")
    public List<String> nombresPasajes(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros) {
        return sistema.nombresPasajes(nombre, nombresPasajeros);
    }

    @WebMethod(operationName = "datosReserva")
    public void datosReserva(
            @WebParam(name = "tipoAsiento") logica.DataTypes.TipoAsiento tipoAsiento,
            @WebParam(name = "cantidadPasaje") int cantidadPasaje,
            @WebParam(name = "equipajeExtra") int equipajeExtra,
            @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros,
            @WebParam(name = "fechaReserva") DTFecha fechaReserva) {
        sistema.datosReserva(tipoAsiento, cantidadPasaje, equipajeExtra, nombresPasajeros, fechaReserva);
    }

    @WebMethod(operationName = "datosReservaConPaquete")
    public void datosReservaConPaquete(
            @WebParam(name = "tipoAsiento") logica.DataTypes.TipoAsiento tipoAsiento,
            @WebParam(name = "cantidadPasaje") int cantidadPasaje,
            @WebParam(name = "equipajeExtra") int equipajeExtra,
            @WebParam(name = "nombresPasajeros") List<String> nombresPasajeros,
            @WebParam(name = "fechaReserva") DTFecha fechaReserva,
            @WebParam(name = "paqueteId") Long paqueteId) {
        sistema.datosReservaConPaquete(tipoAsiento, cantidadPasaje, equipajeExtra, nombresPasajeros, fechaReserva, paqueteId);
    }

    @WebMethod(operationName = "seleccionarPaquete")
    public void seleccionarPaquete(
            @WebParam(name = "nombrePaquete") String nombrePaquete) {
        sistema.seleccionarPaquete(nombrePaquete);
    }

    @WebMethod(operationName = "consultaPaqueteVuelo")
    @WebResult(name = "paquete")
    public DTPaqueteVuelos consultaPaqueteVuelo() {
        return sistema.consultaPaqueteVuelo();
    }

    @WebMethod(operationName = "consultaPaqueteVueloRutas")
    @WebResult(name = "rutas")
    public List<DTRutaVuelo> consultaPaqueteVueloRutas() {
        return sistema.consultaPaqueteVueloRutas();
    }

    @WebMethod(operationName = "seleccionarCliente")
    public void seleccionarCliente(
            @WebParam(name = "nombreCliente") String nombreCliente) {
        sistema.seleccionarCliente(nombreCliente);
    }

    @WebMethod(operationName = "clienteYaComproPaquete")
    @WebResult(name = "yaCompro")
    public boolean clienteYaComproPaquete() {
        return sistema.clienteYaComproPaquete();
    }

    @WebMethod(operationName = "realizarCompra")
    public void realizarCompra(
            @WebParam(name = "fechaCompra") DTFecha fechaCompra,
            @WebParam(name = "costo") float costo,
            @WebParam(name = "vencimiento") DTFecha vencimiento) {
        sistema.realizarCompra(fechaCompra, costo, vencimiento);
    }

    @WebMethod(operationName = "obtenerPaquetesClienteParaRuta")
    @WebResult(name = "paquetes")
    public List<DTPaqueteVuelos> obtenerPaquetesClienteParaRuta(
            @WebParam(name = "nicknameCliente") String nicknameCliente,
            @WebParam(name = "rutaNombre") String rutaNombre) {
        return sistema.obtenerPaquetesClienteParaRuta(nicknameCliente, rutaNombre);
    }

    @WebMethod(operationName = "listarDTReservasCheck")
    @WebResult(name = "reservas")
    public List<DTReserva> listarDTReservasCheck(
            @WebParam(name = "nicknameCliente") String nicknameCliente) {
        return sistema.listarDTReservasCheck(nicknameCliente);
    }

    @WebMethod(operationName = "listarDTReservasNoCheck")
    @WebResult(name = "reservas")
    public List<DTReserva> listarDTReservasNoCheck(
            @WebParam(name = "nicknameCliente") String nicknameCliente) {
        return sistema.listarDTReservasNoCheck(nicknameCliente);
    }


    @WebMethod(operationName = "realizarCheckIn")
    @WebResult(name = "reservas")
    public void realizarCheckIn(
            @WebParam(name = "reservaId") Long reservaId) {
        sistema.realizarCheckIn(reservaId);
    }


    // ===========================
    //      SEGUIDORES
    // ===========================

    @WebMethod(operationName = "seguir")
    public void seguir(
            @WebParam(name = "nickSeguidor") String nickSeguidor,
            @WebParam(name = "nickSeguido") String nickSeguido) {
        logica.servicios.SeguidoresServicio servicio = new logica.servicios.SeguidoresServicio();
        servicio.seguir(nickSeguidor, nickSeguido);
    }

    @WebMethod(operationName = "dejarDeSeguir")
    public void dejarDeSeguir(
            @WebParam(name = "nickSeguidor") String nickSeguidor,
            @WebParam(name = "nickSeguido") String nickSeguido) {
        logica.servicios.SeguidoresServicio servicio = new logica.servicios.SeguidoresServicio();
        servicio.dejarDeSeguir(nickSeguidor, nickSeguido);
    }

    @WebMethod(operationName = "listarSeguidores")
    @WebResult(name = "seguidores")
    public List<String> listarSeguidores(
            @WebParam(name = "nickSeguido") String nickSeguido) {
        logica.servicios.SeguidoresServicio servicio = new logica.servicios.SeguidoresServicio();
        return servicio.listarSeguidores(nickSeguido);
    }

    @WebMethod(operationName = "listarSeguidos")
    @WebResult(name = "seguidos")
    public List<String> listarSeguidos(
            @WebParam(name = "nickSeguidor") String nickSeguidor) {
        logica.servicios.SeguidoresServicio servicio = new logica.servicios.SeguidoresServicio();
        return servicio.listarSeguidos(nickSeguidor);
    }

    // ===========================
    //      ACTUALIZAR FOTO
    // ===========================

    @WebMethod(operationName = "actualizarFotoCliente")
    public void actualizarFotoCliente(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "foto") byte[] foto) {
        logica.servicios.ClienteServicio servicio = new logica.servicios.ClienteServicio();
        dato.entidades.Cliente cliente = servicio.buscarClientePorNickname(nickname);
        if (cliente != null) {
            cliente.setFoto(foto);
            servicio.actualizarCliente(cliente);
        } else {
            throw new IllegalArgumentException("Cliente no encontrado: " + nickname);
        }
    }

    @WebMethod(operationName = "actualizarFotoAerolinea")
    public void actualizarFotoAerolinea(
            @WebParam(name = "nickname") String nickname,
            @WebParam(name = "foto") byte[] foto) {
        logica.servicios.AerolineaServicio servicio = new logica.servicios.AerolineaServicio();
        dato.entidades.Aerolinea aerolinea = servicio.buscarAerolineaPorNickname(nickname);
        if (aerolinea != null) {
            aerolinea.setFoto(foto);
            servicio.actualizarAerolinea(aerolinea);
        } else {
            throw new IllegalArgumentException("Aerolínea no encontrada: " + nickname);
        }
    }

    @WebMethod(operationName = "obtenerPasajerosReserva")
    @WebResult(name = "pasajeros")
    public List<logica.DataTypes.DTPasajero> obtenerPasajerosReserva(
            @WebParam(name = "reservaId") Long reservaId) {
        return sistema.obtenerPasajerosReserva(reservaId);
    }
}
