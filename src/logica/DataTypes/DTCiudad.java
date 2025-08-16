package DataTypes;

public class DTCiudad {
    private String nombre;
    private String pais;

    public DTCiudad(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    //Getters
    public String getNombre() {
        return nombre;
    }
    public String getPais() {
        return pais;
    }

    //Setters
    public void setPais(String pais) {
        this.pais = pais;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
