package presentacion;

import logica.clase.Factory;
import logica.clase.ISistema;
import logica.DataTypes.DTFecha;

public class TestPrecarga {
    public static void main(String[] args) {
        try {
            Factory factory = new Factory();
            ISistema sistema = factory.getSistema();
            
            System.out.println("Iniciando precarga de aeropuertos...");
            sistema.precargarAeropuertos();
            System.out.println("Precarga completada exitosamente.");
            
            // Probar crear una ciudad
            System.out.println("Probando creación de ciudad...");
            DTFecha fecha = new DTFecha(15, 12, 2024);
            sistema.altaCiudad("Montevideo", "Uruguay", "Carrasco", "Capital de Uruguay", "https://www.montevideo.gub.uy", fecha);
            System.out.println("Ciudad creada exitosamente.");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
