package logica.clase;
import dato.entidades.Aerolinea;
import dato.entidades.Ciudad;
import dato.entidades.RutaVuelo;
import dato.entidades.Vuelo;
import logica.DataTypes.*;
import logica.clase.Sistema;


import logica.servicios.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


//NOTAS: Modifique validarContrasena
class SistemaTest {
    //LIMPIAR BD
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    static void initEMF() {
        emf = Persistence.createEntityManagerFactory("volandouyPU");
    }

    @BeforeEach
    void limpiarBasePostgres() {
        em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        em.createQuery("DELETE FROM CompraPaquete").executeUpdate();
        em.createQuery("DELETE FROM Pasaje").executeUpdate();
        em.createQuery("DELETE FROM Reserva").executeUpdate();
        em.createQuery("DELETE FROM CompraComun").executeUpdate();
        em.createQuery("DELETE FROM PaqueteVuelo").executeUpdate();
        em.createQuery("DELETE FROM Cantidad").executeUpdate();
        em.createQuery("DELETE FROM Vuelo").executeUpdate();       // primero vuelos
        em.createQuery("DELETE FROM RutaVuelo").executeUpdate();   // luego rutas
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM Aeropuerto").executeUpdate();
        em.createQuery("DELETE FROM Ciudad").executeUpdate();
        em.createQuery("DELETE FROM Aerolinea").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();
        em.createQuery("DELETE FROM Usuario").executeUpdate();

        tx.commit();
        em.close();
    }


    //CASOS DE USO

    //ALTASUSUARIO
    //AltaCliente
    @Test
    void testAltaCliente() {
        Sistema s = Sistema.getInstance();

        assertDoesNotThrow(() -> s.altaCliente("baseNick", "Juan", "base@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCBASE", new byte[]{1}, "Abcdef1")); // válido
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("baseNick", "Otro", "otro@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC123", new byte[]{1}, "Abcdef1")); // nickname duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNuevo", "Otro", "base@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC124", new byte[]{1}, "Abcdef1")); // correo duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNuevo2", "Otro", "otro2@mail.com", "López", new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOCBASE", new byte[]{1}, "Abcdef1")); // documento duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente(null, "Juan", "nicknull@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNICKNULL", new byte[]{1}, "Abcdef1")); // nickname null
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNomCorto", "Jo", "nomcorto@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNOMCORTO", new byte[]{1}, "Abcdef1")); // nombre corto
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickApeEsp", "Juan", "apeesp@mail.com", "   ", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCAPEESP", new byte[]{1}, "Abcdef1")); // apellido espacios
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickNacNull", "Juan", "nacnull@mail.com", "Pérez", new DTFecha(1,1,1990), null, TipoDoc.CI, "DOCNACNULL", new byte[]{1}, "Abcdef1")); // nacionalidad null
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickFotoNull", "Juan", "foto@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCFOTO", null, "Abcdef1")); // foto nula
        assertThrows(IllegalArgumentException.class, () -> s.altaCliente("nickPassInvalida", "Juan", "pass@mail.com", "Pérez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCPASS", new byte[]{1}, "abc")); // contraseña inválida
    }
    //AltaAerolinea
    @Test
    void testAltaAerolinea() {
        Sistema s = Sistema.getInstance();

        assertDoesNotThrow(() -> s.altaAerolinea("aeroNick", "Aerolíneas", "aero@mail.com", "Empresa aérea uruguaya", "https://aerolineas.com", new byte[]{1}, "Abcdef1")); // valido (fallaba por tildes)
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("aeroNick", "Otra", "otra@mail.com", "Otra descripción", "https://otra.com", new byte[]{1}, "Abcdef1")); // nickname duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("aeroNueva", "Otra", "aero@mail.com", "Otra descripción", "https://otra.com", new byte[]{1}, "Abcdef1")); // correo duplicado
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea(null, "Aerolíneas", "nicknull@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "Abcdef1")); // nickname null
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickNomCorto", "Ae", "nomcorto@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "Abcdef1")); // nombre corto
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickDescEsp", "Aerolíneas", "descesp@mail.com", "   ", "https://a.com", new byte[]{1}, "Abcdef1")); // descripcion invalida
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickFotoNull", "Aerolíneas", "foto@mail.com", "Empresa aérea", "https://a.com", null, "Abcdef1")); // foto nula
        assertThrows(IllegalArgumentException.class, () -> s.altaAerolinea("nickPassInv", "Aerolíneas", "pass@mail.com", "Empresa aérea", "https://a.com", new byte[]{1}, "abc")); // contrasena invalida
    }

    //CONSULTAUSUARIO
    @Test
    void testConsultarUsuarios() {
        Sistema s = Sistema.getInstance();
        assertThrows(IllegalStateException.class, () -> s.consultarUsuarios()); // lista vacía
        s.altaCliente("nickCliente", "Juan", "cliente@mail.com", "Perez", new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOC123", new byte[]{1}, "Abcdef1"); // alta cliente
        s.altaAerolinea("nickAero", "Aerolineas", "aero@mail.com", "Empresa aerea", "https://a.com", new byte[]{1}, "Abcdef1"); // alta aerolinea
        List<DTUsuario> usuarios = assertDoesNotThrow(() -> s.consultarUsuarios()); // lista con usuarios
        assertEquals(2, usuarios.size()); // contiene cliente y aerolinea
    }

    //MODIFICAR USUARIO
    //Modificar Cliente
    @Test
    void testModificarDatosCliente(){
        Sistema s=Sistema.getInstance();
        s.seleccionarUsuarioAMod(null);
        assertThrows(IllegalStateException.class,()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Uruguayo",TipoDoc.CI,"DOC123")); //sin usuario seleccionado
        s.seleccionarUsuarioAMod("inexistente");
        assertThrows(IllegalArgumentException.class,()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Uruguayo",TipoDoc.CI,"DOC123")); //cliente no encontrado
        s.altaCliente("nickCliente","Pedro","cliente@mail.com","Lopez",new DTFecha(2,2,1992),"Uruguayo",TipoDoc.CI,"DOC999",new byte[]{1},"Abcdef1"); //alta cliente
        s.seleccionarUsuarioAMod("nickCliente");
        assertDoesNotThrow(()->s.modificarDatosCliente("Juan","Perez",new DTFecha(1,1,1990),"Argentino",TipoDoc.PASAPORTE,"DOC123")); //modificacion valida
    }
    //Modificar Aerolinea
    @Test
    void testModificarDatosAerolinea(){
        Sistema s=Sistema.getInstance();
        s.seleccionarUsuarioAMod(null);
        assertThrows(IllegalStateException.class,()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //sin usuario seleccionado
        s.seleccionarUsuarioAMod("inexistente");
        assertThrows(IllegalArgumentException.class,()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //aerolinea no encontrada
        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","Empresa aerea","https://a.com",new byte[]{1},"Abcdef1"); //alta aerolinea
        s.seleccionarUsuarioAMod("nickAero");
        assertDoesNotThrow(()->s.modificarDatosAerolinea("NuevaNombre","NuevaDesc","https://nuevo.com")); //modificacion valida
    }
    //ALTA RUTA DE VUELO
    //IngresarDatosRuta
    @Test
    void testIngresarDatosRuta(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        CategoriaServicio categoriaServicio=new CategoriaServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoMVD_T","DescMVD","https://mvd.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAires","Argentina","AeropuertoBUE_T","DescBUE","https://bue.com",new DTFecha(1,1,2024));
        categoriaServicio.registrarCategoria("CategoriaT");

        //aerolínea no seleccionada
        assertThrows(IllegalStateException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));

        //alta y selección de aerolínea válida
        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","EmpresaAerea","https://a.com",new byte[]{1},"Abcdef1");
        s.seleccionarAerolinea("nickAero");

        //validaciones
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",-10,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"CiudadX","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","CiudadY",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
        assertThrows(IllegalArgumentException.class,()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaX"),new byte[]{1}));

        //caso válido
        assertDoesNotThrow(()->s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1}));
    }

    @Test
    void testRegistrarRuta(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        CategoriaServicio categoriaServicio=new CategoriaServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoMVD_T","DescMVD","https://mvd.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAires","Argentina","AeropuertoBUE_T","DescBUE","https://bue.com",new DTFecha(1,1,2024));
        categoriaServicio.registrarCategoria("CategoriaT");

        assertThrows(IllegalStateException.class,()->s.registrarRuta());

        s.altaAerolinea("nickAero","Aerolineas","aero@mail.com","EmpresaAerea","https://a.com",new byte[]{1},"Abcdef1");
        s.seleccionarAerolinea("nickAero");
        assertThrows(IllegalStateException.class,()->s.registrarRuta());

        s.ingresarDatosRuta("RutaValida","DescripcionValida",100,200,50,"Montevideo","BuenosAires",new DTFecha(1,1,2024),List.of("CategoriaT"),new byte[]{1});
        assertDoesNotThrow(()->s.registrarRuta());
    }

    //CONSULTAR RUTA DE VUELO

    @Test
    void testListarRutaVuelo() throws Exception {
        Sistema s = Sistema.getInstance();

        // 1. Alta y selección de aerolínea
        s.altaAerolinea("aeroTestListar","LineaAereaTest","testlistar@air.com",
                "Descripcion aerolinea valida","https://testlistar.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTestListar");

        // 2. Ciudades válidas
        CiudadServicio ciudadServicio = new CiudadServicio();
        ciudadServicio.registrarCiudad("CiudadOrigenListar","Uruguay","AeropuertoOrigenListar",
                "Descripcion ciudad origen listar","https://ciudadorigenlistar.com",new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("CiudadDestinoListar","Argentina","AeropuertoDestinoListar",
                "Descripcion ciudad destino listar","https://ciudaddestinolist.com",new DTFecha(2,2,2024));

        // 3. Categoría
        s.altaCategoria("CategoriaListar");

        // 4. Ingresar y registrar ruta bajo la aerolínea seleccionada
        s.ingresarDatosRuta("RutaListar","Descripcion de ruta listar valida",100,200,50,
                "CiudadOrigenListar","CiudadDestinoListar",
                new DTFecha(1,1,2025),List.of("CategoriaListar"),new byte[]{1});
        s.seleccionarAerolineaRet("aeroTestListar");
        s.registrarRuta();

        // 5. Confirmar ruta usando el flujo oficial de administración
        s.seleccionarAerolineaParaAdministracion("aeroTestListar");
        s.seleccionarRutaVueloParaAdministracion("RutaListar");
        try {
            s.aceptarRutaVuelo();
        } catch (IllegalStateException e) {
            // aceptarRutaVuelo lanza excepción con mensaje SUCCESS, la ignoramos si es ese caso
            if (!e.getMessage().startsWith("SUCCESS")) {
                throw e;
            }
        }

        // 6. Ejecutar listarRutaVuelo
        List<DTRutaVuelo> rutas = s.listarRutaVuelo("aeroTestListar");

        // 7. Validar
        assertEquals(1, rutas.size());
        assertEquals("RutaListar", rutas.get(0).getNombre());
        assertEquals("CONFIRMADA", rutas.get(0).getEstado().name());
    }



    //ALTA VUELO
    @Test
    void testAltaVuelo() {
        Sistema s = Sistema.getInstance();

        // 1. Alta de aerolínea
        s.altaAerolinea("aeroTest", "TestAir", "test@air.com", "Desc",
                "https://test.com", new byte[]{1}, "Clave123");

        // 2. Seleccionar aerolínea
        s.seleccionarAerolineaRet("aeroTest");

        // 3. Alta de ciudades con nombres únicos y válidos
        CiudadServicio ciudadServicio = new CiudadServicio();
        ciudadServicio.registrarCiudad("MontevideoTest", "Uruguay", "AeropuertoMontevideo",
                "Descripcion ciudad 1", "https://ciudad1.com", new DTFecha(1,1,2024));
        ciudadServicio.registrarCiudad("BuenosAiresTest", "Argentina", "AeropuertoBuenosAires",
                "Descripcion ciudad 2", "https://ciudad2.com", new DTFecha(2,2,2024));

        // 4. Alta de categoría
        s.altaCategoria("CategoriaX");

        // 5. Alta de ruta (usando los nombres de ciudades únicos)
        s.ingresarDatosRuta("RutaDummy", "Desc ruta", 100, 200, 50,
                "MontevideoTest", "BuenosAiresTest",
                new DTFecha(1,1,2025), List.of("CategoriaX"), new byte[]{1});
        s.registrarRuta();

        // Confirmar la ruta
        RutaVueloServicio rutaServicio = new RutaVueloServicio();
        RutaVuelo ruta = rutaServicio.buscarRutaVueloPorNombre("RutaDummy");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        // 6. Ingresar datos del vuelo
        DTRutaVuelo dtRuta = new DTRutaVuelo(
                ruta.getNombre(), ruta.getDescripcion(), ruta.getFechaAlta(),
                ruta.getCostoBase(), null,
                new DTCiudad(ruta.getCiudadOrigen().getNombre(), ruta.getCiudadOrigen().getPais()),
                new DTCiudad(ruta.getCiudadDestino().getNombre(), ruta.getCiudadDestino().getPais()),
                null, ruta.getEstado()
        );

        DTVuelo dtVuelo = s.ingresarDatosVuelo(
                "VueloDummy",
                new DTFecha(10,10,2025),
                new DTHora(12,30),
                new DTHora(2,0),
                100, 20,
                new DTFecha(9,9,2025),
                dtRuta,
                new byte[]{1}
        );

        assertNotNull(dtVuelo);
        assertEquals("VueloDummy", dtVuelo.getNombre());

        // 7. Dar de alta el vuelo
        s.seleccionarAerolineaRet("aeroTest"); // <-- reforzar selección
        s.darAltaVuelo();

        // 8. Verificar que el vuelo quedó registrado
        VueloServicio vueloServicio = new VueloServicio();
        Vuelo vueloPersistido = vueloServicio.buscarVueloPorNombre("VueloDummy");

        assertNotNull(vueloPersistido);
        assertEquals("VueloDummy", vueloPersistido.getNombre());
        assertEquals("RutaDummy", vueloPersistido.getRutaVuelo().getNombre());
    }

    //RESERVA
    @Test
    void testCrearPaquete() throws Exception {
        Sistema s = Sistema.getInstance();

        // 1. Alta de aerolínea y selección
        s.altaAerolinea("aeroTest","TestAir","test@air.com","Desc","https://test.com",new byte[]{1},"Clave123");
        s.seleccionarAerolineaRet("aeroTest");

        // 2. Alta de ciudades
        CiudadServicio cs = new CiudadServicio();
        cs.registrarCiudad("MontevideoTest","Uruguay","AeropuertoMVD","Desc1","https://c1.com",new DTFecha(1,1,2024));
        cs.registrarCiudad("BuenosAiresTest","Argentina","AeropuertoBUE","Desc2","https://c2.com",new DTFecha(2,2,2024));

        // 3. Alta de categoría
        s.altaCategoria("CategoriaX");

        // 4. Alta de ruta
        s.ingresarDatosRuta("RutaDummy","Desc ruta",100,200,50,
                "MontevideoTest","BuenosAiresTest",
                new DTFecha(1,1,2025),List.of("CategoriaX"),new byte[]{1});
        s.registrarRuta();

        // Confirmar ruta
        RutaVueloServicio rs = new RutaVueloServicio();
        RutaVuelo ruta = rs.buscarRutaVueloPorNombre("RutaDummy");
        ruta.setEstado(EstadoRutaVuelo.CONFIRMADA);

        // 5. Alta de vuelo
        DTRutaVuelo dtRuta = new DTRutaVuelo(ruta.getNombre(),ruta.getDescripcion(),ruta.getFechaAlta(),
                ruta.getCostoBase(),null,
                new DTCiudad(ruta.getCiudadOrigen().getNombre(),ruta.getCiudadOrigen().getPais()),
                new DTCiudad(ruta.getCiudadDestino().getNombre(),ruta.getCiudadDestino().getPais()),
                null,ruta.getEstado());
        s.ingresarDatosVuelo("VueloDummy",new DTFecha(10,10,2025),new DTHora(12,30),new DTHora(2,0),
                100,20,new DTFecha(9,9,2025),dtRuta,new byte[]{1});

        // Reforzar selección de aerolínea antes de dar de alta el vuelo
        s.seleccionarAerolineaRet("aeroTest");
        s.darAltaVuelo();

        // 6. Precargar clientes
        ClienteServicio clienteServicio = new ClienteServicio();
        clienteServicio.crearCliente(
                "cliente1", "Nombre1", "mail1@test.com", "Apellido1",
                new DTFecha(1,1,1990), "Uruguay", TipoDoc.CI, "12345678",
                new byte[]{1}, "pass1"
        );
        clienteServicio.crearCliente(
                "cliente2", "Nombre2", "mail2@test.com", "Apellido2",
                new DTFecha(2,2,1992), "Argentina", TipoDoc.CI, "87654321",
                new byte[]{1}, "pass2"
        );

        // 7. Seleccionar vuelo para reserva
        s.seleccionarVueloParaReserva("VueloDummy");

        // 8. Pasajeros
        List<String> pasajeros = new ArrayList<>();
        s.nombresPasajes("cliente1", pasajeros);
        s.nombresPasajes("cliente2", pasajeros);

        // 9. Ejecutar reserva
        Exception ex = assertThrows(IllegalStateException.class, () -> {
            s.datosReserva(TipoAsiento.Turista,2,1,pasajeros,new DTFecha(15,10,2025));
        });

        assertTrue(ex.getMessage().startsWith("SUCCESS:"));
    }







    // FUNCIONES EXTRAS

    @Test
    void testValidarContrasena() {
        assertTrue(Sistema.validarContrasena("Abcdef1")); // válida
        assertFalse(Sistema.validarContrasena("Ab1")); // muy corta
        assertFalse(Sistema.validarContrasena("abcdef1")); // sin mayúscula
        assertTrue(Sistema.validarContrasena("ABCDEF1")); // sin minúscula, pero válida según lógica actual
        assertFalse(Sistema.validarContrasena("Abcdef")); // sin número
        assertFalse(Sistema.validarContrasena("")); // vacía
        assertFalse(Sistema.validarContrasena("123456")); // solo números
        assertFalse(Sistema.validarContrasena(null)); // null
    }

    @Test
    void testEsNombreValido() {
        assertTrue(Sistema.esNombreValido("Juan Pérez")); // válido
        assertFalse(Sistema.esNombreValido("Jo")); // muy corto
        assertFalse(Sistema.esNombreValido("Juan123")); // con números
        assertFalse(Sistema.esNombreValido("Juan!")); // con símbolo
        assertFalse(Sistema.esNombreValido("   ")); // solo espacios
        assertFalse(Sistema.esNombreValido("")); // vacío
        assertFalse(Sistema.esNombreValido(null)); //null
    }

    //AltaCiudad
    @Test
    void testAltaCiudad(){
        Sistema s=Sistema.getInstance();
        CiudadServicio ciudadServicio=new CiudadServicio();
        AeropuertoServicio aeropuertoServicio=new AeropuertoServicio();
        ciudadServicio.registrarCiudad("Montevideo","Uruguay","AeropuertoBase","DescripcionBase","https://mvd.com",new DTFecha(1,1,2024)); //persistir ciudad
        dato.entidades.Ciudad ciudadBase=ciudadServicio.buscarCiudadPorNombreYPais("Montevideo","Uruguay"); //obtener ciudad persistida
        aeropuertoServicio.registrarAeropuerto("AeropuertoValido",ciudadBase); //registrar aeropuerto valido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","","AeropuertoValido","DescripcionValida","https://x.com",new DTFecha(2,2,2024))); //pais invalido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","Uruguay","","DescripcionValida","https://x.com",new DTFecha(2,2,2024))); //aeropuerto invalido
        assertThrows(IllegalArgumentException.class,()->s.altaCiudad("CiudadX","Uruguay","AeropuertoValido","","https://x.com",new DTFecha(2,2,2024))); //descripcion invalida
    }



}