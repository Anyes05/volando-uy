package dato.entidades;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @Column(nullable = false, unique = true)
    private String nombre;

    //Capaz nos conviene que sea muchos a muchos, actualmente está linkeado así.
    @ManyToMany (mappedBy = "categorias")
    private List <RutaVuelo> rutas;

    // Como lo tenemos segun el Dcd:
    //@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<RutaVuelo> rutas; // no se si esto es necesario, por lo menos en el DCD esto no está. Lo dejo por si alguien lo está utilizando en alguna función
                                   // sino lo sacamos
    // Constructores
    public Categoria() {}  // Obligatorio para JPA

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<RutaVuelo> getRutas() { return rutas; }
    public void setRutas(List<RutaVuelo> rutas) { this.rutas = rutas; }

}
