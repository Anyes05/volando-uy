package logica.clase;

public class Factory {
    public ISistema getSistema(){
        return Sistema.getInstance();
    }
}
