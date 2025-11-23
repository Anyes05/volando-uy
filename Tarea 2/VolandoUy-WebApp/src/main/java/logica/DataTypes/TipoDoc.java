package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "tipoDoc")
@XmlEnum
public enum TipoDoc {
    CI,
    PASAPORTE,
    DNI
}
