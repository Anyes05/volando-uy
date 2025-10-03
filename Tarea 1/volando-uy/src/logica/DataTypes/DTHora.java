package logica.DataTypes;

public class DTHora {
    private int hora;
    private int minutos;

    public DTHora(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    //Getters
    public int getHora() {
        return hora;
    }

    public int getMinutos() {
        return minutos;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hora, minutos);
    }
}
