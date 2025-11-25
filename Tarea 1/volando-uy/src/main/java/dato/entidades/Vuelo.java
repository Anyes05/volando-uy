package dato.entidades;

import jakarta.persistence.*;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

import logica.DataTypes.DTFecha;
import dato.converter.DTFechaConverter;
import logica.DataTypes.DTHora;
import dato.converter.DTHoraConverter;
import logica.DataTypes.TipoAsiento;

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

    @Column(nullable = false)
    private int cantidadAsientosUsadosTurista;

    @Column(nullable = false)
    private int cantidadAsientosUsadosEjecutivo;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaAlta;

    @Column(name = "foto", columnDefinition = "bytea")
    private byte[] foto;

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

    public Vuelo(String nombre, DTFecha fechaVuelo, DTHora horaVuelo, DTHora duracion, int asientosMaxTurista, int asientosMaxEjecutivo, DTFecha fechaAlta, byte[] foto) {
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.duracion = duracion;
        this.asientosMaxTurista = asientosMaxTurista;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;
        this.foto = foto;
        this.cantidadAsientosUsadosTurista = asientosMaxEjecutivo;
        this.cantidadAsientosUsadosEjecutivo = 0;

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

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getCantidadAsientosUsadosTurista() {
        return cantidadAsientosUsadosTurista;
    }

    public void setCantidadAsientosUsadosTurista(int cantidadAsientosUsadosTurista) {
        this.cantidadAsientosUsadosTurista = cantidadAsientosUsadosTurista;
    }

    public int getCantidadAsientosUsadosEjecutivo() {
        return cantidadAsientosUsadosEjecutivo;
    }

    public void setCantidadAsientosUsadosEjecutivo(int cantidadAsientosUsadosEjecutivo) {
        this.cantidadAsientosUsadosEjecutivo = cantidadAsientosUsadosEjecutivo;
    }

    public void incrementarAsientosUsadosTurista(int cantidad) {
        this.cantidadAsientosUsadosTurista += cantidad;
    }

    public void incrementarAsientosUsadosEjecutivo(int cantidad) {
        this.cantidadAsientosUsadosEjecutivo += cantidad;
    }

    public boolean hayAsientosDisponiblesTurista(int cantidadSolicitada) {
        return (this.cantidadAsientosUsadosTurista + cantidadSolicitada) < this.asientosMaxTurista + this.asientosMaxEjecutivo;
    }

    public boolean hayAsientosDisponiblesEjecutivo(int cantidadSolicitada) {
        return (this.cantidadAsientosUsadosEjecutivo + cantidadSolicitada) < this.asientosMaxEjecutivo;
    }

}
