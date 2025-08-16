package logica.DataTypes;

public class DTUsuario {
    private String nickname;
    private String nombre;
    private String correo;

    public  DTUsuario(String nickname, String nombre, String correo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
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

    // Setters

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
