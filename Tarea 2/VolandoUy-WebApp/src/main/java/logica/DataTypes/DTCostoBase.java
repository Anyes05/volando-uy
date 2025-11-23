package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTCostoBase", propOrder = {
    "costoTurista",
    "costoEjecutivo",
    "costoEquipajeExtra",
    "cantidadEquipajeExtra",
    "costoTotal"
})
public class DTCostoBase {
    private float costoTurista;
    private float costoEjecutivo;
    private float costoEquipajeExtra;
    private int cantidadEquipajeExtra;
    private float costoTotal;

    // Constructor sin argumentos requerido por JAXB
    public DTCostoBase() {
        this.cantidadEquipajeExtra = 0;
    }

    public DTCostoBase(float costoTurista, float costoEjecutivo, float costoEquipajeExtra) {
        this.costoTurista = costoTurista;
        this.costoEjecutivo = costoEjecutivo;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.cantidadEquipajeExtra = 0; // Inicializar en 0, se puede modificar después
    }

    // Getters y setters

    public float getCostoTurista() {
        return costoTurista;
    }

    public void setCostoTurista(float costoTurista) {
        this.costoTurista = costoTurista;
    }

    public float getCostoEjecutivo() {
        return costoEjecutivo;
    }

    public void setCostoEjecutivo(float costoEjecutivo) {
        this.costoEjecutivo = costoEjecutivo;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public int getCantidadEquipajeExtra() {
        return cantidadEquipajeExtra;
    }

    public void setCantidadEquipajeExtra(int cantidadEquipajeExtra) {
        this.cantidadEquipajeExtra = cantidadEquipajeExtra;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String toString() {
        return String.valueOf(getCostoTotal());
    }
}
