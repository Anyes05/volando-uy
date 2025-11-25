package logica.DataTypes;

public class DTPasajero {
    private String nombre;
    private String apellido;
    private String nicknameCliente; // Nickname del cliente al que pertenece este pasajero
    private int numeroAsiento;

    public DTPasajero() {
    }

    public DTPasajero(String nombre, String apellido, String nicknameCliente, int numeroAsiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nicknameCliente = nicknameCliente;
        this.numeroAsiento = numeroAsiento;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNicknameCliente() {
        return nicknameCliente;
    }

    public void setNicknameCliente(String nicknameCliente) {
        this.nicknameCliente = nicknameCliente;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + nicknameCliente + ")";
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }
}
