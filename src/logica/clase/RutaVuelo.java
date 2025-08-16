package logica.clase;

import logica.DataTypes.CostoBase;
import java.util.ArrayList;
import java.util.List;


public class RutaVuelo {
    private String nombre;
    private String descripcion;
    private CostoBase costoBase;
    private float costoEquipajeExtra;

    // Relaciones de muchos
    private List<Categoria> categorias;   // 1..*
    private List<Vuelo> vuelos;           // 1..*
    private List<Aerolinea> aerolineas;

    //Relaciones de uno
    private Ciudad ciudadOrigen;
    private Ciudad ciudadDestino;

    //CONSTRUCTOR
    public RutaVuelo(String nombre, String descripcion, CostoBase costoBase, float costoEquipajeExtra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.costoEquipajeExtra = costoEquipajeExtra;

        this.categorias = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.aerolineas = new ArrayList<>();

        this.ciudadDestino= null;
        this.ciudadOrigen= null;
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
}
