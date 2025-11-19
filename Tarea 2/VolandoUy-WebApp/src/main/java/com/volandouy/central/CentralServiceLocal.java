package com.volandouy.central;

import java.util.List;

import logica.clase.Sistema;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTVuelo;

/**
 * Implementacion LOCAL, usa directamente Sistema.
 * Sirve para pruebas o cuando el WS no esta disponible.
 */
public class CentralServiceLocal implements CentralService {

    private final Sistema sistema = Sistema.getInstance();

    @Override
    public List<DTAerolinea> listarAerolineas() {
        return sistema.listarAerolineas();
    }

    @Override
    public List<DTRutaVuelo> listarRutasPorAerolinea(String nicknameAerolinea) {
        return sistema.listarRutaVuelo(nicknameAerolinea);
    }

    @Override
    public List<DTVuelo> listarVuelosDeRuta(String nombreRuta) {
        return sistema.seleccionarRutaVuelo(nombreRuta);
    }
}
