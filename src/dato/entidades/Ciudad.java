package dato.entidades;

import jakarta.persistence.*;
import logica.DataTypes.DTFecha;
import java.util.ArrayList;
import java.util.List;
import dato.converter.DTFechaConverter;

@Entity
@Table(name = "ciudades")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String pais;

    @OneToMany (mappedBy = "ciudad", cascade = CascadeType.ALL, orphanRemoval = true)
    // A tener en cuenta:
    // mappedBy indica que la relacion esta mapeada por el atributo ciudad en Aeropuerto.
    // cascade, todas las operaciones que se hagan sobre ciudad se aplican tambien a sus aeropuertos
    // orphanRemoval, si se elimina un aeropuerto de la lista de la ciudad, se elimina de la BD
    private List<Aeropuerto> aeropuertos = new ArrayList<>();

    @Column(nullable = false)
    @Convert(converter = DTFechaConverter.class) // En una Base de Datos no puedo guardar un DT, asi que necesito convertir a otro tipo de datos compatibles :)
    private DTFecha fechaAlta;

    // Constructores
    public Ciudad() {}  // Obligatorio para JPA

    public Ciudad(String nombre, String pais, DTFecha fechaAlta) {
        this.nombre = nombre;
        this.pais = pais;
        this.aeropuertos = new ArrayList<>();
        this.fechaAlta = fechaAlta;
    }

    // Getters y setters
    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public List<Aeropuerto> getAeropuertos() { return this.aeropuertos; }
    public void setAeropuertos(List<Aeropuerto> aeropuertos) { this.aeropuertos = aeropuertos; }

    public DTFecha getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(DTFecha fechaAlta) { this.fechaAlta = fechaAlta; }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuertos.add(aeropuerto);
        aeropuerto.setCiudad(this); // Asegura la relación bidireccional
    }

    public List<Aeropuerto> getAeropuertosList() {
        return aeropuertos;
    }
}
