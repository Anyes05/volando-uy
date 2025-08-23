package logica.DataTypes;

public class DTCompraComun extends DTReserva {
    private TipoAsiento tipoAsiento;

    public  DTCompraComun(DTFecha fechaReserva, CostoBase costoReserva, TipoAsiento tipoAsiento) {
        super(fechaReserva, costoReserva);
        this.tipoAsiento = tipoAsiento;
    }

    // Getters

    public TipoAsiento getTipoAsiento(TipoAsiento tipoAsiento) {
        return this.tipoAsiento;
    }

    // Setters

}
