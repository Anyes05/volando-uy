package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTCiudad", propOrder = {
    "nombre",
    "pais"
})
public class DTCiudad {
    private String nombre;
    private String pais;

    // Constructor sin argumentos requerido por JAXB
    public DTCiudad() {
    }

    public DTCiudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    // Setters para JAXB
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public String toString() {
        return nombre + ", " + pais;
    }


}
