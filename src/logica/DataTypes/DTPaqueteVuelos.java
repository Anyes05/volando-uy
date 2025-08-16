package logica.DataTypes;

public class DTPaqueteVuelos {
    private TipoAsiento tipoAsiento;
    private int diasValidos;
    private float descuento;
    private String nombre;
    private String descripcion;
    private float costoTotal;

    public DTPaqueteVuelos(TipoAsiento tipoAsiento, int diasValidos, float descuento, String nombre, String descripcion, float costoTotal) {
        this.tipoAsiento = tipoAsiento;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoTotal = costoTotal;
    }

    //Getters
    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }
    public int getDiasValidos() {
        return diasValidos;
    }
    public float getDescuento() {
        return descuento;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public float getCostoTotal() {
        return costoTotal;
    }

    // Setters
    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }
    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }
    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

}
