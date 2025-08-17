package logica.clase;
import logica.DataTypes.DTUsuario;

public class Aerolinea extends Usuario {
    // Atributos
    private String descripcion;
    private String linkSitioWeb;

    public Aerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
        super(nickname, nombre, correo);
        this.descripcion = descripcion;
        this.linkSitioWeb = linkSitioWeb;
    }

    // getters y setters

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLinkSitioWeb() {
        return linkSitioWeb;
    }

    public void setLinkSitioWeb(String linkSitioWeb) {
        this.linkSitioWeb = linkSitioWeb;
    }
}
