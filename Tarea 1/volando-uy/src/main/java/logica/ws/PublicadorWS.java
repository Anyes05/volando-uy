package logica.ws;

import jakarta.xml.ws.Endpoint;
import com.sun.net.httpserver.HttpServer;
import java.net.InetAddress;
import java.net.InetSocketAddress;

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
            Endpoint endpoint;

            if ("0.0.0.0".equals(ip)) {
                System.out.println(">>> Configurando servidor para escuchar en todas las interfaces (0.0.0.0)...");

                // Crear un HttpServer que escuche en todas las interfaces
                HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", puerto), 0);

                // Crear el endpoint
                endpoint = Endpoint.create(servicio);

                // Publicar el endpoint en el contexto
                endpoint.publish(server.createContext(contexto));

                // Iniciar el servidor
                server.start();

                System.out.println(">>> ✓ Servidor HTTP iniciado en 0.0.0.0:" + puerto);
            } else {
                // Para IPs específicas, usar el método normal
                endpoint = Endpoint.publish(url, servicio);
            }
            
            if (endpoint != null) {
                System.out.println(">>> ✓ CentralWS publicado correctamente.");
                // Construir URL para mostrar (usar IP real si es 0.0.0.0)
                String urlParaMostrar;
                if ("0.0.0.0".equals(ip)) {
                    try {
                        String ipReal = InetAddress.getLocalHost().getHostAddress();
                        urlParaMostrar = String.format("http://%s:%d%s", ipReal, puerto, contexto);
                        System.out.println(">>> ✓ WSDL disponible en: " + urlParaMostrar + "?wsdl");
                        System.out.println(">>> ✓ También accesible desde otras IPs de esta máquina");
                    } catch (Exception e) {
                        urlParaMostrar = url;
                        System.out.println(">>> ✓ WSDL disponible en: " + urlParaMostrar + "?wsdl");
                    }
                } else {
                    urlParaMostrar = url;
                    System.out.println(">>> ✓ WSDL disponible en: " + urlParaMostrar + "?wsdl");
                }

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
