package logica.clase;

import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

import java.util.List;

public class PaqueteVuelo {
    private String nombre;
    private String descripcion;
    private TipoAsiento tipoAsiento; // Mejor sería un Enum
    private int diasValidos;
    private float descuento;
    private DTCostoBase DTCostoBase;
    private float costoTotal;
    private DTFecha fechaAlta;

    private List<Cantidad> cantidad;

    private List<RutaVuelo> rutas;

    public PaqueteVuelo(String nombre, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta, TipoAsiento tipoAsiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.fechaAlta = fechaAlta;
        this.tipoAsiento = tipoAsiento;
        this.cantidad = null;
    }

    // Getter y Setter
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

    public DTCostoBase getCostoBase() {
        return DTCostoBase;
    }

    public void setCostoBase(DTCostoBase DTCostoBase) {
        this.DTCostoBase = DTCostoBase;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public List<Cantidad> getCantidad() {
        return cantidad;
    }

    public void setCantidad(List<Cantidad> cantidad) {
        this.cantidad = cantidad;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<RutaVuelo> getRutas() {
        return rutas;
    }

    public void setRutas(List<RutaVuelo> rutas) {
        this.rutas = rutas;
    }

    public void sumarCantidad(RutaVuelo ruta, int cant) {
        // buscar la ruta de vuelo en la lista de cantidades, asignarla la ruta de vuelo y su respectiva cantidad
        for (Cantidad c : this.cantidad) {
            if (c.getRutaVuelo().getNombre().equals(ruta.getNombre())) {
                c.setCant(c.getCant() + cant);
                return;
            }
        }
        // si no se encuentra, crear una nueva cantidad y agregarla a la lista
        Cantidad nuevaCantidad = new Cantidad(ruta, cant);
        this.cantidad.add(nuevaCantidad);
    }
}
