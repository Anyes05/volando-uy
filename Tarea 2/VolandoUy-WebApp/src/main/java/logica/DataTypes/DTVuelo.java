package logica.DataTypes;
import java.nio.file.Files;
import java.nio.file.Paths;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTVuelo", propOrder = {
    "duracion",
    "nombre",
    "fechaVuelo",
    "horaVuelo",
    "asientosMaxEjecutivo",
    "fechaAlta",
    "asientosMaxTurista",
    "ruta",
    "foto"
})
public class DTVuelo {
    private DTHora duracion;
    private String nombre;
    private DTFecha fechaVuelo;
    private DTHora horaVuelo;
    private int asientosMaxEjecutivo;
    private DTFecha fechaAlta;
    private int asientosMaxTurista;
    private DTRutaVuelo ruta;
    private byte[] foto;

    // Constructor sin argumentos requerido por JAXB
    public DTVuelo() {
    }

    public DTVuelo(DTHora duracion, String nombre, DTFecha fechaVuelo, DTHora horaVuelo, int asientosMaxEjecutivo, DTFecha fechaAlta, int asientosMaxTurista, DTRutaVuelo ruta, byte[] foto) {
        this.duracion = duracion;
        this.nombre = nombre;
        this.fechaVuelo = fechaVuelo;
        this.horaVuelo = horaVuelo;
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
        this.fechaAlta = fechaAlta;
        this.asientosMaxTurista = asientosMaxTurista;
        this.ruta = ruta;
        this.foto = foto;
    }

    //Getters
    public DTHora getDuracion() {
        return duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public DTFecha getFechaVuelo() {
        return fechaVuelo;
    }

    public DTHora getHoraVuelo() {
        return horaVuelo;
    }

    public int getAsientosMaxEjecutivo() {
        return asientosMaxEjecutivo;
    }

    public DTFecha getFechaAlta() {
        return fechaAlta;
    }

    public int getAsientosMaxTurista() {
        return asientosMaxTurista;
    }

    public DTRutaVuelo getRuta() {
        return ruta;
    }

    public byte[] getFoto() {
        return foto;
    }

    // Setters para JAXB
    public void setDuracion(DTHora duracion) {
        this.duracion = duracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaVuelo(DTFecha fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public void setHoraVuelo(DTHora horaVuelo) {
        this.horaVuelo = horaVuelo;
    }

    public void setAsientosMaxEjecutivo(int asientosMaxEjecutivo) {
        this.asientosMaxEjecutivo = asientosMaxEjecutivo;
    }

    public void setFechaAlta(DTFecha fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setAsientosMaxTurista(int asientosMaxTurista) {
        this.asientosMaxTurista = asientosMaxTurista;
    }

    public void setRuta(DTRutaVuelo ruta) {
        this.ruta = ruta;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    //Setters
    public String toString() {
        return nombre;
    }

}
