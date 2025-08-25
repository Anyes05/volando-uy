package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compraComun")
public class CompraComun extends Reserva{
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private int equipajeExtra;

    // Relacion con vuelo
    @OneToMany(mappedBy = "compraComun")
    private List<Vuelo> vuelos = new ArrayList<>();

    //CONSTRUCTORES
    public CompraComun() {}

    public CompraComun(Cliente cliente, DTFecha fechaReserva, TipoAsiento tipoAsiento, int equipajeExtra) {
        super(cliente, fechaReserva);
        this.tipoAsiento= tipoAsiento;
        this.equipajeExtra = equipajeExtra;
    }

    //GETTERS Y SETTERS
    public TipoAsiento getTipoAsiento() {return tipoAsiento;}
    public void setTipoAsiento(TipoAsiento tipoAsiento) {this.tipoAsiento = tipoAsiento;}

    public int getEquipajeExtra() {return equipajeExtra;}
    public void setEquipajeExtra(int equipajeExtra) {this.equipajeExtra = equipajeExtra;}

    public List<Vuelo> getVuelos() {return vuelos;}
    public void setVuelos(List<Vuelo> vuelos) {this.vuelos = vuelos;}

    //no se si va
    //@Override
    //    public int calcularCosto() {
    //        return 0;
    //    }

}
