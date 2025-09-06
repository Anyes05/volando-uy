package logica.DataTypes;

public class DTCompraComun extends DTReserva {
    private TipoAsiento tipoAsiento;

    public  DTCompraComun(DTFecha fechaReserva, DTCostoBase costoReserva, TipoAsiento tipoAsiento) {
        super(fechaReserva, costoReserva);
        this.tipoAsiento = tipoAsiento;
    }

    // Getters

    public TipoAsiento getTipoAsiento() {
        return this.tipoAsiento;
    }

    // Setters

}
