package dato.entidades;

import dato.converter.DTFechaConverter;
import jakarta.persistence.*;
import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoAsiento;

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

//    @Column(nullable = false)
//    private DTCostoBase DTCostoBase;

//    @Column(nullable = false)
//    private float costoTotal; // Lo dejé porque lo teníamos en la clase, pero costoTotal sería un calculado que no va en el constructor(?

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaAlta;

    // Relacion con cantidad
    @ManyToOne
    private Cantidad cantidad;

    //Relacion con compraPaquete
    @ManyToOne
    @JoinColumn(name = "compraPaquete_id")
    private CompraPaquete compraPaquete;

    // Constructores

    public PaqueteVuelo() { // Constuctor vacío
        this.cantidad = null;
    }

    public PaqueteVuelo(String nombrePaquete, String descripcion, int diasValidos, float descuento, DTFecha fechaAlta) {
        this.nombre = nombrePaquete;
        this.descripcion = descripcion;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.fechaAlta = fechaAlta;
        this.cantidad = null;
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

//    public TipoAsiento getTipoAsiento() {
//        return tipoAsiento;
//    }
//    public void setTipoAsiento(TipoAsiento tipoAsiento) {
//        this.tipoAsiento = tipoAsiento;
//    }
    public int getDiasValidos() {
        return diasValidos;
    }
    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) {
        this.descuento = descuento; }

//    public DTCostoBase getCostoBase() { return DTCostoBase; }
//    public void setCostoBase(DTCostoBase DTCostoBase) { this.DTCostoBase = DTCostoBase; }
//    public float getCostoTotal() { return costoTotal; }
//    public void setCostoTotal(float costoTotal) { this.costoTotal = costoTotal;}

    public Cantidad getCantidad() { return cantidad; }
    public void setCantidad(Cantidad cantidad) { this.cantidad = cantidad;  }
}
