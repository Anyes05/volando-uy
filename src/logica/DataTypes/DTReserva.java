package logica.DataTypes;

public class DTReserva {
    private DTFecha fechaReserva;
    private DTCostoBase costoReserva;

    public DTReserva(DTFecha fechaReserva, DTCostoBase costoReserva) {
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;
    }

    // Getters

    public DTFecha getFechaReserva() {
        return this.fechaReserva;
    }

    public DTCostoBase getCostoReserva() {
        return this.costoReserva;
    }

    // Setters

}
