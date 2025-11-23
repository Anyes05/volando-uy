package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DTVueloReserva")
public class DTVueloReserva {// Armar según corresponda
    DTVuelo vuelo;
    DTReserva reserva;

    // Constructor sin argumentos requerido por JAXB
    public DTVueloReserva() {
    }

    public DTVueloReserva(DTVuelo vuelo, DTReserva reserva) {
        this.vuelo = vuelo;
        this.reserva = reserva;
    }

    public DTVuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(DTVuelo vuelo) {
        this.vuelo = vuelo;
    }

    public DTReserva getReserva() {
        return reserva;
    }

    public void setReserva(DTReserva reserva) {
        this.reserva = reserva;
    }

    public String toString() {
        if (reserva == null) {
            return "DTVueloReserva [vuelo=" + vuelo + ", reserva=null]";
        }
        return "ID: " + reserva.getId() + "     -     Cliente: " + reserva.getNickname();
    }
}
