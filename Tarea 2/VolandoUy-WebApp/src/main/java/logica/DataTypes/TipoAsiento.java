package logica.DataTypes;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "tipoAsiento")
@XmlEnum
public enum TipoAsiento {
    Turista,
    Ejecutivo
}
