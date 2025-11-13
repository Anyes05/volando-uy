package dato.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "seguidores",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seguidor_id", "seguido_id"})
)
public class Seguidores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seguidor_id")
    private Usuario seguidor; // Puede ser Cliente o Aerolínea

    @ManyToOne(optional = false)
    @JoinColumn(name = "seguido_id")
    private Usuario seguido; // Puede ser Cliente o Aerolínea

    // 🔹 Constructor
    public Seguidores() {
    }

    // 🔹 Constructor con parámetros
    public Seguidores(Usuario seguidor, Usuario seguido) {
        this.seguidor = seguidor;
        this.seguido = seguido;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Usuario getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(Usuario seguidor) {
        this.seguidor = seguidor;
    }

    public Usuario getSeguido() {
        return seguido;
    }

    public void setSeguido(Usuario seguido) {
        this.seguido = seguido;
    }
}
