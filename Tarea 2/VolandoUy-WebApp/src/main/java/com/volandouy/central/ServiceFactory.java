package com.volandouy.central;

/**
 * Factory para obtener una instancia de CentralService.
 *
 * Por ahora:
 *   - usarWS = true -> usa WebService
 *   - usarWS = false -> usa Sistema local
 */
public class ServiceFactory {

    // Cambia esto a true cuando quieras probar con Web Services
    private static final boolean usarWS = true;

    private static CentralService instance;

    public static CentralService getCentralService() {
        if (instance != null) {
            return instance;
        }

        if (usarWS) {
            // URL del WSDL del servidor central
            String wsdlUrl = "http://localhost:8082/centralws?wsdl";
            instance = new CentralServiceWS(wsdlUrl);
        } else {
            instance = new CentralServiceLocal();
        }

        return instance;
    }
}
