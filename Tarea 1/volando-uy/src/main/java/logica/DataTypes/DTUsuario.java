package logica.DataTypes;
import java.util.List;

public class DTUsuario {
    private String nickname;
    private String nombre;
    private String correo;
    private byte[] foto;
    private List<String> seguidos;
    private String contrasena;


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
