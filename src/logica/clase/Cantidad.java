package logica.clase;

import java.util.ArrayList;
import java.util.List;

public class Cantidad {
    //ATRIBUTOS
    private int cant;
    private RutaVuelo rutaVuelo;

    //CONSTRUCTOR
    public Cantidad(RutaVuelo ruta, int cant) {
        this.cant = cant;
        this.rutaVuelo = ruta;
    }

    //GET Y SET
    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public RutaVuelo getRutaVuelo() {
        return rutaVuelo;
    }

    public void setRutasVuelos(RutaVuelo rutaVuelo) {
        this.rutaVuelo = rutaVuelo;
    }

}
