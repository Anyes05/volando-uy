package logica.DataTypes;

public class DTCompraPaquete extends DTReserva{
    private DTFecha vencimiento;

    public DTCompraPaquete(DTFecha fechaReserva, DTCostoBase costoReserva, DTFecha vencimiento){
        super(fechaReserva, costoReserva);
        this.vencimiento=vencimiento;
    }

    //Getters
    public DTFecha getVencimiento() {
        return vencimiento;
    }

    //Setters
}
