package logica.DataTypes;

public class DTRutaVuelo {
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private DTCostoBase costoBase;
    private DTAerolinea aerolinea;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;

    public DTRutaVuelo(String nombre, String descripcion,DTFecha fechaAlta,DTCostoBase costoBase, DTAerolinea aerolinea, DTCiudad ciudadOrigen, DTCiudad ciudadDestino) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.aerolinea = aerolinea;
        this.costoBase = costoBase;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
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

    // MÉTODOS
    @Override
    public String toString() {
        return nombre;

    }
}
