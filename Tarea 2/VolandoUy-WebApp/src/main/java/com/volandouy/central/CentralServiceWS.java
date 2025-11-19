package com.volandouy.central;

import jakarta.xml.ws.Service;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;
import logica.ws.CentralWS;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

/**
 * Implementacion que consume el WebService SOAP CentralWS.
 */
public class CentralServiceWS implements CentralService {

    private final CentralWS port;

    /**
     * wsdlUrl: URL completa al ?wsdl, por ejemplo:
     * "http://localhost:8082/centralws?wsdl"
     */
    public CentralServiceWS(String wsdlUrl) {
        try {
            URL url = new URL(wsdlUrl);

            // targetNamespace sale del paquete de CentralWS:
            // package logica.webservices;
            // -> "http://webservices.logica/"
            QName serviceName = new QName("http://ws.logica/", "CentralWSService");

            Service service = Service.create(url, serviceName);

            // CentralWS viene del JAR del central (mi-libreria)
            this.port = service.getPort(CentralWS.class);

        } catch (Exception e) {
            throw new RuntimeException("Error inicializando CentralServiceWS: " + e.getMessage(), e);
        }
    }

    @Override
    public List<DTAerolinea> listarAerolineas() {
        return port.listarAerolineas();
    }

    @Override
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea) {
        return port.listarRutasPorAerolinea(nicknameAerolinea);
    }

    @Override
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        return port.listarVuelosDeRuta(nombreRuta);
    }
}
