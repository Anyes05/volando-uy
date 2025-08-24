package dato.entidades;

import jakarta.persistence.*;
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

    // Relacion con RutaVuelo
    @OneToMany(mappedBy = "cantidad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RutaVuelo> rutasVuelos;

    // CONSTRUCTOR
    public Cantidad() {
        this.rutasVuelos = new ArrayList<>();
    }

    public Cantidad(int cant) {
        this.cant = cant;
        this.rutasVuelos = new ArrayList<>();
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public int getCant() {return cant;}

    public void setCant(int cant) {this.cant = cant;}

    public List<RutaVuelo> getRutasVuelos() {return rutasVuelos;}

    public void setRutasVuelos(List<RutaVuelo> rutasVuelos) {this.rutasVuelos = rutasVuelos;}
}
