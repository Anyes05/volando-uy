package logica.DataTypes;

public class DTVuelo {
    private DTHora duracion;
    private String nombre;
    private DTFecha fechaVuelo;
    private int asientosMaxEjecutivo;
    private DTFecha fechaAlta;
    private int asientosMaxTurista;

    public DTVuelo(DTHora duracion, String nombre, DTFecha fechaVuelo, int asientosMaxEjecutivo, DTFecha fechaAlta, int asientosMaxTurista) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;
        this.asientosMaxTurista = asientosMaxTurista;
    }

    //Getters
    public DTHora getDuracion() {
        return duracion;
    }
    public String getNombre() {
        return nombre;
    }
    public DTFecha getFechaVuelo() {
        return fechaVuelo;
    }
    public int getAsientosMaxEjecutivo() {
        return asientosMaxEjecutivo;
    }
    public DTFecha getFechaAlta() {
        return fechaAlta;
    }
    public int getAsientosMaxTurista() {
        return asientosMaxTurista;
    }

    //Setters


}
