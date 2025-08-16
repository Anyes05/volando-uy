package logica.clase;

import logica.DataTypes.CostoBase;
import logica.DataTypes.TipoAsiento;

public class PaqueteVuelo {
    private String nombre;
    private String descripcion;
    private TipoAsiento tipoAsiento; // Mejor sería un Enum
    private int diasValidos;
    private float descuento;
    private CostoBase costoBase;
    private float costoTotal;

    private Cantidad cantidad;

    public PaqueteVuelo(String nombre, String descripcion, TipoAsiento tipoAsiento,
                        int diasValidos, float descuento, CostoBase costoBase, float costoTotal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoAsiento = tipoAsiento;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.costoBase = costoBase;
        this.costoTotal = costoTotal;
        this.cantidad = null;
    }

    // Getter y Setter
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

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

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public CostoBase getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(CostoBase costoBase) {
        this.costoBase = costoBase;
    }
    public float getCostoTotal() {
        return costoTotal;
    }
    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Cantidad getCantidad() {
        return cantidad;
    }
    public void setCantidad(Cantidad cantidad) {
        this.cantidad = cantidad;
    }
}
