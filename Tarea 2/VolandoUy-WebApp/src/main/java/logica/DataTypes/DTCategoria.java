package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCategoria", propOrder = {
    "nombre"
})
public class DTCategoria {
    private String nombre;

    // Constructor sin parámetros requerido por JAXB
    public DTCategoria() {
    }

    public DTCategoria(String nombre){
        this.nombre=nombre;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    //Setter para JAXB
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
