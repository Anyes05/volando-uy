package logica.DataTypes;

public class DTReserva {
    private DTFecha fechaReserva;
    private float costoReserva;

    public DTReserva(DTFecha fechaReserva, float costoReserva) {
        this.fechaReserva = fechaReserva;
        this.costoReserva = costoReserva;
    }

    // Getters

    public DTFecha getFechaReserva() {
        return this.fechaReserva;
    }

    public float getCostoReserva() {
        return this.costoReserva;
    }

    // Setters

}
