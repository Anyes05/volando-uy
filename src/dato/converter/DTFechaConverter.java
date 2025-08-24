package dato.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import logica.DataTypes.DTFecha;
import java.sql.Date;
import java.time.LocalDate;

@Converter(autoApply = true)
public class DTFechaConverter implements AttributeConverter<DTFecha, Date> {
    // Convierte DTFecha a Date para almacenar en la base de datos
    @Override
    public Date convertToDatabaseColumn(DTFecha dtFecha) {
        if (dtFecha == null) return null;
        LocalDate localDate = LocalDate.of(dtFecha.getAno(), dtFecha.getMes(), dtFecha.getDia());
        return Date.valueOf(localDate);
    }
    // Convierte Date de la base de datos a DTFecha
    @Override
    public DTFecha convertToEntityAttribute(Date date) {
        if (date == null) return null;
        LocalDate localDate = date.toLocalDate();
        return new DTFecha(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
