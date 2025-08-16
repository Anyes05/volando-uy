package logica.DataTypes;

public class DTCompraPaquete extends DTReserva{
    private DTFecha vencimiento;

    public DTCompraPaquete(DTFecha fechaReserva, float costoReserva, DTFecha vencimiento){
        super(fechaReserva, costoReserva);
        this.vencimiento=vencimiento;
    }

    //Getters
    public DTFecha getVencimiento() {
        return vencimiento;
    }

    //Setters
    public void setVencimiento(DTFecha vencimiento) {
        this.vencimiento = vencimiento;
    }
}
