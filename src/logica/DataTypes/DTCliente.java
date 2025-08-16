package logica.DataTypes;

public class DTCliente extends DTUsuario{
    private String apellido;
    private TipoDoc tipoDocumento;
    private String numeroDocumento;
    private DTFecha fechaNacimiento;
    private String nacionalidad;

    public DTCliente(String nickname, String nombre, String correo, String apellido, TipoDoc tipoDocumento, String numeroDocumento, DTFecha fechaNacimiento, String nacionalidad){
        super(nickname, nombre, correo);
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
    }

    // Getters
    public String getApellido() {
        return this.apellido;
    }
    public String getNumeroDocumento() {
        return this.numeroDocumento;
    }
    public String getNacionalidad() {
        return this.nacionalidad;
    }

    public TipoDoc getTipoDocumento() {
        return this.tipoDocumento;
    }

    public DTFecha getFechaNacimiento(){
        return this.fechaNacimiento;
    }

    public DTCliente getCliente(){
        return this;
    }
    // Setters

}
