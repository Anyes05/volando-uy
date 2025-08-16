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

    private List<Reserva> reservas;

    public Cliente(String nickaname, String nombre, String apellido, String correo, DTFecha fechaNacimiento, String nacionalidad, TipoDoc tipoDoc, String numeroDocumento) {
        super(nickaname, nombre, correo);

        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDoc = tipoDoc;
        this.numeroDocumento = numeroDocumento;
        this.reservas = new ArrayList<>();
    }


}
