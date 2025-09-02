package dato.entidades;

import jakarta.persistence.*;
import java.util.List;
import logica.DataTypes.DTCliente;
import logica.DataTypes.TipoAsiento;

@Entity
@Table(name = "pasajes")
public class Pasaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @ManyToOne // Preguntarle a facu bien como sería
    private Cliente pasajero;

    @Enumerated(EnumType.STRING) // me dice que el enum es string para guardar correctamente en la base de datos
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private int costoPasaje;

    // Relacion con reserva - preguntaerle a facu
    @ManyToOne
    private Reserva reserva;

    public Pasaje() { // Constuctor vacío
    }

    public Pasaje(Cliente pasajero, Reserva reserva, TipoAsiento tipoAsiento) {
        this.pasajero = pasajero;
        this.reserva = reserva;
        this.tipoAsiento = tipoAsiento;
    }

    // Getter y Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getPasajero() { return pasajero; }
    public void setPasajero(Cliente pasajero) { this.pasajero = pasajero; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public TipoAsiento getTipoAsiento() { return tipoAsiento; }
    public void setTipoAsiento(TipoAsiento tipoAsiento) { this.tipoAsiento = tipoAsiento; }

    public int getCostoPasaje() { return costoPasaje; }
    public void setCostoPasaje(int costoPasaje) { this.costoPasaje = costoPasaje; }
}
