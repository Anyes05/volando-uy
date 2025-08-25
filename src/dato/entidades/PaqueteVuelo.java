package dato.entidades;

import jakarta.persistence.*;
import java.util.List;
import logica.DataTypes.CostoBase;
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

    @Enumerated(EnumType.STRING) // me dice que el enum es string para guardar correctamente en la base de datos
    @Column(nullable = false)
    private TipoAsiento tipoAsiento;

    @Column(nullable = false)
    private int diasValidos;

    @Column(nullable = false)
    private float descuento;

    @Column(nullable = false)
    private CostoBase costoBase;

    @Column(nullable = false)
    private float costoTotal; // Lo dejé porque lo teníamos en la clase, pero costoTotal sería un calculado que no va en el constructor(?

    // Relacion con cantidad
    @ManyToOne
    private Cantidad cantidad;

    public PaqueteVuelo() { // Constuctor vacío
        this.cantidad = null;
    }

    public PaqueteVuelo(String nombre, String descripcion, TipoAsiento tipoAsiento,
                        int diasValidos, float descuento, CostoBase costoBase, float costoTotal) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoAsiento = tipoAsiento;
        this.diasValidos = diasValidos;
        this.descuento = descuento;
        this.costoBase = costoBase;
        this.costoTotal = costoTotal;
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
    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }
    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }
    public int getDiasValidos() {
        return diasValidos;
    }
    public void setDiasValidos(int diasValidos) {
        this.diasValidos = diasValidos;
    }

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) {
        this.descuento = descuento; }

    public CostoBase getCostoBase() { return costoBase; }
    public void setCostoBase(CostoBase costoBase) { this.costoBase = costoBase; }
    public float getCostoTotal() { return costoTotal; }
    public void setCostoTotal(float costoTotal) { this.costoTotal = costoTotal;}
}
