package dato.entidades;

import dato.converter.DTFechaConverter;
import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(nullable = false)
    private String apellido;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaNacimiento;

    @Column(nullable = true)
    private String nacionalidad;

    private int cantidadPaquetes;

    @Enumerated(EnumType.STRING)
    private TipoDoc tipoDoc;

    @Column(nullable = false, unique = true)
    private String numeroDocumento;

    // Relación con Reserva
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente() {}

    public Cliente(String nickname, String nombre, String correo, String apellido, DTFecha fechaNacimiento,
                   String nacionalidad, TipoDoc tipoDoc, String numeroDocumento) {
        super(nickname, nombre, correo);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDoc = tipoDoc;
        this.numeroDocumento = numeroDocumento;
    }

    // Getters y Setters
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public DTFecha getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(DTFecha fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public TipoDoc getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(TipoDoc tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    // Helper methods para mantener sincronía en la relación
    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setCliente(this);
    }

//    public void removeReserva(Reserva reserva) {
//        reservas.remove(reserva);
//        reserva.setCliente(null);
//    }
    public void incrementarCantidadPaquetes() {
        this.cantidadPaquetes++;
    }
}
