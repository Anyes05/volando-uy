package presentacion;
import logica.DataTypes.*;
import logica.clase.Factory;
import logica.clase.ISistema;
import java.util.List;
import logica.clase.Ciudad;

public class MainTest {
    public static void main(String[] args) {
        Factory factory = new Factory();
        ISistema estacion = factory.getSistema();

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
        // ----- PRECARGA DE CIUDADES -----

        // Crear los dtfecha para la precarga de ciudades
        DTFecha fechaAltaCiudad = new DTFecha(1, 1, 2025);

        estacion.agregarCiudadParaTest(new Ciudad("Montevideo", "Uruguay", fechaAltaCiudad));
        estacion.agregarCiudadParaTest(new Ciudad("Buenos Aires", "Argentina", fechaAltaCiudad));

        // ----- PRECARGA PARA ALTA DE RUTA DE VUELO -----
        try {
            System.out.println("\n--- Precarga de ruta de vuelo ---");

            // 1. Seleccionamos la aerolínea
            estacion.seleccionarAerolinea("air1");

            // 2. Creamos la ruta de vuelo
            DTHora horaVueloRuta = new DTHora(10, 0); // hora de salida solo para DTO
            DTRutaVuelo ruta = estacion.ingresarDatosRuta(
                    "MVD-BUE",                   // nombre de la ruta
                    "Vuelo Montevideo → Buenos Aires", // descripción
                    horaVueloRuta,               // hora de salida
                    150.0f,                      // costo turista
                    300.0f,                      // costo ejecutivo
                    50.0f,                       // costo equipaje extra
                    "Montevideo",                // ciudad origen
                    "Buenos Aires",              // ciudad destino
                    new DTFecha(1, 7, 2025),     // fecha alta
                    "Turista"                    // categoría
            );

            // 3. Registramos la ruta
            estacion.registrarRuta();

            // 4. Mostrar detalles de la ruta
            System.out.println("Ruta de vuelo registrada:");
            System.out.println("Nombre: " + ruta.getNombre());
            System.out.println("Descripción: " + ruta.getDescripcion());
            System.out.println("Origen: " + ruta.getCiudadOrigen().getNombre());
            System.out.println("Destino: " + ruta.getCiudadDestino().getNombre());
            System.out.println("Costo Turista: " + ruta.getCostoBase().getCostoTurista());
            System.out.println("Costo Ejecutivo: " + ruta.getCostoBase().getCostoEjecutivo());
            System.out.println("Costo Equipaje Extra: " + ruta.getCostoBase().getCostoEquipajeExtra());

        } catch (Exception e) {
            System.out.println("Error en precarga de ruta: " + e.getMessage());
        }

// ----- PRECARGA PARA ALTA DE VUELO -----
        try {
            System.out.println("\n--- Precarga de vuelo ---");

            // 1. Listar aerolíneas disponibles
            System.out.println("Aerolineas disponibles:");
            for (DTAerolinea a : estacion.listarAerolineas()) {
                System.out.println(a);
            }

            // 2. Seleccionar aerolínea "air1" y obtener sus rutas
            List<DTRutaVuelo> rutas = estacion.seleccionarAerolineaRet("air1");
            System.out.println("\nRutas de air1:");
            for (DTRutaVuelo r : rutas) {
                System.out.println("- " + r.getNombre() + " (" + r.getCiudadOrigen().getNombre() + " → " + r.getCiudadDestino().getNombre() + ")");
            }

            // 3. Seleccionar una ruta específica
            DTRutaVuelo rutaSeleccionada = estacion.seleccionarRutaVueloRet("MVD-BUE");
            System.out.println("\nRuta seleccionada: " + rutaSeleccionada.getNombre());

            // 4. Ingresar datos del vuelo
            DTFecha fechaVuelo = new DTFecha(15, 8, 2025);
            DTHora horaVuelo = new DTHora(10, 30);
            DTHora duracion = new DTHora(2, 0);
            int maxTurista = 100;
            int maxEjecutivo = 20;
            DTFecha fechaAltaVuelo = new DTFecha(20, 7, 2025);

            DTVuelo dtVuelo = estacion.ingresarDatosVuelo(
                    "AIR001",
                    fechaVuelo,
                    horaVuelo,
                    duracion,
                    maxTurista,
                    maxEjecutivo,
                    fechaAltaVuelo,
                    rutaSeleccionada
            );

            // 5. Mostrar detalles del vuelo
            System.out.println("\nDatos del vuelo ingresados:");
            System.out.println("Nombre del vuelo: " + dtVuelo.getNombre());
            System.out.println("Fecha: " + dtVuelo.getFechaVuelo());
            System.out.println("Hora de salida: " + dtVuelo.getHoraVuelo().getHora() + ":" + dtVuelo.getHoraVuelo().getMinutos());
            System.out.println("Duración: " + dtVuelo.getDuracion().getHora() + "h " + dtVuelo.getDuracion().getMinutos() + "m");
            System.out.println("Máx. Turista: " + dtVuelo.getAsientosMaxTurista());
            System.out.println("Máx. Ejecutivo: " + dtVuelo.getAsientosMaxEjecutivo());
            System.out.println("Ruta asociada: " + dtVuelo.getRuta().getNombre() + " (" + dtVuelo.getRuta().getCiudadOrigen().getNombre() + " → " + dtVuelo.getRuta().getCiudadDestino().getNombre() + ")");
            // 6. Dar de alta el vuelo
            estacion.darAltaVuelo();
            System.out.println("Vuelo dado de alta correctamente.");

        } catch (Exception e) {
            System.out.println("Error en precarga de vuelo: " + e.getMessage());
        }

        // ----- CONSULTA DE VUELOS -----
        try {
            System.out.println("\n--- Consulta de vuelos por ruta ---");
            String nombreRuta = "MVD-BUE";

            // 1. Seleccionar vuelos de la ruta
            List<DTVuelo> vuelosRuta = estacion.seleccionarRutaVuelo(nombreRuta);

            if (vuelosRuta.isEmpty()) {
                System.out.println("No hay vuelos para la ruta " + nombreRuta);
            } else {
                for (DTVuelo v : vuelosRuta) {
                    System.out.println("\nVuelo encontrado: " + v.getNombre());
                    System.out.println("Fecha: " + v.getFechaVuelo().getDia() + "/" + v.getFechaVuelo().getMes() + "/" + v.getFechaVuelo().getAno());
                    System.out.println("Hora: " + v.getHoraVuelo().getHora() + ":" + v.getHoraVuelo().getMinutos());
                    System.out.println("Duración: " + v.getDuracion().getHora() + "h " + v.getDuracion().getMinutos() + "m");
                    System.out.println("Max turista: " + v.getAsientosMaxTurista());
                    System.out.println("Max ejecutivo: " + v.getAsientosMaxEjecutivo());

                    // Datos de la ruta
                    DTRutaVuelo ruta = v.getRuta();
                    System.out.println("Ruta: " + ruta.getNombre() + " - " + ruta.getDescripcion());
                    System.out.println("Origen: " + ruta.getCiudadOrigen().getNombre() + ", Destino: " + ruta.getCiudadDestino().getNombre());
                    System.out.println("Costo turista: " + ruta.getCostoBase().getCostoTurista());
                    System.out.println("Costo ejecutivo: " + ruta.getCostoBase().getCostoEjecutivo());

                    // Aerolínea de la ruta
                    DTAerolinea aerolinea = ruta.getAerolinea();
                    System.out.println("Aerolínea: " + aerolinea.getNombre() + " (" + aerolinea.getNickname() + ")");
                }
            }

            // ----- CONSULTA DE RESERVAS PARA UN VUELO -----
            System.out.println("\n--- Consulta de reservas para un vuelo ---");
            if (!vuelosRuta.isEmpty()) {
                String nombreVuelo = vuelosRuta.get(0).getNombre(); // tomamos el primer vuelo de ejemplo
                List<DTVueloReserva> reservas = estacion.seleccionarVuelo(nombreVuelo);

                if (reservas.isEmpty()) {
                    System.out.println("No hay reservas para el vuelo " + nombreVuelo);
                } else {
                    for (DTVueloReserva dr : reservas) {
                        System.out.println("Reserva:");
                        System.out.println("  Fecha reserva: " + dr.getReserva().getFechaReserva().getDia() + "/" +
                                dr.getReserva().getFechaReserva().getMes() + "/" +
                                dr.getReserva().getFechaReserva().getAno());
                        System.out.println("  Costo reserva: " + dr.getReserva().getCostoReserva());
                        System.out.println("  Vuelo asociado: " + dr.getVuelo().getNombre());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error en consulta de vuelos o reservas: " + e.getMessage());
        }


        // CONSULTA USUARIOS
        try {
            System.out.println("\n--- Consulta de perfil de usuario ---");

            // 1. Mostrar lista de usuarios
            List<DTUsuario> usuarios = estacion.consultarUsuarios();
            System.out.println("Usuarios registrados:");
            for (DTUsuario u : usuarios) {
                System.out.println(u);
            }

            // 2. Consultar perfil de cliente
            System.out.println("\nPerfil de cliente 'nick1':");
            DTUsuario cliente = estacion.mostrarDatosUsuario("nick1");
            System.out.println(cliente);

            // 3. Consultar perfil de aerolínea
            System.out.println("\nPerfil de aerolínea 'air1':");
            DTUsuario aerolinea = estacion.mostrarDatosUsuario("air1");
            System.out.println(aerolinea);

            // 4. Mostrar rutas de vuelo de la aerolínea
            System.out.println("\nRutas de vuelo de 'air1':");
            List<DTRutaVuelo> rutas = estacion.listarRutaVuelo("air1");
            for (DTRutaVuelo r : rutas) {
                System.out.println(r);
            }

            // 5. Mostrar reservas y paquetes del cliente (si existen)
            // Si el toString de DTCliente muestra las reservas y paquetes, ya se ven en el paso 2

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}

