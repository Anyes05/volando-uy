package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtRutaVuelo", propOrder = {
    "nombre",
    "descripcion",
    "fechaAlta",
    "costoBase",
    "aerolinea",
    "ciudadOrigen",
    "ciudadDestino",
    "categorias",
    "foto",
    "videoUrl",
    "estado"
})
public class DTRutaVuelo {
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private DTCostoBase costoBase;
    private DTAerolinea aerolinea;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;
    private List<DTCategoria> categorias;
    private byte[] foto;
    private String videoUrl;
    private EstadoRutaVuelo estado;

    // Constructor sin parámetros requerido por JAXB
    public DTRutaVuelo() {
    }

    public DTRutaVuelo(String nombre, String descripcion, DTFecha fechaAlta, DTCostoBase costoBase, DTAerolinea aerolinea, DTCiudad ciudadOrigen, DTCiudad ciudadDestino, byte[] foto, EstadoRutaVuelo estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.aerolinea = aerolinea;
        this.costoBase = costoBase;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.foto = foto;
        this.estado = estado;
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

    public List<DTCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<DTCategoria> categorias) {
        this.categorias = categorias;
    }

    public byte[] getFoto() {
        return foto;
    }

    public EstadoRutaVuelo getEstado() {
        return estado;
    }
    
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    // MÉTODOS
    @Override
    public String toString() {
        return nombre;
    }


    public String toString2() {
        String nombresCategorias = "";
        for (DTCategoria c : categorias) {
            nombresCategorias = nombresCategorias + c.getNombre() + "     ";

        }
        return nombresCategorias;
    }
}
