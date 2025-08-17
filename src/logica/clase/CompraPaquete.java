package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;

public class CompraPaquete extends Reserva {
    private DTFecha vencimiento;

    public CompraPaquete(DTCliente cliente, DTFecha fechaReserva, int costoReserva, DTFecha vencimiento) {
        super(cliente, fechaReserva, costoReserva);

        this.vencimiento = vencimiento;
    }
    // getters y setters
    public DTFecha getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(DTFecha vencimiento) {
        this.vencimiento = vencimiento;
    }

    @Override
    public int calcularCosto() {
        return 0;
    }
}

