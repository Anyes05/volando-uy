package logica.DataTypes;

public class DTRutaVuelo {
    private DTHora horaVuelo;
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private CostoBase costoBase;
    private DTAerolinea aerolinea;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;

    public DTRutaVuelo(DTHora horaVuelo, String nombre, String descripcion, DTFecha fechaAlta, CostoBase costoBase, DTAerolinea aerolinea, DTCiudad ciudadOrigen, DTCiudad ciudadDestino) {
        this.horaVuelo = horaVuelo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.costoBase = costoBase;
        this.aerolinea = aerolinea;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
    }

    // Getters
    public DTHora getHoraVuelo() {
        return horaVuelo;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public DTFecha getFechaAlta() {
        return fechaAlta;
    }
    public CostoBase getCostoBase() {
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

    //Setters
    }
