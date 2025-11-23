package logica.DataTypes;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCliente", propOrder = {
    "apellido",
    "tipoDocumento",
    "numeroDocumento",
    "fechaNacimiento",
    "nacionalidad",
    "reserva"
})
public class DTCliente extends DTUsuario {
    private String apellido;
    private TipoDoc tipoDocumento;
    private String numeroDocumento;
    private DTFecha fechaNacimiento;
    private String nacionalidad;
    private List<DTReserva> reserva;

    // Constructor sin parámetros requerido por JAXB para serialización/deserialización XML
    public DTCliente() {
        super(); // Llama al constructor sin parámetros de DTUsuario
    }

    public DTCliente(String nickname, String nombre, String correo, String apellido, TipoDoc tipoDocumento, String numeroDocumento, DTFecha fechaNacimiento, String nacionalidad, List<DTReserva> reserva, byte[] foto, String contrasena) {
        super(nickname, nombre, correo, foto, contrasena);
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.reserva = reserva;
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

    public DTFecha getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public DTCliente getCliente() {
        return this;
    }

    public List<DTReserva> getReserva() {
        return this.reserva;
    }

    // MÉTODOS
    public String toString() {
        return super.getNickname();
    }

}
