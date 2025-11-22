package com.volandouy.central;

import java.io.InputStream;
import java.util.Properties;

/**
 * Clase utilitaria para leer la configuración del cliente Web Service desde archivos .properties
 * Esta clase permite configurar la URL del Servidor Central sin hardcodearla en el código.
 */
public class ConfiguracionClienteWS {
    
    private static final String CONFIG_FILE = "/config.properties";
    private static Properties propiedades;
    
    static {
        propiedades = new Properties();
        try {
            InputStream inputStream = ConfiguracionClienteWS.class.getResourceAsStream(CONFIG_FILE);
            if (inputStream != null) {
                propiedades.load(inputStream);
                inputStream.close();
            } else {
                System.err.println("ADVERTENCIA: No se encontró el archivo config.properties. Usando valores por defecto.");
                // Valores por defecto
                propiedades.setProperty("servidor.central.url", "http://localhost:8082/centralws");
                propiedades.setProperty("servidor.central.timeout", "30000");
            }
        } catch (Exception e) {
            System.err.println("ERROR al cargar config.properties: " + e.getMessage());
            e.printStackTrace();
            // Valores por defecto en caso de error
            propiedades.setProperty("servidor.central.url", "http://localhost:8082/centralws");
            propiedades.setProperty("servidor.central.timeout", "30000");
        }
    }
    
    /**
     * Obtiene la URL completa del Servidor Central desde la configuración
     * @return URL del servicio web (ej: http://192.168.1.100:8082/centralws)
     */
    public static String getURLServidorCentral() {
        return propiedades.getProperty("servidor.central.url", "http://localhost:8082/centralws");
    }
    
    /**
     * Obtiene la URL del WSDL del Servidor Central
     * @return URL del WSDL (ej: http://192.168.1.100:8082/centralws?wsdl)
     */
    public static String getWSDLURL() {
        String baseUrl = getURLServidorCentral();
        // Asegurar que termine con ?wsdl
        if (baseUrl.contains("?wsdl")) {
            return baseUrl;
        }
        return baseUrl + "?wsdl";
    }
    
    /**
     * Obtiene el timeout para las conexiones (en milisegundos)
     */
    public static int getTimeout() {
        try {
            return Integer.parseInt(propiedades.getProperty("servidor.central.timeout", "30000"));
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Timeout inválido en config.properties. Usando 30000 por defecto.");
            return 30000;
        }
    }
    
    /**
     * Obtiene todas las propiedades cargadas (útil para debugging)
     */
    public static Properties getPropiedades() {
        return new Properties(propiedades);
    }
}

