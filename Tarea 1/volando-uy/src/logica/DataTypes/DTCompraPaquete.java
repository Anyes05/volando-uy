package logica.DataTypes;

public class DTCompraPaquete extends DTReserva {
    private DTFecha vencimiento;
    private String nombrePaquete;

    public DTCompraPaquete(DTFecha fechaReserva, DTCostoBase costoReserva, DTFecha vencimiento) {
        super(fechaReserva, costoReserva);
        this.vencimiento = vencimiento;
    }

    //Getters
    public DTFecha getVencimiento() {
        return vencimiento;
    }

    public String getNombrePaquete() {
        return nombrePaquete;
    }

    //Setters
    public void setNombrePaquete(String nombrePaquete) {
        this.nombrePaquete = nombrePaquete;
    }
}
