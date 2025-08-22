package logica.clase;
import logica.DataTypes.DTFecha;

import java.util.ArrayList;
import java.util.List;

public class Ciudad {
    private String nombre;
    private String pais;
    private List<Aeropuerto> aeropuertos;
    private DTFecha fechaAlta;

    public Ciudad(String nombre, String pais, DTFecha fechaAlta){
        this.nombre = nombre;
        this.pais = pais;
        this.aeropuertos = new ArrayList<>();
        this.fechaAlta = fechaAlta;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }

    public void setPais(String pais) { this.pais = pais; }

    public List<Aeropuerto> getAeropuertos() { return this.aeropuertos; }

    public void setAeropuertos(List<Aeropuerto> aeropuertos) { this.aeropuertos = aeropuertos; }

    public DTFecha getFechaAlta() { return fechaAlta; }

    public void setFechaAlta(DTFecha fechaAlta) { this.fechaAlta = fechaAlta;}
}
