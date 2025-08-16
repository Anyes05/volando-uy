package logica.clase;

import logica.DataTypes.DTCliente;

public class Pasaje {
    //Atributos
    private DTCliente pasajero;
    private int cantidadEquipajeExtra;

    //Relaciones
    private Reserva reserva;

    //Constructor
    public Pasaje(DTCliente pasajero, int cantidadEquipajeExtra, Reserva reserva) {
        this.pasajero = pasajero;
        this.cantidadEquipajeExtra = cantidadEquipajeExtra;
        this.reserva = reserva;
    }

    // getters y setters

    public DTCliente getPasajero() {
        return pasajero;
    }

    public void setPasajero(DTCliente pasajero) {
        this.pasajero = pasajero;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public int getCantidadEquipajeExtra() {
        return cantidadEquipajeExtra;
    }

    public void setCantidadEquipajeExtra(int cantidadEquipajeExtra) {
        this.cantidadEquipajeExtra = cantidadEquipajeExtra;
    }
}
