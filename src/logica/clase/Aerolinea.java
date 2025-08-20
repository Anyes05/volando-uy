package logica.clase;
import logica.DataTypes.DTRutaVuelo;
import logica.DataTypes.DTUsuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Aerolinea extends Usuario {
    // Atributos
    private String descripcion;
    private String linkSitioWeb;
    private List<RutaVuelo> rutasVuelo;

    public Aerolinea(String nickname, String nombre, String correo, String descripcion, String linkSitioWeb) {
        super(nickname, nombre, correo);
        this.descripcion = descripcion;
        this.linkSitioWeb = linkSitioWeb;
        this.rutasVuelo = new ArrayList<>();
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

    public List<RutaVuelo> getRutasVuelo() {
        return rutasVuelo;
    }
}
