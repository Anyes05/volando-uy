package dato.entidades;

import dato.converter.DTCostoBaseConverter;
import jakarta.persistence.*;
import java.util.List;

import logica.DataTypes.DTCostoBase;
import logica.DataTypes.DTFecha;
import dato.converter.DTFechaConverter;

import java.util.ArrayList;

@Entity
@Table(name = "rutasVuelo")
public class RutaVuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column
    @Convert(converter = DTCostoBaseConverter.class)
    private DTCostoBase costoBase;

    @Column
    private float costoEquipajeExtra;

    @Column
    private float costoBaseTurista;

    @Column
    private float costoBaseEjecutivo;

    @Convert(converter = DTFechaConverter.class)
    private DTFecha fechaAlta;

    // Relación con categoría
    @ManyToMany
    @JoinTable(
            name = "ruta_categoria",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    // Relación con vuelo
    @OneToMany(mappedBy = "rutaVuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vuelo> vuelos;

    // Relación con aerolínea
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "ruta_aerolinea",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "aerolinea_id")
    )
    private List<Aerolinea> aerolineas;

    // Relación con cantidad, hay que ponerlo de este lado también creo
    @ManyToOne
    @JoinColumn(name = "cantidad_id")
    private Cantidad cantidad;


    //relacion con ciudad
    @ManyToOne
    @JoinColumn(name = "ciudad_origen_id")
    private Ciudad ciudadOrigen;

    @ManyToOne
    @JoinColumn(name = "ciudad_destino_id")
    private Ciudad ciudadDestino;

    // Constructores
    public RutaVuelo() {}  // Obligatorio para JPA

    public RutaVuelo(String nombre, String descripcion, DTCostoBase costoBase, DTFecha fechaAlta) { // No sé por qué no estaba fechaAlta como atributo en clase/RutaVuelo, por las dudas esperar para ver como han ido implementando
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBase = costoBase;
        this.fechaAlta = fechaAlta;
        this.costoEquipajeExtra = costoBase.getCostoEquipajeExtra();
        this.costoBaseEjecutivo = costoBase.getCostoEjecutivo();
        this.costoBaseTurista = costoBase.getCostoTurista();

        this.categorias = new ArrayList<>();
        this.vuelos = new ArrayList<>();
        this.aerolineas = new ArrayList<>();

        this.ciudadDestino = null;
        this.ciudadOrigen = null;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public DTCostoBase getCostoBase() { return costoBase; }
    public void setCostoBase(DTCostoBase DTCostoBase) { this.costoBase = costoBase; }

    public DTFecha getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(DTFecha fechaAlta) { this.fechaAlta = fechaAlta; }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public Ciudad getCiudadOrigen() {
        return ciudadOrigen;
    }

    public void setCiudadOrigen(Ciudad ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public List<Categoria> getCategorias() { return categorias; }

    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias;}

    public List<Vuelo> getVuelos() { return vuelos; }

    public void setVuelos(List<Vuelo> vuelos) { this.vuelos = vuelos; }

    public List<Aerolinea> getAerolineas() { return aerolineas; }

    public void setAerolineas(List<Aerolinea> aerolineas) { this.aerolineas = aerolineas; }

    public Cantidad getCantidad() { return cantidad; }

    public void setCantidad(Cantidad cantidad) { this.cantidad = cantidad; }

    public float getCostoBaseTurista() {
        return costoBaseTurista;
    }

    public void setCostoBaseTurista(float costoBaseTurista) {
        this.costoBaseTurista = costoBaseTurista;
    }

    public float getCostoBaseEjecutivo() {
        return costoBaseEjecutivo;
    }

    public void setCostoBaseEjecutivo(float costoBaseEjecutivo) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
    }
}