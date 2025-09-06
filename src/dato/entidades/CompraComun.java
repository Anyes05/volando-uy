package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compraComun")
public class CompraComun extends Reserva{
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private int equipajeExtra;

    // La relación con vuelo se hereda de Reserva

    //CONSTRUCTORES
    public CompraComun() {}

    public CompraComun(Cliente cliente, DTFecha fechaReserva, TipoAsiento tipoAsiento, int equipajeExtra) {
        super(cliente, fechaReserva);
        this.tipoAsiento= tipoAsiento;
        this.equipajeExtra = equipajeExtra;
        
        // Inicializar costoReserva con valores por defecto usando el setter
        this.setCostoReserva(new logica.DataTypes.DTCostoBase(0, 0, 0));
    }

    //GETTERS Y SETTERS
    public TipoAsiento getTipoAsiento() {return tipoAsiento;}
    public void setTipoAsiento(TipoAsiento tipoAsiento) {this.tipoAsiento = tipoAsiento;}

    public int getEquipajeExtra() {return equipajeExtra;}
    public void setEquipajeExtra(int equipajeExtra) {this.equipajeExtra = equipajeExtra;}

    // Los métodos getVuelo() y setVuelo() se heredan de Reserva

    //no se si va
    //@Override
    //    public int calcularCosto() {
    //        return 0;
    //    }

}
