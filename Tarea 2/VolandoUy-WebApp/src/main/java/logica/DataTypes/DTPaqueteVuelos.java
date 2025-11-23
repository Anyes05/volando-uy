package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtPaqueteVuelos", propOrder = {
    "nombre",
    "descripcion",
    "diasValidos",
    "descuento",
    "costoTotal",
    "fechaAlta",
    "foto",
    "id",
    "cantidad",
    "rutas",
    "cantidadDisponible"
})

public class DTPaqueteVuelos {
    private String nombre;
    private String descripcion;
    private int diasValidos;
    private float descuento;
    private float costoTotal;
    private DTFecha fechaAlta;
    private byte[] foto;
    private Long id;

    private List<DTCantidad> cantidad;

    private List<DTRutaVuelo> rutas;
    private int cantidadDisponible;


    // Constructor sin parámetros requerido por JAXB
    public DTPaqueteVuelos() {
    }

    public DTPaqueteVuelos(String nombre, String descripcion /*TipoAsiento tipoAsiento*/, int diasValidos, float descuento, DTFecha fechaAlta, byte[] foto) {
//        this.tipoAsiento = tipoAsiento;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.foto = foto;
    }

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

//    public TipoAsiento getTipoAsiento() {
//        return tipoAsiento;
//    }
//
//    public void setTipoAsiento(TipoAsiento tipoAsiento) {
//        this.tipoAsiento = tipoAsiento;
//    }

    public int getDiasValidos() {
        return diasValidos;
    }

    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

//    public DTCostoBase getDTCostoBase() {
//        return DTCostoBase;
//    }
//
//    public void setDTCostoBase(DTCostoBase DTCostoBase) {
//        this.DTCostoBase = DTCostoBase;
//    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<DTCantidad> getCantidad() {
        return cantidad;
    }

    public void setCantidad(List<DTCantidad> cantidad) {
        this.cantidad = cantidad;
    }

    public List<DTRutaVuelo> getRutas() {
        return rutas;
    }

    public void setRutas(List<DTRutaVuelo> rutas) {
        this.rutas = rutas;
    }

    public String toString() {
        return nombre;
    }

    public byte[] getFoto() {
        return foto;
    }


    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
        return id;
    }
    
    public int getCantidadDisponible() {
        return cantidadDisponible;
    }
    
    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}
