package dato.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import logica.DataTypes.DTCostoBase;

@Converter(autoApply = true)
public class DTCostoBaseConverter implements AttributeConverter<DTCostoBase, String> {

    private static final String SEPARADOR = ";";

    @Override
    public String convertToDatabaseColumn(DTCostoBase costoBase) {
        if (costoBase == null) return null;

        // Guardar los valores en un String separados por ;
        return costoBase.getCostoTurista() + SEPARADOR +
                costoBase.getCostoEjecutivo() + SEPARADOR +
                costoBase.getCostoEquipajeExtra() + SEPARADOR +
                costoBase.getCantidadEquipajeExtra() + SEPARADOR +
                costoBase.getCostoTotal();
    }

    @Override
    public DTCostoBase convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;

        String[] partes = dbData.split(SEPARADOR);
        DTCostoBase costoBase = new DTCostoBase(
                Float.parseFloat(partes[0]), // costoTurista
                Float.parseFloat(partes[1]), // costoEjecutivo
                Float.parseFloat(partes[2])  // costoEquipajeExtra
        );
        costoBase.setCantidadEquipajeExtra(Integer.parseInt(partes[3]));
        costoBase.setCostoTotal(Float.parseFloat(partes[4]));

        return costoBase;
    }
}
