package co.edu.univalle.puzzle.dominio.privacidad

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AvisoPrivacidadTest {

    @Test
    fun `el aviso cita la Ley 1581 y tiene sus tres secciones`() {
        assertEquals("Ley 1581 de 2012", AvisoPrivacidad.LEY)
        assertEquals(
            listOf("Qué datos usamos", "Para qué los usamos", "Tus derechos"),
            AvisoPrivacidad.secciones.map { it.titulo }
        )
        assertTrue(AvisoPrivacidad.secciones.all { it.texto.isNotBlank() })
    }

    @Test
    fun `el aviso informa que se puede eliminar la cuenta (RF-07)`() {
        assertTrue(AvisoPrivacidad.secciones.any { "eliminar tu cuenta" in it.texto })
    }

    @Test
    fun `aceptar crea el consentimiento con la version vigente`() {
        val c = AvisoPrivacidad.consentir(aceptoAviso = true, mayorDeEdad = true, ahoraMillis = 1_000L)
        assertEquals(AvisoPrivacidad.VERSION, c.version)
        assertEquals(1_000L, c.aceptadoEnMillis)
        assertTrue(AvisoPrivacidad.estaVigente(c))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `sin aceptar el aviso no hay consentimiento`() {
        AvisoPrivacidad.consentir(aceptoAviso = false, mayorDeEdad = true, ahoraMillis = 0L)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `sin declarar mayoria de edad no hay consentimiento`() {
        AvisoPrivacidad.consentir(aceptoAviso = true, mayorDeEdad = false, ahoraMillis = 0L)
    }

    @Test
    fun `un consentimiento de otra version no esta vigente`() {
        assertFalse(AvisoPrivacidad.estaVigente(AvisoPrivacidad.Consentimiento("2025-01", 0L, true)))
        assertFalse(AvisoPrivacidad.estaVigente(null))
    }
}
