package logica.clase;

import logica.DataTypes.CostoBase;
import logica.DataTypes.DTCategoria;
import logica.DataTypes.DTAerolinea;
import logica.DataTypes.DTVuelo;

import java.util.ArrayList;
import java.util.List;


public class rutaVuelo {
    private String nombre;
    private String descripcion;
    private CostoBase costoBase;
    private float costoEquipajeExtra;

    // Relaciones de muchos
    private List<DTCategoria> categorias;   // 1..*
    private List<DTVuelo> vuelos;           // 1..*
    private List<DTAerolinea> aerolineas;

    //Relaciones de uno
    private ciudad ciudadOrigen;
    private ciudad ciudadDestino;

    //CONSTRUCTOR
    public rutaVuelo(String nombre, String descripcion, CostoBase costoBase, float costoEquipajeExtra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.costoEquipajeExtra = costoEquipajeExtra;

        this.categorias = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.aerolineas = new ArrayList<>();
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

    public CostoBase getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(CostoBase costoBase) {
        this.costoBase = costoBase;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public List<DTCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<DTCategoria> categorias) {
        this.categorias = categorias;
    }

    public ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public List<DTVuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<DTVuelo> vuelos) {
        this.vuelos = vuelos;
    }

    public List<DTAerolinea> getAerolineas() {
        return aerolineas;
    }

    public void setAerolineas(List<DTAerolinea> aerolineas) {
        this.aerolineas = aerolineas;
    }

    public ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }
}
