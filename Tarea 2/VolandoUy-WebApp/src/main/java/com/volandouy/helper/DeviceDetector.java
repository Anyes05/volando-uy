package com.volandouy.helper;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class para detectar el tipo de dispositivo desde el que se accede
 * a la aplicación web.
 */
public class DeviceDetector {
    
    /**
     * Detecta si la petición proviene de un dispositivo móvil
     * basándose en el User-Agent del navegador.
     * 
     * @param request HttpServletRequest de la petición
     * @return true si es un dispositivo móvil, false en caso contrario
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        
        if (userAgent == null || userAgent.isEmpty()) {
            return false;
        }
        
        // Convertir a minúsculas para comparación case-insensitive
        String userAgentLower = userAgent.toLowerCase();
        
        // Patrones comunes de dispositivos móviles
        return userAgentLower.matches(".*(android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini|mobile|tablet).*");
    }
    
    /**
     * Detecta si la petición proviene de una tablet
     * 
     * @param request HttpServletRequest de la petición
     * @return true si es una tablet, false en caso contrario
     */
    public static boolean isTabletDevice(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        
        if (userAgent == null || userAgent.isEmpty()) {
            return false;
        }
        
        String userAgentLower = userAgent.toLowerCase();
        
        // Tablets específicas
        return userAgentLower.matches(".*(ipad|tablet|playbook|silk).*") 
            && !userAgentLower.matches(".*(mobile|phone).*");
    }
    
    /**
     * Detecta si la petición proviene de un teléfono móvil (no tablet)
     * 
     * @param request HttpServletRequest de la petición
     * @return true si es un teléfono móvil, false en caso contrario
     */
    public static boolean isMobilePhone(HttpServletRequest request) {
        return isMobileDevice(request) && !isTabletDevice(request);
    }
}

