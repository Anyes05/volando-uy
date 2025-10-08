package logica.DataTypes;

import java.util.List;

public class DTAerolinea extends DTUsuario {
    private String descripcion;
    private String linkSitioWeb;
    private List<DTRutaVuelo> rutasVuelo;

    public DTAerolinea(String nickname, String nombre, String correo, String descripcion, String linksitioweb, List<DTRutaVuelo> rutasVuelo, byte[] foto, String contrasena) {
        super(nickname, nombre, correo, foto, contrasena);
        this.descripcion = descripcion;
        this.linkSitioWeb = linksitioweb;
        this.rutasVuelo = rutasVuelo;
    }

    // Getters
    public String getDescripcion() {
        return this.descripcion;
    }

    public String getLinkSitioWeb() {
        return this.linkSitioWeb;
    }

    public List<DTRutaVuelo> getRutasVuelo() {
        return this.rutasVuelo;
    }

    // MÉTODOS
    @Override
    public String toString() {
        return super.toString() +
                ", Descripción: " + descripcion +
                ", Sitio Web: " + linkSitioWeb;
    }


}
