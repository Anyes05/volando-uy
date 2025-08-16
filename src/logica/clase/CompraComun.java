package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraComun extends Reserva{
    private TipoAsiento tipoAsiento;

    public CompraComun(DTCliente cliente, DTFecha fechaReserva, int costoReserva, TipoAsiento tipoAsiento) {
        super(cliente, fechaReserva, costoReserva);

        this.tipoAsiento = tipoAsiento;
    }

    // getters y setters

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }
}
