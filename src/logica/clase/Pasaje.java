package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.TipoAsiento;

public class Pasaje {
    //Atributos
    private Cliente pasajero;
    private TipoAsiento tipoAsiento;
    private int costoPasaje;
    //Relaciones
    private Reserva reserva;

    //Constructor
    public Pasaje(Cliente pasajero, Reserva reserva, TipoAsiento tipoAsiento) {
        this.pasajero = pasajero;
        this.reserva = reserva;
        this.tipoAsiento = tipoAsiento;
    }

    // getters y setters

    public Cliente getPasajero() {
        return pasajero;
    }

    public void setPasajero(Cliente pasajero) {
        this.pasajero = pasajero;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public int getCostoPasaje() {
        return costoPasaje;
    }

    public void setCostoPasaje(int costoPasaje) {
        this.costoPasaje = costoPasaje;
    }
}
