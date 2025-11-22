package logica.ws;

import jakarta.xml.ws.Endpoint;

/**
 * Publicador WS: levanta el servicio en la URL indicada.
 * La configuración se lee desde el archivo config.properties (IP, puerto, contexto).
 * 
 * Ejecutar como "Java Application" en IntelliJ o desde línea de comandos.
 * 
 * IMPORTANTE: Asegúrate de que el archivo config.properties esté en:
 * src/main/resources/config.properties
 */
public class PublicadorWS {

    public static void main(String[] args) {
        // Leer configuración desde archivo .properties
        String ip = ConfiguracionWS.getIP();
        int puerto = ConfiguracionWS.getPuerto();
        String contexto = ConfiguracionWS.getContexto();
        String url = ConfiguracionWS.getURL();
        
        System.out.println("==========================================");
        System.out.println(">>> Configuración del Servidor Central:");
        System.out.println(">>> IP: " + ip);
        System.out.println(">>> Puerto: " + puerto);
        System.out.println(">>> Contexto: " + contexto);
        System.out.println(">>> URL completa: " + url);
        System.out.println("==========================================");
        System.out.println(">>> Iniciando Publicador CentralWS...");

        try {
            CentralWS servicio = new CentralWS();
            
            // Publicar el servicio web
            Endpoint endpoint = Endpoint.publish(url, servicio);
            
            if (endpoint != null) {
                System.out.println(">>> ✓ CentralWS publicado correctamente.");
                System.out.println(">>> ✓ WSDL disponible en: " + url + "?wsdl");
                System.out.println(">>> ✓ Servicio escuchando en todas las interfaces (0.0.0.0)");
                System.out.println(">>> Presiona Ctrl+C para detener el servidor.");
            } else {
                System.err.println(">>> ✗ ERROR: No se pudo publicar el servicio.");
            }
        } catch (Exception e) {
            System.err.println(">>> ✗ ERROR al publicar el servicio: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
