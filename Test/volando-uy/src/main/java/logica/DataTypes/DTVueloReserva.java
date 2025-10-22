package logica.DataTypes;

public class DTVueloReserva {// Armar según corresponda
    private DTVuelo vuelo;
    private DTReserva reserva;

    public DTVueloReserva(DTVuelo vuelo, DTReserva reserva) {
        this.vuelo = vuelo;
        this.reserva = reserva;

    }

    public DTVuelo getVuelo() {
        return vuelo;
    }

    public DTReserva getReserva() {
        return reserva;
    }

    public String toString() {
        return "ID: " + reserva.getId() + "     -     Cliente: " + reserva.getNickname();
    }
}
