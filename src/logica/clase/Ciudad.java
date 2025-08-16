package logica.clase;
import java.util.ArrayList;
import java.util.List;

public class Ciudad {
    private String nombre;
    private String pais;
    private List<Aeropuerto> aeropuertos;

    public Ciudad(String nombre, String pais){
        this.nombre = nombre;
        this.pais = pais;
        this.aeropuertos = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }

    public void setPais(String pais) { this.pais = pais; }

    public List<Aeropuerto> getAeropuertos() { return this.aeropuertos; }

    public void setAeropuertos(List<Aeropuerto> aeropuertos) { this.aeropuertos = aeropuertos; }
}
