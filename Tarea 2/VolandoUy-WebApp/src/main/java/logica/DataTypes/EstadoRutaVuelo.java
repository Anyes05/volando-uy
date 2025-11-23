package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "estadoRutaVuelo")
@XmlEnum
public enum EstadoRutaVuelo {
    INGRESADA("Ingresada"),
    CONFIRMADA("Confirmada"),
    FINALIZADA("Finalizada"),
    RECHAZADA("Rechazada");

    private final String descripcion;

    EstadoRutaVuelo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
