package DataTypes;

public class DTCompraComun extends DTReserva {
    private TipoAsiento tipoAsiento;

    public  DTCompraComun(DTFecha fechaReserva, float costoReserva, TipoAsiento tipoAsiento) {
        super(fechaReserva, costoReserva);
        this.tipoAsiento = tipoAsiento;
    }

    // Getters

    public TipoAsiento getTipoAsiento(TipoAsiento tipoAsiento) {
        return this.tipoAsiento;
    }

    // Setters

    public void setTipoAsiento(TipoAsiento tipoAsiento){
        this.tipoAsiento = tipoAsiento;
    }
}
