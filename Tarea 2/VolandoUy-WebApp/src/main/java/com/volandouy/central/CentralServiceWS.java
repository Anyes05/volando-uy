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
            throw new RuntimeException("Error inicializando CentralServiceWS con URL: " + wsdlUrl + 
                                     ". Error: " + e.getMessage(), e);
        }
    }

    // ===========================
    //      AEROLÍNEAS
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
    //         CIUDADES
    // ===========================

    public List<DTCiudad> listarCiudades() {
        try {
            return port.listarCiudades();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar ciudades: " + e.getMessage(), e);
        }
    }

    public List<String> listarAeropuertos() {
        try {
            return port.listarAeropuertos();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar aeropuertos: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         RUTAS
    // ===========================

    @Override
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea) {
        try {
            return port.listarRutasPorAerolinea(nicknameAerolinea);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar rutas por aerolínea: " + e.getMessage(), e);
        }
    }

    public List<DTRutaVuelo> listarRutasDeVuelo() {
        try {
            return port.listarRutasDeVuelo();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar rutas de vuelo: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         VUELOS
    // ===========================

    @Override
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        try {
            return port.listarVuelosDeRuta(nombreRuta);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar vuelos de ruta: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         CLIENTES
    // ===========================

    public List<DTCliente> listarClientes() {
        try {
            return port.listarClientes();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar clientes: " + e.getMessage(), e);
        }
    }

    public DTUsuario mostrarDatosUsuario(String nickname) {
        try {
            return port.mostrarDatosUsuario(nickname);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener datos de usuario: " + e.getMessage(), e);
        }
    }

    public List<DTUsuario> consultarUsuarios() {
        try {
            return port.consultarUsuarios();
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar usuarios: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         RESERVAS
    // ===========================

    public List<DTVueloReserva> listarReservasVuelo(String nombreVuelo) {
        try {
            return port.listarReservasVuelo(nombreVuelo);
        } catch (Exception e) {
            throw new RuntimeException("Error al listar reservas de vuelo: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         PAQUETES
    // ===========================

    public List<DTPaqueteVuelos> mostrarPaquetes() {
        try {
            return port.mostrarPaquetes();
        } catch (Exception e) {
            throw new RuntimeException("Error al mostrar paquetes: " + e.getMessage(), e);
        }
    }

    public List<DTPaqueteVuelos> obtenerPaquetesNoComprados() {
        try {
            return port.obtenerPaquetesNoComprados();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener paquetes no comprados: " + e.getMessage(), e);
        }
    }

    // ===========================
    //         PRUEBA
    // ===========================

    public String ping() {
        try {
            return port.ping();
        } catch (Exception e) {
            throw new RuntimeException("Error en ping: " + e.getMessage(), e);
        }
    }
}
