package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTCostoBase;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reservas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id") // columna FK en la tabla actual
    private Cliente cliente;

    @Column(nullable = false)
    private DTFecha fechaReserva;

    @Column(nullable = true) // Temporalmente nullable para permitir guardar primero
    @Convert(converter = dato.converter.DTCostoBaseConverter.class)
    private DTCostoBase costoReserva;

//    @Column(nullable = false)
//    private float costoTotal;

    @OneToMany(mappedBy = "reserva", fetch = FetchType.EAGER)
    private List<dato.entidades.Pasaje> pasajeros = new ArrayList<>();

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

    public DTCostoBase getCostoReserva() { return costoReserva; }
    public void setCostoReserva(DTCostoBase costoReserva) { this.costoReserva = costoReserva; }

    public List<Pasaje> getPasajeros() { 
        if (pasajeros == null) {
            pasajeros = new ArrayList<>();
        }
        return pasajeros; 
    }
    public void setPasajeros(List<Pasaje> pasajeros) { this.pasajeros = pasajeros; }

    public Vuelo getVuelo() { return vuelo; }
    public void setVuelo(Vuelo vuelo) { this.vuelo = vuelo; }

    public void setCostoTotal(float costoTotal) {
        if (this.costoReserva != null) {
            this.costoReserva.setCostoTotal(costoTotal);
        }
    }

//    public float getCostoTotal() {
//        return costoTotal;
//    }

// esto seria para la relacion con cliente que me vueleve LOCA, primero hay que crear el atributo privado, despues hay que
    // descomentar la parte que esta en Cliente que sirve como para "controlar" que la relacion estable jaja
    //    public Cliente getCliente() { return cliente; }
    //    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
