package dato.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import logica.DataTypes.DTHora;

@Converter(autoApply = true)
public class DTHoraConverter implements AttributeConverter<DTHora, String> {
    @Override
    public String convertToDatabaseColumn(DTHora dtHora) {
        if (dtHora == null) return null;
        return String.format("%02d:%02d", dtHora.getHora(), dtHora.getMinutos());
    }

    @Override
    public DTHora convertToEntityAttribute(String value) {
        if (value == null) return null;
        String[] parts = value.split(":");
        return new DTHora(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }
}
