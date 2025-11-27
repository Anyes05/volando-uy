package logica.ws;

import logica.config.ConfiguracionExterna;
import java.net.InetAddress;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para leer la configuración del Web Service desde archivos .properties
 * 
 * PRIORIDAD DE CARGA:
 * 1. Intenta cargar desde ~/volandouy/servidor.properties (configuración externa)
 * 2. Si no existe, intenta cargar desde recursos embebidos /config.properties (para desarrollo)
 * 3. Si no existe ninguno, usa valores por defecto
 */
public class ConfiguracionWS {
    
    private static final String CONFIG_FILE_EMBEDDED = "/config.properties";
    private static Properties propiedades;
    
    static {
        propiedades = new Properties();
        
        // Primero intentar cargar desde configuración externa (~/volandouy/servidor.properties)
        Properties propsExternas = ConfiguracionExterna.cargarConfigServidor();
        if (!propsExternas.isEmpty()) {
            propiedades.putAll(propsExternas);
            System.out.println(">>> Configuración del servidor cargada desde archivo externo.");
        } else {
            // Si no existe configuración externa, intentar desde recursos embebidos
            try {
                InputStream inputStream = ConfiguracionWS.class.getResourceAsStream(CONFIG_FILE_EMBEDDED);
                if (inputStream != null) {
                    propiedades.load(inputStream);
                    inputStream.close();
                    System.out.println(">>> Configuración del servidor cargada desde recursos embebidos.");
                } else {
                    System.err.println("ADVERTENCIA: No se encontró el archivo config.properties. Usando valores por defecto.");
                    // Valores por defecto
                    propiedades.setProperty("servidor.central.ip", "0.0.0.0");
                    propiedades.setProperty("servidor.central.puerto", "8082");
                    propiedades.setProperty("servidor.central.contexto", "/centralws");
                }
            } catch (Exception e) {
                System.err.println("ERROR al cargar config.properties: " + e.getMessage());
                e.printStackTrace();
                // Valores por defecto en caso de error
                propiedades.setProperty("servidor.central.ip", "0.0.0.0");
                propiedades.setProperty("servidor.central.puerto", "8082");
                propiedades.setProperty("servidor.central.contexto", "/centralws");
            }
        }
    }
    
    /**
     * Obtiene la IP del servidor desde la configuración
     */
    public static String getIP() {
        return propiedades.getProperty("servidor.central.ip", "0.0.0.0");
    }
    
    /**
     * Obtiene el puerto del servidor desde la configuración
     */
    public static int getPuerto() {
        try {
            return Integer.parseInt(propiedades.getProperty("servidor.central.puerto", "8082"));
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Puerto inválido en config.properties. Usando 8082 por defecto.");
            return 8082;
        }
    }
    
    /**
     * Obtiene el contexto/path del servicio desde la configuración
     */
    public static String getContexto() {
        return propiedades.getProperty("servidor.central.contexto", "/centralws");
    }
    
    /**
     * Construye la URL completa del servicio web
     */
    public static String getURL() {
        String ip = getIP();
        int puerto = getPuerto();
        String contexto = getContexto();

        String ipParaURL;
        if ("0.0.0.0".equals(ip)) {
            // Intentar usar 0.0.0.0 directamente (funciona con JAX-WS moderno)
            // Si no funciona, JAX-WS lanzará una excepción y podremos usar la IP real como fallback
            ipParaURL = "0.0.0.0";
            System.out.println(">>> IP configurada como 0.0.0.0, el servidor escuchará en todas las interfaces");
        } else {
            ipParaURL = ip;
        }
        
        return String.format("http://%s:%d%s", ipParaURL, puerto, contexto);
    }
    
    /**
     * Obtiene todas las propiedades cargadas (útil para debugging)
     */
    public static Properties getPropiedades() {
        return new Properties(propiedades);
    }
}

