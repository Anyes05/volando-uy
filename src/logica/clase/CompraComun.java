package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraComun extends Reserva{
    private Vuelo vuelo;
    private TipoAsiento tipoAsiento;
    private int equipajeExtra;

    public CompraComun(Cliente cliente, DTFecha fechaReserva, TipoAsiento tipoAsiento, int equipajeExtra) {
        super(cliente, fechaReserva);
        this.tipoAsiento = tipoAsiento;
        this.equipajeExtra = equipajeExtra;
        this.vuelo = null; // Inicializar vuelo como null, se puede asignar más adelante
    }

    // getters y setters

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public int getEquipajeExtra() {
        return equipajeExtra;
    }

    public void setEquipajeExtra(int equipajeExtra) {
        this.equipajeExtra = equipajeExtra;
    }


    @Override
    public int calcularCosto() {
        return 0;
    }



}
