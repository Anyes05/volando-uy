package dato.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // Clave primaria

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(name = "foto", columnDefinition = "bytea")
    private byte[] foto;

    @Column(nullable = false)
    private String contrasena;


    // Constructores
    public Usuario() {
    }  // Obligatorio para JPA

    public Usuario(String nickname, String nombre, String correo, byte[] foto, String contrasena) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.contrasena = contrasena;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFoto() { return this.foto; }

    public String getContrasena() { return this.contrasena; }

    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public void setFoto(byte[] foto) { this.foto = foto; }
}
