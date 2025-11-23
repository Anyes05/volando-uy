package com.volandouy.central;

/**
 * Factory para obtener una instancia de CentralService.
 *
 * La configuración se lee desde el archivo config.properties:
 *   - servidor.central.url: URL del Servidor Central
 *
 * Por defecto usa Web Services, pero puede cambiarse a local.
 */
public class ServiceFactory {

    private static CentralService instance;

    /**
     * Obtiene una instancia de CentralService usando Web Services.
     * La URL del servidor se lee desde config.properties.
     * 
     * IMPORTANTE: Este proyecto funciona únicamente con Web Services.
     * No se permite el uso directo del .jar de la Tarea 1.
     */
    public static CentralService getCentralService() {
        if (instance != null) {
            return instance;
        }

        try {
            // Leer URL del WSDL desde archivo de configuración
            String wsdlUrl = ConfiguracionClienteWS.getWSDLURL();
            System.out.println(">>> Conectando al Servidor Central vía Web Services: " + wsdlUrl);
            System.out.println(">>> IMPORTANTE: Asegúrate de que el Servidor Central esté ejecutándose.");
            System.out.println(">>> Ejecuta PublicadorWS.java desde la Tarea 1 antes de usar la aplicación web.");
            
            instance = new CentralServiceWS(wsdlUrl);
            System.out.println(">>> ✓ Conexión al Servidor Central establecida correctamente.");
            
        } catch (Exception e) {
            System.err.println(">>> ✗ ERROR: No se pudo conectar al Servidor Central.");
            System.err.println(">>> URL intentada: " + ConfiguracionClienteWS.getWSDLURL());
            System.err.println(">>> Error: " + e.getMessage());
            System.err.println(">>> SOLUCIÓN: Ejecuta PublicadorWS.java desde la Tarea 1.");
            e.printStackTrace();
            throw new RuntimeException("No se pudo conectar al Servidor Central. " +
                    "Asegúrate de que el Servidor Central esté ejecutándose en: " + 
                    ConfiguracionClienteWS.getWSDLURL(), e);
        }

        return instance;
    }
    
    /**
     * Fuerza la recreación de la instancia (útil para cambiar de configuración en runtime)
     */
    public static void reset() {
        instance = null;
    }
}
