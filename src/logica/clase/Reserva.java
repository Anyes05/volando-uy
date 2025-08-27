package logica.clase;

import logica.DataTypes.DTFecha;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.TipoAsiento;

import java.util.ArrayList;
import java.util.List;

public abstract class Reserva {
    //Atributos
    private Cliente cliente;
    private DTFecha fechaReserva;
    private DTCostoBase costoReserva;
    private TipoAsiento tipoAsiento;
    private List<Pasaje> pasajeros;

    // Constructor
    public Reserva(Cliente cliente, DTFecha fechaReserva, TipoAsiento tipoAsiento) {
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
        this.tipoAsiento = tipoAsiento;

        this.pasajeros = new ArrayList<>();
    }

    // getters y setters


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DTFecha getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(DTFecha fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public DTCostoBase getCostoReserva() {
        return costoReserva;
    }

    public void setCostoReserva(DTCostoBase costoReserva) {
        this.costoReserva = costoReserva;
    }

    public List<Pasaje> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Pasaje> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoReserva.setCostoTotal(costoTotal);
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public abstract int calcularCosto();}


