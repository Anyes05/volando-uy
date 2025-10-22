package logica.DataTypes;

public class DTVuelo {
    private DTHora duracion;
    private String nombre;
    private DTFecha fechaVuelo;
    private DTHora horaVuelo;
    private int asientosMaxEjecutivo;
    private DTFecha fechaAlta;
    private int asientosMaxTurista;
    private DTRutaVuelo ruta;
    private byte[] foto;


    public DTVuelo(DTHora duracion, String nombre, DTFecha fechaVuelo, DTHora horaVuelo, int asientosMaxEjecutivo, DTFecha fechaAlta, int asientosMaxTurista, DTRutaVuelo ruta, byte[] foto) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;
        this.asientosMaxTurista = asientosMaxTurista;
        this.ruta = ruta;
        this.foto = foto;
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

    public DTHora getHoraVuelo() {
        return horaVuelo;
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

    public DTRutaVuelo getRuta() {
        return ruta;
    }

    public byte[] getFoto() {
        return foto;
    }

    //Setters
    public String toString() {
        return nombre;
    }

}
