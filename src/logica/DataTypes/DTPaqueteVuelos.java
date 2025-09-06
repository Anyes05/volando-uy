package logica.DataTypes;

import dato.entidades.Cantidad;
import dato.entidades.RutaVuelo;

import java.util.List;

public class DTPaqueteVuelos {
    private String nombre;
    private String descripcion;
    private TipoAsiento tipoAsiento; // Mejor sería un Enum
    private int diasValidos;
    private float descuento;
    private DTCostoBase DTCostoBase;
    private float costoTotal;
    private DTFecha fechaAlta;

    private List<dato.entidades.Cantidad> cantidad;

    private List<dato.entidades.RutaVuelo> rutas;


    public DTPaqueteVuelos(String nombre, String descripcion, TipoAsiento tipoAsiento, int diasValidos, float descuento, DTFecha fechaAlta) {
        this.tipoAsiento = tipoAsiento;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
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

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

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

    public DTCostoBase getDTCostoBase() {
        return DTCostoBase;
    }

    public void setDTCostoBase(DTCostoBase DTCostoBase) {
        this.DTCostoBase = DTCostoBase;
    }

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

    public List<dato.entidades.Cantidad> getCantidad() {
        return cantidad;
    }

    public void setCantidad(List<Cantidad> cantidad) {
        this.cantidad = cantidad;
    }

    public List<dato.entidades.RutaVuelo> getRutas() {
        return rutas;
    }

    public void setRutas(List<RutaVuelo> rutas) {
        this.rutas = rutas;
    }

    public String toString() {
        return nombre;
    }
}
