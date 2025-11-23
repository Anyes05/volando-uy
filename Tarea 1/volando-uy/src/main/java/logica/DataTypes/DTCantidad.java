package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCantidad", propOrder = {
    "id",
    "cant",
    "tipoAsiento",
    "rutaVueloNombre"
})
public class DTCantidad {
    private Long id;
    private int cant;
    private TipoAsiento tipoAsiento;
    private String rutaVueloNombre; // Solo el nombre de la ruta, no la entidad completa

    // Constructor sin parámetros requerido por JAXB
    public DTCantidad() {
    }

    public DTCantidad(int cant, TipoAsiento tipoAsiento, String rutaVueloNombre) {
        this.cant = cant;
        this.tipoAsiento = tipoAsiento;
        this.rutaVueloNombre = rutaVueloNombre;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public String getRutaVueloNombre() {
        return rutaVueloNombre;
    }

    public void setRutaVueloNombre(String rutaVueloNombre) {
        this.rutaVueloNombre = rutaVueloNombre;
    }
}

