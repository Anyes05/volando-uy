package logica.ws;

import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para leer la configuración del Web Service desde archivos .properties
 */
public class ConfiguracionWS {
    
    private static final String CONFIG_FILE = "/config.properties";
    private static Properties propiedades;
    
    static {
        propiedades = new Properties();
        try {
            InputStream inputStream = ConfiguracionWS.class.getResourceAsStream(CONFIG_FILE);
            if (inputStream != null) {
                propiedades.load(inputStream);
                inputStream.close();
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
        
        // Si la IP es 0.0.0.0, usar localhost para la URL (aunque el servidor escuchará en todas las interfaces)
        String ipParaURL = "0.0.0.0".equals(ip) ? "localhost" : ip;
        
        return String.format("http://%s:%d%s", ipParaURL, puerto, contexto);
    }
    
    /**
     * Obtiene todas las propiedades cargadas (útil para debugging)
     */
    public static Properties getPropiedades() {
        return new Properties(propiedades);
    }
}

