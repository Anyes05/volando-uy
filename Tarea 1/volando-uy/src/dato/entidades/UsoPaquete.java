package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

@Entity
@Table(name = "uso_paquete")
public class UsoPaquete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "compra_paquete_id", nullable = false)
    private CompraPaquete compraPaquete;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vuelo_id", nullable = false)
    private Vuelo vuelo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cantidad_id", nullable = false)
    private Cantidad cantidad;

    @Column(nullable = false)
    private int cantidadUsada;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private DTFecha fechaUso;

    public UsoPaquete() {
    }

    public UsoPaquete(CompraPaquete compraPaquete, Vuelo vuelo, Cantidad cantidad, int cantidadUsada, TipoAsiento tipoAsiento, DTFecha fechaUso) {
        this.compraPaquete = compraPaquete;
        this.vuelo = vuelo;
        this.cantidad = cantidad;
        this.cantidadUsada = cantidadUsada;
        this.tipoAsiento = tipoAsiento;
        this.fechaUso = fechaUso;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompraPaquete getCompraPaquete() {
        return compraPaquete;
    }

    public void setCompraPaquete(CompraPaquete compraPaquete) {
        this.compraPaquete = compraPaquete;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Cantidad getCantidad() {
        return cantidad;
    }

    public void setCantidad(Cantidad cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public DTFecha getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(DTFecha fechaUso) {
        this.fechaUso = fechaUso;
    }
}
