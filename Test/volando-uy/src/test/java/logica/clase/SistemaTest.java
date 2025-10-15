package logica.clase;
import logica.clase.Sistema;


import logica.DataTypes.DTFecha;
import logica.DataTypes.TipoDoc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



//NOTAS: Modifique validarContrasena
class SistemaTest {
    //CASOS DE USO
    //AltaCliente
    @Test
    void testAltaClienteCasos() {
        Sistema Sistema = logica.clase.Sistema.getInstance();

        // Caso válido
        assertDoesNotThrow(() ->
                Sistema.altaCliente("juanp", "Juan", "juan@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
                        new byte[]{1,2,3}, "Abcdef1")); // válido

        // Nickname duplicado
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("repetido", "Juan", "mail@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
                        new byte[]{1}, "Abcdef1")); // nickname ya existe

        // Correo duplicado
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("juanp", "Juan", "repetido@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
                        new byte[]{1}, "Abcdef1")); // correo ya existe

        // Documento duplicado
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("juanp", "Juan", "mail@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "repetido",
                        new byte[]{1}, "Abcdef1")); // documento ya existe

        // Nombre inválido
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("juanp", "Jo", "mail@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
                        new byte[]{1}, "Abcdef1")); // nombre inválido

        // Foto nula
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("juanp", "Juan", "mail@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
                        null, "Abcdef1")); // foto null

        // Contraseña inválida
        assertThrows(IllegalArgumentException.class, () ->
                Sistema.altaCliente("juanp", "Juan", "mail@mail.com", "Pérez",
                        new DTFecha(1,1,1990), "Uruguayo", TipoDoc.CI, "12345678",
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