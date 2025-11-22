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

    // Cambia esto a true para usar Web Services (recomendado para producción)
    // false para usar Sistema local (útil para desarrollo/testing)
    private static final boolean usarWS = true;

    private static CentralService instance;

    /**
     * Obtiene una instancia de CentralService.
     * La URL del servidor se lee desde config.properties.
     */
    public static CentralService getCentralService() {
        if (instance != null) {
            return instance;
        }

        if (usarWS) {
            // Leer URL del WSDL desde archivo de configuración
            String wsdlUrl = ConfiguracionClienteWS.getWSDLURL();
            System.out.println(">>> Conectando al Servidor Central: " + wsdlUrl);
            instance = new CentralServiceWS(wsdlUrl);
        } else {
            System.out.println(">>> Usando Sistema local (sin Web Services)");
            instance = new CentralServiceLocal();
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
