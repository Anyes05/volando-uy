package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "compraPaquete")
public class CompraPaquete extends Reserva{
    @Column(nullable = false)
    private DTFecha vencimiento;

    @OneToMany (mappedBy = "compraPaquete")
    private List<PaqueteVuelo> paquetesVuelo =new ArrayList<>();
    // private PaqueteVuelo paqueteVuelo; Asi esta en clase/compraPaquete, pero segun el dcd seria uno a muchos y deberia de haber una lista(?

    public CompraPaquete() {}

    public CompraPaquete(Cliente cliente, DTFecha fechaReserva, DTFecha vencimiento) {
        super(cliente, fechaReserva);
        this.vencimiento = vencimiento;
    }

    public DTFecha getVencimiento() {return vencimiento;}

    public void setVencimiento(DTFecha vencimiento) {this.vencimiento = vencimiento;}

    public List<PaqueteVuelo> getPaquetesVuelo() {return paquetesVuelo;}

    public void setPaqueteVuelo(List <PaqueteVuelo> paquetesVuelo) {this.paquetesVuelo = paquetesVuelo;}

    // No se bien que pasa con el otro metodo
    // @Override
    //    public int calcularCosto() {
    //        return 0;
    //    }

}
