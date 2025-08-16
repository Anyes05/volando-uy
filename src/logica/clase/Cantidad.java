package logica.clase;

import java.util.ArrayList;
import java.util.List;

public class Cantidad {
    //ATRIBUTOS
    private int cant;
    private List<RutaVuelo> rutasVuelos;

    //CONSTRUCTOR
    public Cantidad(int cant) {
        this.cant = cant;

        this.rutasVuelos = new ArrayList<>();
    }

    //GET Y SET
    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public List<RutaVuelo> getRutasVuelos() {
        return rutasVuelos;
    }

    public void setRutasVuelos(List<RutaVuelo> rutasVuelos) {
        this.rutasVuelos = rutasVuelos;
    }
}
