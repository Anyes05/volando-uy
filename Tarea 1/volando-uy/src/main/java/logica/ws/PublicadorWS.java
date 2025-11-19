package logica.ws;

import jakarta.xml.ws.Endpoint;

/**
 * Publicador WS: levanta el servicio en la URL indicada.
 * Ejecutalo como "Java Application" en IntelliJ.
 */
public class PublicadorWS {

    private static final String URL = "http://localhost:8082/centralws";

    public static void main(String[] args) {
        System.out.println(">>> Iniciando Publicador CentralWS en " + URL + " ...");

        logica.ws.CentralWS servicio = new logica.ws.CentralWS();

        Endpoint.publish(URL, servicio);

        System.out.println(">>> CentralWS publicado correctamente.");
        System.out.println(">>> WSDL disponible en: " + URL + "?wsdl");
    }
}
