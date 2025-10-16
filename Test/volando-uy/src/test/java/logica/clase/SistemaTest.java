package logica.clase;
import logica.clase.Sistema;


import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import jakarta.persistence.*;



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

        // Borrado en orden inverso
        em.createQuery("DELETE FROM CompraPaquete").executeUpdate();
        em.createQuery("DELETE FROM CompraComun").executeUpdate();
        em.createQuery("DELETE FROM Reserva").executeUpdate();
        em.createQuery("DELETE FROM Pasaje").executeUpdate();
        em.createQuery("DELETE FROM PaqueteVuelo").executeUpdate();
        em.createQuery("DELETE FROM Cantidad").executeUpdate();
        em.createQuery("DELETE FROM Categoria").executeUpdate();
        em.createQuery("DELETE FROM RutaVuelo").executeUpdate();
        em.createQuery("DELETE FROM Vuelo").executeUpdate();
        em.createQuery("DELETE FROM Aeropuerto").executeUpdate();
        em.createQuery("DELETE FROM Ciudad").executeUpdate();
        em.createQuery("DELETE FROM Aerolinea").executeUpdate();
        em.createQuery("DELETE FROM Cliente").executeUpdate();
        em.createQuery("DELETE FROM Usuario").executeUpdate();

        tx.commit();
        em.close();
    }

    //CASOS DE USO
    //AltaCliente
    @Test
    void testAltaClienteCasos() {
        Sistema sistema = Sistema.getInstance();

        // Caso válido
        assertDoesNotThrow(() ->
                sistema.altaCliente("baseNick", "Juan", "base@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCBASE",
                        new byte[]{1}, "Abcdef1")); // válido

        // Nickname duplicado
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("baseNick", "Otro", "otro@mail.com", "López",
                        new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC123",
                        new byte[]{1}, "Abcdef1")); // nickname duplicado

        // Correo duplicado
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickNuevo", "Otro", "base@mail.com", "López",
                        new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOC124",
                        new byte[]{1}, "Abcdef1")); // correo duplicado

        // Documento duplicado
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickNuevo2", "Otro", "otro2@mail.com", "López",
                        new DTFecha(2,2,1992), "Uruguayo", TipoDoc.CI, "DOCBASE",
                        new byte[]{1}, "Abcdef1")); // documento duplicado

        // Nickname inválido: null
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente(null, "Juan", "nicknull@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNICKNULL",
                        new byte[]{1}, "Abcdef1")); // nickname null

        // Nombre inválido: muy corto
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickNomCorto", "Jo", "nomcorto@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCNOMCORTO",
                        new byte[]{1}, "Abcdef1")); // nombre corto

        // Apellido inválido: solo espacios
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickApeEsp", "Juan", "apeesp@mail.com", "   ",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCAPEESP",
                        new byte[]{1}, "Abcdef1")); // apellido espacios

        // Nacionalidad inválida: null
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickNacNull", "Juan", "nacnull@mail.com", "Pérez",
                        new DTFecha(1,1,1990), null, TipoDoc.CI, "DOCNACNULL",
                        new byte[]{1}, "Abcdef1")); // nacionalidad null

        // Foto nula
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickFotoNull", "Juan", "foto@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCFOTO",
                        null, "Abcdef1")); // foto nula

        // Contraseña inválida
        assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("nickPassInvalida", "Juan", "pass@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "DOCPASS",
                        new byte[]{1}, "abc")); // contraseña inválida
    }

    // FUNCIONES EXTRAS
    @Test
    void testValidarContrasenaCasos() {
        assertTrue(Sistema.validarContrasena("Abcdef1")); // válida
        assertFalse(Sistema.validarContrasena("Ab1")); // muy corta
        assertFalse(Sistema.validarContrasena("abcdef1")); // sin mayúscula
        assertFalse(Sistema.validarContrasena("ABCDEF1")); // sin minúscula
        assertFalse(Sistema.validarContrasena("Abcdef")); // sin número
        assertFalse(Sistema.validarContrasena("")); // vacía
        assertFalse(Sistema.validarContrasena("123456")); // solo números
        assertFalse(Sistema.validarContrasena(null)); //null
    }

    @Test
    void testEsNombreValidoCasos() {
        assertTrue(Sistema.esNombreValido("Juan Pérez")); // válido
        assertFalse(Sistema.esNombreValido("Jo")); // muy corto
        assertFalse(Sistema.esNombreValido("Juan123")); // con números
        assertFalse(Sistema.esNombreValido("Juan!")); // con símbolo
        assertFalse(Sistema.esNombreValido("   ")); // solo espacios
        assertFalse(Sistema.esNombreValido("")); // vacío
        assertFalse(Sistema.esNombreValido(null)); //null
    }



}