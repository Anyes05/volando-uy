package logica.DataTypes;
import java.time.*;
import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtFecha", propOrder = {
    "dia",
    "mes",
    "ano"
})
public class DTFecha {
    private int dia;
    private int mes;
    private int ano;

    // Constructor sin parámetros requerido por JAXB
    public DTFecha() {
    }

    public DTFecha(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }

    //Getters

    public int getAno() {
        return ano;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    // MÉTODOS
    @Override
    public String toString() {
        return dia + "/" + mes + "/" + ano;
    }


    public boolean isAfter(LocalDate hoy) {
        LocalDate fecha = LocalDate.of(ano, mes, dia);
        hoy = LocalDate.of(hoy.getYear(), hoy.getMonth(), hoy.getDayOfMonth()-1);
        return fecha.isAfter(hoy);
    }
}
