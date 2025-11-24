package com.volandouy.central;

import jakarta.xml.ws.Service;
import jakarta.xml.ws.BindingProvider;
import logica.DataTypes.*;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Implementación que consume el WebService SOAP CentralWS.
 * 
 * Esta clase se conecta al Servidor Central usando Web Services SOAP.
 * La URL se configura en config.properties (servidor.central.url).
 */
public class CentralServiceWS implements CentralService {

    private final CentralWSPort port; // Usamos la interfaz CentralWSPort

    /**
     * Constructor que inicializa la conexión al Web Service.
     * 
     * @param wsdlUrl URL completa al WSDL, por ejemplo:
     *                "http://192.168.1.100:8082/centralws?wsdl"
     */
    public CentralServiceWS(String wsdlUrl) {
        try {
            URL url = new URL(wsdlUrl);

            // targetNamespace debe coincidir con el definido en CentralWS
            QName serviceName = new QName("http://ws.logica/", "CentralWSService");

            Service service = Service.create(url, serviceName);

            // Obtener el port usando la interfaz CentralWSPort
            // JAX-WS puede crear un proxy dinámico desde una interfaz
            this.port = service.getPort(CentralWSPort.class);

            // Configurar timeout si es necesario
            if (port instanceof BindingProvider) {
                BindingProvider bp = (BindingProvider) port;
                Map<String, Object> requestContext = bp.getRequestContext();

                // Timeout de conexión (milisegundos)
                int timeout = ConfiguracionClienteWS.getTimeout();
                requestContext.put("com.sun.xml.ws.connect.timeout", timeout);
                requestContext.put("com.sun.xml.ws.request.timeout", timeout);
            }

        } catch (Exception e) {
            String mensajeError = "Error inicializando CentralServiceWS con URL: " + wsdlUrl;
            if (e.getMessage() != null) {
                if (e.getMessage().contains("Connection refused") || e.getMessage().contains("connect")) {
                    mensajeError += "\nEl Servidor Central no está disponible. " +
                            "Asegúrate de ejecutar PublicadorWS.java desde la Tarea 1.";
                } else {
                    mensajeError += "\nError: " + e.getMessage();
                }
            }
            System.err.println(">>> ✗ " + mensajeError);
            throw new RuntimeException(mensajeError, e);
        }
    }

    // ===========================
    // AEROLÍNEAS
    // ===========================

    @Override
    public List<DTAerolinea> listarAerolineas() {
        try {
            return port.listarAerolineas();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar aerolíneas: " + e.getMessage(), e);
        }
    }

    // ===========================
    // CIUDADES
    // ===========================

    @Override
    public List<DTCiudad> listarCiudades() {
        try {
            return port.listarCiudades();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar ciudades: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> listarAeropuertos() {
        try {
            return port.listarAeropuertos();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar aeropuertos: " + e.getMessage(), e);
        }
    }

    // ===========================
    // RUTAS
    // ===========================

    @Override
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea) {
        try {
            return port.listarRutasPorAerolinea(nicknameAerolinea);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar rutas por aerolínea: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTRutaVuelo> listarRutasDeVuelo() {
        try {
            return port.listarRutasDeVuelo();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar rutas de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public DTRutaVuelo seleccionarRutaVueloRet(String nombreRutaVuelo) {
        try {
            return port.seleccionarRutaVueloRet(nombreRutaVuelo);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar ruta de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public void seleccionarAerolinea(String nickname) {
        try {
            port.seleccionarAerolinea(nickname);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar aerolínea: " + e.getMessage(), e);
        }
    }

    @Override
    public void ingresarDatosRuta(String nombreRuta, String descripcion, float costoTurista,
            float costoEjecutivo, float costoEquipajeExtra, String ciudadOrigen,
            String ciudadDestino, DTFecha fechaAlta, List<String> categorias,
            byte[] foto, String videoUrl) {
        try {
            port.ingresarDatosRuta(nombreRuta, descripcion, costoTurista, costoEjecutivo,
                    costoEquipajeExtra, ciudadOrigen, ciudadDestino, fechaAlta, categorias, foto, videoUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar datos de ruta: " + e.getMessage(), e);
        }
    }

    @Override
    public void registrarRuta() {
        try {
            port.registrarRuta();
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar ruta: " + e.getMessage(), e);
        }
    }

    @Override
    public void EstadoFinalizarRutaVuelo(String nombreRuta) {
        try {
            port.EstadoFinalizarRutaVuelo(nombreRuta);
        } catch (Exception e) {
            throw new RuntimeException("Error al finalizar ruta de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public void incrementarVisitasRuta(String nombreRuta) {
        try {
            port.incrementarVisitasRuta(nombreRuta);
        } catch (Exception e) {
            throw new RuntimeException("Error al incrementar visitas de ruta: " + e.getMessage(), e);
        }
    }

    // ===========================
    // VUELOS
    // ===========================

    @Override
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        try {
            return port.listarVuelosDeRuta(nombreRuta);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar vuelos de ruta: " + e.getMessage(), e);
        }
    }

    @Override
    public DTVuelo ingresarDatosVuelo(String nombre, DTFecha fecha, DTHora horaVuelo,
            DTHora duracion, int maxTurista, int maxEjecutivo, DTFecha fechaAlta,
            DTRutaVuelo ruta, byte[] foto) {
        try {
            return port.ingresarDatosVuelo(nombre, fecha, horaVuelo, duracion, maxTurista,
                    maxEjecutivo, fechaAlta, ruta, foto);
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar datos de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public void darAltaVuelo() {
        try {
            port.darAltaVuelo();
        } catch (Exception e) {
            throw new RuntimeException("Error al dar alta vuelo: " + e.getMessage(), e);
        }
    }

    // ===========================
    // CLIENTES
    // ===========================

    @Override
    public List<DTCliente> listarClientes() {
        try {
            return port.listarClientes();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
    }

    @Override
    public DTUsuario mostrarDatosUsuario(String nickname) {
        try {
            return port.mostrarDatosUsuario(nickname);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos de usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public DTUsuario mostrarDatosUsuarioMod(String nickname) {
        try {
            return port.mostrarDatosUsuarioMod(nickname);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos de usuario mod: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTUsuario> consultarUsuarios() {
        try {
            return port.consultarUsuarios();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar usuarios: " + e.getMessage(), e);
        }
    }

    @Override
    public void seleccionarUsuarioAMod(String nickname) {
        try {
            port.seleccionarUsuarioAMod(nickname);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar usuario a modificar: " + e.getMessage(), e);
        }
    }

    @Override
    public void modificarDatosCliente(String nombre, String apellido, DTFecha fechaNac,
            String nacionalidad, TipoDoc tipoDocumento, String numeroDocumento) {
        try {
            port.modificarDatosCliente(nombre, apellido, fechaNac, nacionalidad, tipoDocumento, numeroDocumento);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar datos de cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void modificarDatosAerolinea(String nombre, String descripcion, String linkSitioWeb) {
        try {
            port.modificarDatosAerolinea(nombre, descripcion, linkSitioWeb);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar datos de aerolínea: " + e.getMessage(), e);
        }
    }

    @Override
    public void altaCliente(String nickname, String nombre, String correo, String apellido,
            DTFecha fechaNac, String nacionalidad, TipoDoc tipoDocumento,
            String numeroDocumento, byte[] foto, String contrasena) {
        try {
            port.altaCliente(nickname, nombre, correo, apellido, fechaNac, nacionalidad,
                    tipoDocumento, numeroDocumento, foto, contrasena);
        } catch (Exception e) {
            throw new RuntimeException("Error al dar alta cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void altaAerolinea(String nickname, String nombre, String correo, String descripcion,
            String linkSitioWeb, byte[] foto, String contrasena) {
        try {
            port.altaAerolinea(nickname, nombre, correo, descripcion, linkSitioWeb, foto, contrasena);
        } catch (Exception e) {
            throw new RuntimeException("Error al dar alta aerolínea: " + e.getMessage(), e);
        }
    }

    // ===========================
    // RESERVAS
    // ===========================

    @Override
    public List<DTVueloReserva> listarReservasVuelo(String nombreVuelo) {
        try {
            return port.listarReservasVuelo(nombreVuelo);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar reservas de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public void seleccionarVueloParaReserva(String nombreVuelo) {
        try {
            port.seleccionarVueloParaReserva(nombreVuelo);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar vuelo para reserva: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> nombresPasajes(String nombre, List<String> nombresPasajeros) {
        try {
            return port.nombresPasajes(nombre, nombresPasajeros);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar nombres de pasajes: " + e.getMessage(), e);
        }
    }

    @Override
    public void datosReserva(TipoAsiento tipoAsiento, int cantidadPasaje, int equipajeExtra,
            List<String> nombresPasajeros, DTFecha fechaReserva) {
        try {
            port.datosReserva(tipoAsiento, cantidadPasaje, equipajeExtra, nombresPasajeros, fechaReserva);
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar datos de reserva: " + e.getMessage(), e);
        }
    }

    @Override
    public void datosReservaConPaquete(TipoAsiento tipoAsiento, int cantidadPasaje,
            int equipajeExtra, List<String> nombresPasajeros, DTFecha fechaReserva,
            Long paqueteId) {
        try {
            port.datosReservaConPaquete(tipoAsiento, cantidadPasaje, equipajeExtra, nombresPasajeros, fechaReserva,
                    paqueteId);
        } catch (Exception e) {
            throw new RuntimeException("Error al ingresar datos de reserva con paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTReserva> listarReservasCheck(String nicknameCliente) {
        try {
            return port.listarDTReservasCheck(nicknameCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar reservas con check-in: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTReserva> listarDTReservasCheck(String nicknameCliente) {
        try {
            return port.listarDTReservasCheck(nicknameCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar reservas con check-in: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTReserva> listarDTReservasNoCheck(String nicknameCliente) {
        try {
            return port.listarDTReservasNoCheck(nicknameCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar reservas con check-in: " + e.getMessage(), e);
        }
    }

    @Override
    public void realizarCheckIn(Long reservaId) {
        try {
            port.realizarCheckIn(reservaId);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar Check In: " + e.getMessage(), e);
        }
    }

    // ===========================
    // PAQUETES
    // ===========================

    @Override
    public List<DTPaqueteVuelos> mostrarPaquetes() {
        try {
            return port.mostrarPaquetes();
        } catch (Exception e) {
            throw new RuntimeException("Error al mostrar paquetes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados() {
        try {
            return port.obtenerPaquetesNoComprados();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener paquetes no comprados: " + e.getMessage(), e);
        }
    }

    @Override
    public void seleccionarPaquete(String nombrePaquete) {
        try {
            port.seleccionarPaquete(nombrePaquete);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public DTPaqueteVuelos consultaPaqueteVuelo() {
        try {
            return port.consultaPaqueteVuelo();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar paquete de vuelo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTRutaVuelo> consultaPaqueteVueloRutas() {
        try {
            return port.consultaPaqueteVueloRutas();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar rutas de paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public void seleccionarCliente(String nombreCliente) {
        try {
            port.seleccionarCliente(nombreCliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al seleccionar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean clienteYaComproPaquete() {
        try {
            return port.clienteYaComproPaquete();
        } catch (Exception e) {
            throw new RuntimeException("Error al verificar compra de paquete: " + e.getMessage(), e);
        }
    }

    @Override
    public void realizarCompra(DTFecha fechaCompra, float costo, DTFecha vencimiento) {
        try {
            port.realizarCompra(fechaCompra, costo, vencimiento);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar compra: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTPaqueteVuelos> obtenerPaquetesClienteParaRuta(String nicknameCliente, String rutaNombre) {
        try {
            return port.obtenerPaquetesClienteParaRuta(nicknameCliente, rutaNombre);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener paquetes de cliente para ruta: " + e.getMessage(), e);
        }
    }

    // ===========================
    // UTILIDADES
    // ===========================

    @Override
    public List<DTCategoria> getCategorias() {
        try {
            return port.getCategorias();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener categorías: " + e.getMessage(), e);
        }
    }

    @Override
    public void precargarSistemaCompleto() {
        try {
            port.precargarSistemaCompleto();
        } catch (Exception e) {
            throw new RuntimeException("Error al precargar sistema completo: " + e.getMessage(), e);
        }
    }

    @Override
    public String ping() {
        try {
            return port.ping();
        } catch (Exception e) {
            throw new RuntimeException("Error en ping: " + e.getMessage(), e);
        }
    }

    // ===========================
    // SEGUIDORES
    // ===========================

    @Override
    public void seguir(String nickSeguidor, String nickSeguido) {
        try {
            port.seguir(nickSeguidor, nickSeguido);
        } catch (Exception e) {
            throw new RuntimeException("Error al seguir usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void dejarDeSeguir(String nickSeguidor, String nickSeguido) {
        try {
            port.dejarDeSeguir(nickSeguidor, nickSeguido);
        } catch (Exception e) {
            throw new RuntimeException("Error al dejar de seguir usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> listarSeguidores(String nickSeguido) {
        try {
            return port.listarSeguidores(nickSeguido);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar seguidores: " + e.getMessage(), e);
        }
    }

    @Override
    public List<String> listarSeguidos(String nickSeguidor) {
        try {
            return port.listarSeguidos(nickSeguidor);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar seguidos: " + e.getMessage(), e);
        }
    }

    // ===========================
    // ACTUALIZAR FOTO
    // ===========================

    @Override
    public void actualizarFotoCliente(String nickname, byte[] foto) {
        try {
            port.actualizarFotoCliente(nickname, foto);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar foto de cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarFotoAerolinea(String nickname, byte[] foto) {
        try {
            port.actualizarFotoAerolinea(nickname, foto);
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar foto de aerolínea: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTPasajero> obtenerPasajerosReserva(Long reservaId) {
        try {
            return port.obtenerPasajerosReserva(reservaId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener pasajeros de la reserva: " + e.getMessage(), e);
        }
    }
}
