package logica.DataTypes;

public class DTReserva {
    private DTFecha fechaReserva;
    private CostoBase costoReserva;

    public DTReserva(DTFecha fechaReserva, CostoBase costoReserva) {
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;
    }

    // Getters

    public DTFecha getFechaReserva() {
        return this.fechaReserva;
    }

    public CostoBase getCostoReserva() {
        return this.costoReserva;
    }

    // Setters

}
