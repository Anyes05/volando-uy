package logica.clase;

import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    private String apellido;
    private DTFecha fechaNacimiento;
    private String nacionalidad;
    private TipoDoc tipoDoc;
    private String numeroDocumento;
    private int cantidadPaquetes;

    private List<Reserva> reservas;

    public Cliente(String nickaname, String nombre, String correo, String apellido, DTFecha fechaNacimiento, String nacionalidad, TipoDoc tipoDoc, String numeroDocumento) {
        super(nickaname, nombre, correo);

        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDoc = tipoDoc;
        this.numeroDocumento = numeroDocumento;
        this.reservas = new ArrayList<>();
        this.cantidadPaquetes = 0;
    }

    // Getters y Setters
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public DTFecha getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(DTFecha fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public TipoDoc getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(TipoDoc tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void agregarReserva(Reserva reserva) {
        this.reservas.add(reserva);
    }

    public void incrementarCantidadPaquetes() {
        this.cantidadPaquetes++;
    }
}
