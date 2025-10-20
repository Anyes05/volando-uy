package dato.entidades;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ManyToMany(mappedBy = "categorias", fetch = FetchType.EAGER)
    private List<RutaVuelo> rutas;

    // Constructores
    public Categoria() {
    }  // Obligatorio para JPA

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonIgnore
    public List<RutaVuelo> getRutas() {
        return rutas;
    }

    public void setRutas(List<RutaVuelo> rutas) {
        this.rutas = rutas;
    }

}
