package logica.clase;

import logica.clase.ISistema;
import logica.clase.Sistema;

public class Factory {
    public ISistema getSistema() {
        return Sistema.getInstance();
    }
}
