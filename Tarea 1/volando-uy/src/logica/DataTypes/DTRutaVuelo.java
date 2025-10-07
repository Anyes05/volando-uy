package logica.DataTypes;

import dato.entidades.Categoria;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DTRutaVuelo {
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private DTCostoBase costoBase;
    private DTAerolinea aerolinea;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;
    private List<Categoria> categorias;
    private byte[] foto;

    public DTRutaVuelo(String nombre, String descripcion, DTFecha fechaAlta, DTCostoBase costoBase, DTAerolinea aerolinea, DTCiudad ciudadOrigen, DTCiudad ciudadDestino, byte[] foto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.aerolinea = aerolinea;
        this.costoBase = costoBase;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.foto = foto;
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public DTCostoBase getCostoBase() {
        return costoBase;
    }

    public DTAerolinea getAerolinea() {
        return aerolinea;
    }

    public DTCiudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public DTCiudad getCiudadDestino() {
        return ciudadDestino;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public byte[] getFoto() {
        return foto;
    }

    // MÉTODOS
    @Override
    public String toString() {
        return nombre;
    }


    public String toString2() {
        String nombresCategorias = "";
        for (Categoria c : categorias) {
            nombresCategorias = nombresCategorias + c.getNombre() + "     ";

        }
        return nombresCategorias;
    }
}
