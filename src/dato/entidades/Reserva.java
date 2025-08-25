package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTCliente;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTPasajes;
import logica.DataTypes.TipoAsiento;
import logica.DataTypes.CostoBase;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Cliente cliente;  // Relacion con Cliente

    @Column(nullable = false)
    private DTFecha fechaReserva;

    @Column(nullable = false)
    private CostoBase costoReserva;

    @OneToMany
    private List<DTPasajes> pasajeros = new ArrayList<>();

    // Relacion con Vuelo
    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    // Constructores
    public Reserva() {}

    public Reserva(Cliente cliente, DTFecha fechaReserva) {
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
    }
    // getters y setters
    public Long getId() { return id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public DTFecha getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(DTFecha fechaReserva) { this.fechaReserva = fechaReserva; }

    public CostoBase getCostoReserva() { return costoReserva; }
    public void setCostoReserva(CostoBase costoReserva) { this.costoReserva = costoReserva; }

    public List<DTPasajes> getPasajeros() { return pasajeros; }
    public void setPasajeros(List<DTPasajes> pasajeros) { this.pasajeros = pasajeros; }

    public Vuelo getVuelo() { return vuelo; }
    public void setVuelo(Vuelo vuelo) { this.vuelo = vuelo; }


    // esto seria para la relacion con cliente que me vueleve LOCA, primero hay que crear el atributo privado, despues hay que
    // descomentar la parte que esta en Cliente que sirve como para "controlar" que la relacion estable jaja
    //    public Cliente getCliente() { return cliente; }
    //    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
