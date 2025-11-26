package presentacion;

import logica.ws.PublicadorWS;

import javax.swing.*;


/**
 * Clase principal para ejecutar el servidor.jar
 * Permite elegir entre ejecutar el PublicadorWS o la EstaciónTrabajo
 */
public class Main {
    
    public static void main(String[] args) {
        // Si se pasa "ws" como argumento, ejecutar solo el Web Service
        if (args.length > 0 && "ws".equals(args[0])) {
            System.out.println(">>> Iniciando solo el PublicadorWS...");
            PublicadorWS.main(new String[0]);
            return;
        }
        
        // Si se pasa "gui" como argumento, ejecutar solo la Estación de Trabajo
        if (args.length > 0 && "gui".equals(args[0])) {
            System.out.println(">>> Iniciando solo la Estación de Trabajo...");
            iniciarEstacionTrabajo();
            return;
        }
        
        // Por defecto, mostrar menú para elegir
        mostrarMenu();
    }
    
    private static void mostrarMenu() {
        System.out.println("==========================================");
        System.out.println(">>> VolandoUy - Servidor Central");
        System.out.println("==========================================");
        System.out.println(">>> Seleccione una opción:");
        System.out.println(">>> 1. Iniciar Web Service (PublicadorWS)");
        System.out.println(">>> 2. Iniciar Estación de Trabajo (GUI)");
        System.out.println(">>> 3. Salir");
        System.out.println("==========================================");
        
        // En modo JAR, usar argumentos o iniciar GUI directamente
        // Para facilitar el uso, iniciaremos la GUI por defecto
        // El Web Service debe iniciarse por separado con: java -jar servidor.jar ws
        System.out.println(">>> Iniciando Estación de Trabajo...");
        System.out.println(">>> Para iniciar el Web Service, ejecute: java -jar servidor.jar ws");
        iniciarEstacionTrabajo();
    }
    
    private static void iniciarEstacionTrabajo() {
        SwingUtilities.invokeLater(() -> {
            try {
                // Intentar usar el método main de EstacionTrabajo directamente
                // Esto asegura que los componentes GUI se inicialicen correctamente
                EstacionTrabajo.main(new String[0]);
            } catch (Exception e) {
                System.err.println(">>> ERROR al iniciar la Estación de Trabajo: " + e.getMessage());
                e.printStackTrace();
                System.err.println(">>> ERROR crítico: No se pudo iniciar la Estación de Trabajo");
            }
        });
    }
}

