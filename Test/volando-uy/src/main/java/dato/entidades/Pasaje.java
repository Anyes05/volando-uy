package dato.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import logica.DataTypes.TipoAsiento;

@Entity
@Table(name = "pasajes")
public class Pasaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente pasajero;

    @Enumerated(EnumType.ORDINAL) // Usar ordinal para compatibilidad con base de datos existente
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private int costoPasaje;

    // Campos para nombre y apellido del pasajero (pueden ser diferentes al cliente principal)
    @Column(nullable = false)
    private String nombrePasajero;

    @Column(nullable = false)
    private String apellidoPasajero;

    // Relacion con reserva
    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Pasaje() { // Constuctor vacío
    }

    public Pasaje(Cliente pasajero, Reserva reserva, TipoAsiento tipoAsiento) {
        this.pasajero = pasajero;
        this.reserva = reserva;
        this.tipoAsiento = tipoAsiento;
        // Establecer nombre y apellido del cliente como pasajero
//        this.nombrePasajero = pasajero.getNombre();
//        this.apellidoPasajero = pasajero.getApellido();
    }

    // Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getPasajero() {
        return pasajero;
    }

    public void setPasajero(Cliente pasajero) {
        this.pasajero = pasajero;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public int getCostoPasaje() {
        return costoPasaje;
    }

    public void setCostoPasaje(int costoPasaje) {
        this.costoPasaje = costoPasaje;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public String getApellidoPasajero() {
        return apellidoPasajero;
    }

    public void setApellidoPasajero(String apellidoPasajero) {
        this.apellidoPasajero = apellidoPasajero;
    }
}
