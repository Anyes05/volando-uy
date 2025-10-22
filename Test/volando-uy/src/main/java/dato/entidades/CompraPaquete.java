package dato.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import logica.DataTypes.DTFecha;

@Entity
@Table(name = "compraPaquete")
public class CompraPaquete extends Reserva {
    @Column(nullable = false)
    private DTFecha vencimiento;

    @ManyToOne(fetch = FetchType.EAGER)  // cada compra pertenece a un paquete
    @JoinColumn(name = "paquete_id", nullable = false)
    private PaqueteVuelo paqueteVuelo;


    //@OneToOne (mappedBy = "tipoAsiento")

//    @Enumerated(EnumType.STRING) // guarda "Turista" o "Ejecutivo" en la BD
//    @Column(nullable = false)
//    private TipoAsiento tipoAsiento;


    public CompraPaquete() {
    }

    public CompraPaquete(Cliente cliente, DTFecha fechaReserva, DTFecha vencimiento) {
        super(cliente, fechaReserva);
        this.vencimiento = vencimiento;
    }

    public DTFecha getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(DTFecha vencimiento) {
        this.vencimiento = vencimiento;
    }

    public PaqueteVuelo getPaqueteVuelo() {
        return this.paqueteVuelo;
    }

    public void setPaqueteVuelo(PaqueteVuelo paqueteVuelo) {
        this.paqueteVuelo = paqueteVuelo;
    }

//    public TipoAsiento getTipoAsiento() {return tipoAsiento;}
//
//    public void setTipoAsiento(TipoAsiento tipoAsiento) {this.tipoAsiento = tipoAsiento;}

    // No se bien que pasa con el otro metodo
    // @Override
    //    public int calcularCosto() {
    //        return 0;
    //    }

}
