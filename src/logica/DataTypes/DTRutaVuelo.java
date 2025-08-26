package logica.DataTypes;

public class DTRutaVuelo {
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private DTCostoBase DTCostoBase;
    private DTAerolinea aerolinea;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;

    public DTRutaVuelo(String nombre, String descripcion, DTFecha fechaAlta, DTCostoBase DTCostoBase, DTAerolinea aerolinea, DTCiudad ciudadOrigen, DTCiudad ciudadDestino) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.DTCostoBase = DTCostoBase;
        this.aerolinea = aerolinea;
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
        return DTCostoBase;
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
