package co.edu.univalle.puzzle.dominio.cuenta

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidacionCuentaTest {

    @Test
    fun `nombre de 3 a 20 caracteres es valido`() {
        assertNull(ValidacionCuenta.nombre("Ana"))
        assertNull(ValidacionCuenta.nombre("a".repeat(20)))
        assertNull(ValidacionCuenta.nombre("José_07 Ñ"))
    }

    @Test
    fun `nombre corto, largo o con simbolos es invalido`() {
        assertEquals("De 3 a 20 caracteres", ValidacionCuenta.nombre("Al"))
        assertEquals("De 3 a 20 caracteres", ValidacionCuenta.nombre("a".repeat(21)))
        assertNotNull(ValidacionCuenta.nombre("ana@@"))
    }

    @Test
    fun `nombre se valida sin espacios de los extremos`() {
        assertEquals("De 3 a 20 caracteres", ValidacionCuenta.nombre("  Al  "))
    }

    @Test
    fun `correo valido e invalido`() {
        assertNull(ValidacionCuenta.correo("jugador@correo.com"))
        assertNull(ValidacionCuenta.correo(" jugador.07@univalle.edu.co "))
        assertNotNull(ValidacionCuenta.correo("jugador@"))
        assertNotNull(ValidacionCuenta.correo("jugador.correo.com"))
        assertNotNull(ValidacionCuenta.correo(""))
    }

    @Test
    fun `contrasena necesita al menos 8 caracteres`() {
        assertNotNull(ValidacionCuenta.contrasena("1234567"))
        assertNull(ValidacionCuenta.contrasena("12345678"))
    }

    @Test
    fun `confirmacion debe coincidir`() {
        assertNull(ValidacionCuenta.confirmacion("secreto123", "secreto123"))
        assertEquals("Las contraseñas no coinciden", ValidacionCuenta.confirmacion("secreto123", "secreto124"))
    }

    @Test
    fun `login completo solo con ambos campos`() {
        assertTrue(ValidacionCuenta.loginCompleto("a@b.co", "x"))
        assertFalse(ValidacionCuenta.loginCompleto(" ", "x"))
        assertFalse(ValidacionCuenta.loginCompleto("a@b.co", ""))
    }
}
