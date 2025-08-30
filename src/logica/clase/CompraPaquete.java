package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraPaquete extends Reserva {
    private DTFecha vencimiento;
    private PaqueteVuelo paqueteVuelo;

    public CompraPaquete(Cliente cliente, DTFecha fechaReserva, DTFecha vencimiento, TipoAsiento tipoAsiento) {
        super(cliente, fechaReserva, tipoAsiento);

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

