package logica.clase;
import logica.DataTypes.*;

public class MainTest {
    public static void main(String[] args) {
        Factory factory = new Factory();
        IEstacionTrabajo estacion = factory.getEstacionTrabajo();

        // ALTA USUARIO
        // Alta de Cliente válido
        try {
            estacion.altaCliente("nick1", "Juan", "juan@mail.com", "Perez", new DTFecha(1,1,1990), "Uruguay", TipoDoc.Pasaporte, "12345");
            System.out.println("Cliente dado de alta correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alta de Aerolínea válida
        try {
            estacion.altaAerolinea("air1", "AeroUruguay", "aero@mail.com", "Aerolínea nacional", "www.aerouruguay.com");
            System.out.println("Aerolínea dada de alta correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Intento de alta con nickname repetido
        try {
            estacion.altaCliente("nick1", "Carlos", "carlos@mail.com", "Lopez", new DTFecha(2,2,1985), "Argentina", TipoDoc.DNI, "54321");
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        // Intento de alta con correo repetido
        try {
            estacion.altaAerolinea("air2", "AeroArgentina", "aero@mail.com", "Aerolínea argentina", "www.aeroarg.com");
        } catch (Exception e) {
            System.out.println("Error esperado: " + e.getMessage());
        }

        // Mostrar usuarios dados de alta
        try {
            System.out.println(estacion.consultarUsuarios());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // MODIFICAR DATOS DE USUARIO
        // Modificar Cliente
        try {
            estacion.seleccionarUsuarioAMod("nick1");
            estacion.modificarDatosCliente("JuanMod", "PerezMod", new DTFecha(2,2,1991), "Argentina", TipoDoc.DNI, "99999");
            System.out.println("Cliente modificado:");
            System.out.println(estacion.mostrarDatosUsuarioMod("nick1"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Modificar Aerolínea
        try {
            estacion.seleccionarUsuarioAMod("air1");
            estacion.modificarDatosAerolinea("AeroUruguayMod", "Nueva descripción", "www.nuevo.com");
            System.out.println("Aerolínea modificada:");
            System.out.println(estacion.mostrarDatosUsuarioMod("air1"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
