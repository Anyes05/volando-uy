package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTHora", propOrder = {
    "hora",
    "minutos"
})
public class DTHora {
    private int hora;
    private int minutos;

    // Constructor sin argumentos requerido por JAXB
    public DTHora() {
    }

    public DTHora(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    //Getters
    public int getHora() {
        return hora;
    }

    public int getMinutos() {
        return minutos;
    }

    // Setters para JAXB
    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hora, minutos);
    }
}
