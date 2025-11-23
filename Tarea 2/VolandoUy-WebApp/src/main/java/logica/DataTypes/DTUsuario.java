package logica.DataTypes;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtUsuario", propOrder = {
    "nickname",
    "nombre",
    "correo",
    "foto",
    "seguidos",
    "contrasena"
})
public class DTUsuario {
    private String nickname;
    private String nombre;
    private String correo;
    private byte[] foto;
    private List<String> seguidos;
    private String contrasena;

    // Constructor sin parámetros requerido por JAXB para serialización/deserialización XML
    public DTUsuario() {
        // Constructor vacío para JAXB
    }

    public DTUsuario(String nickname, String nombre, String correo, byte[] foto, String contrasena) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
        this.contrasena = contrasena;
    }

    // Getters
    public String getNickname() {
        return this.nickname;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCorreo() {
        return this.correo;
    }

    public byte[] getFoto() { return this.foto; }

    public String getContrasena() { return this.contrasena; }


    // MÉTODOS
    @Override
    public String toString() {
        return "Nickname= " + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo;
    }

}
