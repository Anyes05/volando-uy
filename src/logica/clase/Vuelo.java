package logica.clase;

import logica.DataTypes.DTFecha;
import logica.DataTypes.DTHora;
import logica.DataTypes.CostoBase;
import java.util.ArrayList;
import java.util.List;

public class Vuelo {
    private String nombre;
    private DTFecha fechaVuelo;
    private DTHora horaVuelo;
    private DTHora duracion;
    private int asientosMaxTurista;
    private int asientosMaxEjecutivo;
    private DTFecha fechaAlta;

    // REFERENCIAS
    private List<RutaVuelo> rutaVuelo;
    private List<Reserva> reserva;

    // ----CONSTRUCTOR----
    public Vuelo(String nombre, DTFecha fechaVuelo, DTHora horaVuelo, DTHora duracion, int asientosMaxTurista, int asientosMaxEjecutivo, DTFecha fechaAlta) {
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.duracion = duracion;
        this.asientosMaxTurista = asientosMaxTurista;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;

        this.rutaVuelo = new ArrayList<>();
        this.reserva = new ArrayList<>();

    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DTFecha getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(DTFecha fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public DTHora getHoraVuelo() {
        return horaVuelo;
    }

    public void setHoraVuelo(DTHora horaVuelo) {
        this.horaVuelo = horaVuelo;
    }

    public DTHora getDuracion() {
        return duracion;
    }

    public void setDuracion(DTHora duracion) {
        this.duracion = duracion;
    }

    public int getAsientosMaxTurista() {
        return asientosMaxTurista;
    }

    public void setAsientosMaxTurista(int asientosMaxTurista) {
        this.asientosMaxTurista = asientosMaxTurista;
    }

    public int getAsientosMaxEjecutivo() {
        return asientosMaxEjecutivo;
    }

    public void setAsientosMaxEjecutivo(int asientosMaxEjecutivo) {
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public List<RutaVuelo> getRutaVuelo() {
        return rutaVuelo;
    }

    public void setRutaVuelo(List<RutaVuelo> rutaVuelo) {
        this.rutaVuelo = rutaVuelo;
    }

    public List<Reserva> getReserva() {
        return reserva;
    }

    public void setReserva(List<Reserva> reserva) {
        this.reserva = reserva;
    }
}
