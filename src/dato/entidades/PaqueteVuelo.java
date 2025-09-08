package dato.entidades;

import dato.converter.DTFechaConverter;
import jakarta.persistence.*;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "paquetesVuelo")
public class PaqueteVuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private int diasValidos;

    @Column(nullable = false)
    private float descuento;

    private boolean comprado;

    @Column(nullable = false)
    private float costoTotal;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaAlta;

    @OneToMany(mappedBy = "paqueteVuelo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Cantidad> cantidades;

    //Relacion con compraPaquete
    @ManyToOne
    @JoinColumn(name = "compraPaquete_id")
    private CompraPaquete compraPaquete;

    // Constructores

    public PaqueteVuelo() { // Constuctor vacío

    }

    public PaqueteVuelo(String nombrePaquete, String descripcion,/*TipoAsiento tipoAsiento*/ int diasValidos, float descuento, DTFecha fechaAlta) {
        this.nombre = nombrePaquete;
        this.descripcion = descripcion;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.fechaAlta = fechaAlta;
        this.costoTotal = 0;
        this.cantidades = new ArrayList<>();
        this.comprado = false;
    }

    // Getter y Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DTFecha getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(DTFecha fechaAlta) { this.fechaAlta = fechaAlta; }

    public int getDiasValidos() {
        return diasValidos;
    }
    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) {
        this.descuento = descuento; }

    public float getCostoTotal() { return costoTotal; }

    public void setCostoTotal(float costoTotal) { this.costoTotal = costoTotal; }

    public void addCantidad(Cantidad c) {
        cantidades.add(c);
        c.setPaqueteVuelo(this);   // importantísimo: setea el lado propietario
    }
    public List<Cantidad> getCantidad() { return cantidades; }
    public void setCantidad(List<Cantidad> cantidades) { this.cantidades = cantidades;  }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public CompraPaquete getCompraPaquete() {
        return compraPaquete;
    }

    public void setCompraPaquete(CompraPaquete compraPaquete) {
        this.compraPaquete = compraPaquete;
    }
}
