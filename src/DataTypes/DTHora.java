package DataTypes;

public class DTHora {
    private int hora;
    private int minutos;

    public DTHora(int hora, int minutos) {
        this.hora = hora;
        this.minutos = minutos;
    }

    public int getHora() {
        return hora;
    }

    public int getMinutos(int minutos) {
        return minutos;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }
}
