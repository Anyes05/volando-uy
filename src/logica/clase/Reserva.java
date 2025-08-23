package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTPasajes;
import logica.DataTypes.TipoAsiento;
import logica.DataTypes.CostoBase;

import java.util.ArrayList;
import java.util.List;

public abstract class Reserva {
    //Atributos
    private Cliente cliente;
    private DTFecha fechaReserva;
    private CostoBase costoReserva;
    private List<Pasaje> pasajeros;

    // Constructor
    public Reserva(Cliente cliente, DTFecha fechaReserva) {
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;

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

    public CostoBase getCostoReserva() {
        return costoReserva;
    }

    public void setCostoReserva(CostoBase costoReserva) {
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



    public abstract int calcularCosto();}


