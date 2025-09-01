package presentacion;

import logica.clase.Sistema;
import logica.DataTypes.DTFecha;

public class TestPrecarga {
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando precarga de aeropuertos...");
            Sistema.getInstance().precargarAeropuertos();
            System.out.println("Precarga completada exitosamente.");
            
            // Probar crear una ciudad
            System.out.println("Probando creación de ciudad...");
            DTFecha fecha = new DTFecha(15, 12, 2024);
            Sistema.getInstance().altaCiudad("Montevideo", "Uruguay", "Carrasco", fecha);
            System.out.println("Ciudad creada exitosamente.");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
