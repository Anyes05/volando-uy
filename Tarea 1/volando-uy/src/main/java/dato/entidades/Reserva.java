package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.TipoAsiento;

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

    @Column(nullable = true)
    private boolean checkInRealizado = false;

    @Column(nullable = false)
    private DTFecha fechaReserva;

    @Column(nullable = true) // Temporalmente nullable para permitir guardar primero
    @Convert(converter = dato.converter.DTCostoBaseConverter.class)
    private DTCostoBase costoReserva;

    @OneToMany(mappedBy = "reserva", fetch = FetchType.EAGER)
    private List<dato.entidades.Pasaje> pasajeros = new ArrayList<>();

    // Relacion con Vuelo
    @ManyToOne
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    // Constructores
    public Reserva() {
    }

    public Reserva(Cliente cliente, DTFecha fechaReserva) {
        this.cliente = cliente;
        this.fechaReserva = fechaReserva;
        this.checkInRealizado = false;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public DTFecha getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(DTFecha fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public DTCostoBase getCostoReserva() {
        return costoReserva;
    }

    public void setCostoReserva(DTCostoBase costoReserva) {
        this.costoReserva = costoReserva;
    }

    public List<Pasaje> getPasajeros() {
        if (pasajeros == null) {
            pasajeros = new ArrayList<>();
        }
        return pasajeros;
    }

    public void setPasajeros(List<Pasaje> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public void setCostoTotal(float costoTotal) {
        if (this.costoReserva != null) {
            this.costoReserva.setCostoTotal(costoTotal);
        }
    }

    public boolean isCheckInRealizado() {
        return checkInRealizado;
    }

    public void setCheckInRealizado(boolean checkInRealizado) {
        this.checkInRealizado = checkInRealizado;
    }

    public TipoAsiento getTipoAsientoReserva() {
        if (this.pasajeros != null && !this.pasajeros.isEmpty()) {
            return this.pasajeros.get(0).getTipoAsiento();
        }
        return null;
    }
}
