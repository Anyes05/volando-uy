package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;

import java.util.ArrayList;
import java.util.List;

public abstract class Reserva {
    //Atributos
    private DTCliente cliente;
    private DTFecha fechaReserva;
    private int costoReserva;

    private List<DTCliente> pasajeros;

    // Constructor
    public Reserva(DTCliente cliente, DTFecha fechaReserva, int costoReserva) {
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;

        this.pasajeros = new ArrayList<>();
    }

    // getters y setters


    public DTCliente getCliente() {
        return cliente;
    }

    public void setCliente(DTCliente cliente) {
        this.cliente = cliente;
    }

    public DTFecha getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(DTFecha fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public int getCostoReserva() {
        return costoReserva;
    }

    public void setCostoReserva(int costoReserva) {
        this.costoReserva = costoReserva;
    }

    public List<DTCliente> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<DTCliente> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public abstract int calcularCosto();

}
