package com.volandouy.central;

import java.util.List;

import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;
import logica.ws.CentralWS;

/**
 * Interfaz que define los servicios que el Servidor Web
 * necesita del Servidor Central.
 *
 * La implementacion puede ser local (Sistema) o via WebService.
 */
public interface CentralService {

    // Aerolineas
    List<DTAerolinea> listarAerolineas();

    // Rutas
    List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea);

    // Vuelos
    List<DTVuelo> listarVuelosDeRuta(String nombreRuta);
}
