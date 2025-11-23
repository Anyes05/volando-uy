package logica.DataTypes;

import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtAerolinea", propOrder = {
    "descripcion",
    "linkSitioWeb",
    "rutasVuelo"
})
public class DTAerolinea extends DTUsuario {
    private String descripcion;
    private String linkSitioWeb;
    private List<DTRutaVuelo> rutasVuelo;

    // Constructor sin parámetros requerido por JAXB para serialización/deserialización XML
    public DTAerolinea() {
        super(); // Llama al constructor sin parámetros de DTUsuario
    }

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
