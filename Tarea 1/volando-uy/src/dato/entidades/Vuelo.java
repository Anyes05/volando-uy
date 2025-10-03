package dato.entidades;

import jakarta.persistence.*;

import java.util.List;

import logica.DataTypes.DTFecha;
import dato.converter.DTFechaConverter;
import logica.DataTypes.DTHora;
import dato.converter.DTHoraConverter;

import java.util.ArrayList;

@Entity
@Table(name = "vuelos")
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @Column(nullable = false, unique = true)
    private String nombre;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaVuelo;

    @Convert(converter = DTHoraConverter.class)
    private DTHora horaVuelo;

    @Convert(converter = DTHoraConverter.class)
    private DTHora duracion;

    @Column(nullable = false)
    private int asientosMaxTurista;

    @Column(nullable = false)
    private int asientosMaxEjecutivo;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaAlta;

    // Relacion con rutaVuelo
    // en clase/rutaVuelo, lo tenemos como una lista, pero en realidad seria así, no?: un vuelo pertenece a una sola ruta
    @ManyToOne
    @JoinColumn(name = "ruta_vuelo_id")
    private RutaVuelo rutaVuelo;

    //Relacion con reserva
    @OneToMany(mappedBy = "vuelo", fetch = FetchType.EAGER)
    private List<Reserva> reserva;

    // La relación con CompraComun se maneja a través de Reserva (herencia)

    // CONSTRUCTOR
    public Vuelo() {
        this.rutaVuelo = null;
        this.reserva = new ArrayList<>();
    }

    public Vuelo(String nombre, DTFecha fechaVuelo, DTHora horaVuelo, DTHora duracion, int asientosMaxTurista, int asientosMaxEjecutivo, DTFecha fechaAlta) {
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.duracion = duracion;
        this.asientosMaxTurista = asientosMaxTurista;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;

        this.rutaVuelo = null;
        this.reserva = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public DTFecha getFechaVuelo() {
        return fechaVuelo;
    }

    public void setFechaVuelo(DTFecha fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public DTHora getHoraVuelo() {
        return horaVuelo;
    }

    public void setHoraVuelo(DTHora horaVuelo) {
        this.horaVuelo = horaVuelo;
    }

    public DTHora getDuracion() {
        return duracion;
    }

    public void setDuracion(DTHora duracion) {
        this.duracion = duracion;
    }

    public int getAsientosMaxTurista() {
        return asientosMaxTurista;
    }

    public void setAsientosMaxTurista(int asientosMaxTurista) {
        this.asientosMaxTurista = asientosMaxTurista;
    }

    public int getAsientosMaxEjecutivo() {
        return asientosMaxEjecutivo;
    }

    public void setAsientosMaxEjecutivo(int asientosMaxEjecutivo) {
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public RutaVuelo getRutaVuelo() {
        return rutaVuelo;
    }

    public void setRutaVuelo(RutaVuelo rutaVuelo) {
        this.rutaVuelo = rutaVuelo;
    }

    public List<Reserva> getReserva() {
        return reserva;
    }

    public void setReserva(List<Reserva> reserva) {
        this.reserva = reserva;
    }
}
