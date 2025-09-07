package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.TipoAsiento;

import java.util.List;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cantidad")
public class Cantidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clave primaria

    @Column(nullable = false)
    private int cant;

    // Relacion con rutaVuelo
    @ManyToOne
    @JoinColumn(name = "ruta_vuelo_id")
    private RutaVuelo rutaVuelo;

    // Relacion con paqueteVuelo
    @ManyToOne
    @JoinColumn(name = "paquete_vuelo_id")
    private PaqueteVuelo paqueteVuelo;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = true)
    private TipoAsiento tipoAsiento;

    // CONSTRUCTOR
    public Cantidad() {
    }

    public Cantidad(int cant) {
        this.cant = cant;
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public int getCant() {return cant;}

    public void setCant(int cant) {this.cant = cant;}

    public RutaVuelo getRutaVuelo() {
        return rutaVuelo;
    }

    public void setRutaVuelo(RutaVuelo rutaVuelo) {
        this.rutaVuelo = rutaVuelo;
    }

    public PaqueteVuelo getPaqueteVuelo() {
        return paqueteVuelo;
    }

    public void setPaqueteVuelo(PaqueteVuelo paqueteVuelo) {
        this.paqueteVuelo = paqueteVuelo;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    //    public List<RutaVuelo> getRutasVuelos() {return rutasVuelos;}
//
//    public void setRutasVuelos(List<RutaVuelo> rutasVuelos) {this.rutasVuelos = rutasVuelos;}
}
