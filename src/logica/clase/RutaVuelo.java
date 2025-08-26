package logica.clase;

import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;

import java.util.ArrayList;
import java.util.List;


public class RutaVuelo {
    private String nombre;
    private String descripcion;
    private DTCostoBase DTCostoBase;
    private DTFecha fechaAlta;

    // Relaciones de muchos
    private List<Categoria> categorias;   // 1..*
    private List<Vuelo> vuelos;           // 1..*
    private List<Aerolinea> aerolineas;

    //Relaciones de uno
    private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;

    //CONSTRUCTOR
    public RutaVuelo(String nombre, String descripcion, DTCostoBase DTCostoBase) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.DTCostoBase = DTCostoBase;

        this.categorias = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.aerolineas = new ArrayList<>();

        this.ciudadDestino = null;
        this.ciudadOrigen = null;
    }

    //GET Y SET
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DTCostoBase getCostoBase() {
        return DTCostoBase;
    }

    public void setCostoBase(DTCostoBase DTCostoBase) {
        this.DTCostoBase = DTCostoBase;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public List<Aerolinea> getAerolineas() {
        return aerolineas;
    }

    public void setAerolineas(List<Aerolinea> aerolineas) {
        this.aerolineas = aerolineas;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}

