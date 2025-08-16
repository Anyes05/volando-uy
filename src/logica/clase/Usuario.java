package logica.clase;

public class Usuario {
    private String nickname;
    private String nombre;
    private String correo;

    public Usuario(String nickname, String nombre, String correo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
    }

    // getters y setters

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
}
