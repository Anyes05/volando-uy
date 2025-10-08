package logica.DataTypes;

public class DTUsuario {
    private String nickname;
    private String nombre;
    private String correo;
    private byte[] foto;


    public DTUsuario(String nickname, String nombre, String correo, byte[] foto) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
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


    // MÉTODOS
    @Override
    public String toString() {
        return "Nickname= " + nickname + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo;
    }

}
