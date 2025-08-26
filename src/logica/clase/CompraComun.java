package logica.clase;

import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

public class CompraComun extends Reserva{
    private Vuelo vuelo;
    private int equipajeExtra;

    public CompraComun(Cliente cliente, DTFecha fechaReserva, int equipajeExtra, TipoAsiento tipoAsiento) {
        super(cliente, fechaReserva, tipoAsiento);
        this.equipajeExtra = equipajeExtra;
        this.vuelo = null; // Inicializar vuelo como null, se puede asignar más adelante
    }

    // getters y setters

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
