package logica.clase;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nombre;
    private List<RutaVuelo> rutas;

    public Categoria(String nombre){
        this.nombre = nombre;
        this.rutas = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<RutaVuelo> getRutas() { return rutas; }

    public void setRutas(List<RutaVuelo> rutas) { this.rutas = rutas; }

}
