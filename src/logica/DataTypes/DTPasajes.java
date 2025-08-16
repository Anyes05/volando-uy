package logica.DataTypes;

public class DTPasajes {
    private String nombre;
    private String apellido;
    private int cantidadEquipajeExtra;

    public DTPasajes(String nombre, String apellido, int cantidadEquipajeExtra) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cantidadEquipajeExtra = cantidadEquipajeExtra;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getCantidadEquipajeExtra() {
        return cantidadEquipajeExtra;
    }

    //Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setCantidadEquipajeExtra(int cantidadEquipajeExtra) {
        this.cantidadEquipajeExtra = cantidadEquipajeExtra;
    }
}
