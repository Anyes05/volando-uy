package logica.DataTypes;

public class DTRutaVuelo {
    private DTHora horaVuelo;
    private String nombre;
    private String descripcion;
    private DTFecha fechaAlta;
    private CostoBase costoBase;
    private int costoEquipajeExtra;
    private DTAerolinea aerolinea;
    private DTCategoria categoria;
    private DTCiudad ciudadOrigen;
    private DTCiudad ciudadDestino;

    public DTRutaVuelo(DTHora horaVuelo, String nombre, String descripcion, DTFecha fechaAlta, CostoBase costoBase, int costoEquipajeExtra, DTAerolinea aerolinea, DTCategoria categoria, DTCiudad ciudadOrigen, DTCiudad ciudadDestino) {
        this.horaVuelo = horaVuelo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.costoBase = costoBase;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.aerolinea = aerolinea;
        this.categoria = categoria;
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
    public int getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }
    public DTAerolinea getAerolinea() {
        return aerolinea;
    }
    public DTCategoria getCategoria() {
        return categoria;
    }
    public DTCiudad getCiudadOrigen() {
        return ciudadOrigen;
    }
    public DTCiudad getCiudadDestino() {
        return ciudadDestino;
    }

    //Setters
    }
