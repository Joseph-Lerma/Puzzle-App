package co.edu.univalle.puzzle.ui.acceso

import co.edu.univalle.puzzle.dominio.cuenta.ErrorCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ResultadoCuenta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AccesoViewModelsTest {

    private val despachador = StandardTestDispatcher()

    @Before fun antes() = Dispatchers.setMain(despachador)
    @After fun despues() = Dispatchers.resetMain()

    // ---------- Login (RF-02) ----------

    @Test
    fun `login correcto entra a Inicio`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = LoginViewModel(servicio)
        vm.cambiarCorreo(" jugador@correo.com ")
        vm.cambiarContrasena("secreto123")
        vm.entrar()
        advanceUntilIdle()
        assertTrue(vm.estado.value.entro)
        assertEquals("jugador@correo.com" to "secreto123", servicio.ultimoLogin)
    }

    @Test
    fun `login incorrecto muestra el mensaje generico de 01b`() = runTest(despachador) {
        val vm = LoginViewModel(ServicioCuentaFalso(ResultadoCuenta.Error(ErrorCuenta.CREDENCIALES_INCORRECTAS)))
        vm.cambiarCorreo("jugador@correo.com")
        vm.cambiarContrasena("mala")
        vm.entrar()
        advanceUntilIdle()
        assertFalse(vm.estado.value.entro)
        assertEquals("Correo o contraseña incorrectos", vm.estado.value.error)
    }

    @Test
    fun `con sesion guardada entra directo`() {
        val vm = LoginViewModel(ServicioCuentaFalso(sesionIniciada = true))
        assertTrue(vm.estado.value.entro)
    }

    @Test
    fun `sin campos no llama al servicio`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = LoginViewModel(servicio)
        vm.entrar()
        advanceUntilIdle()
        assertNull(servicio.ultimoLogin)
    }

    @Test
    fun `entrar con Google usa el token`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = LoginViewModel(servicio)
        vm.entrarConGoogle("token-123")
        advanceUntilIdle()
        assertEquals("token-123", servicio.ultimoTokenGoogle)
        assertTrue(vm.estado.value.entro)
    }

    // ---------- Registro (RF-01) ----------

    private fun registroLleno(vm: RegistroViewModel) {
        vm.cambiarNombre("Jugador")
        vm.cambiarCorreo("jugador@correo.com")
        vm.cambiarContrasena("secreto123")
        vm.cambiarConfirmacion("secreto123")
        vm.cambiarMayorDeEdad(true)
        vm.cambiarAceptoPrivacidad(true)
    }

    @Test
    fun `registro valido crea la cuenta`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = RegistroViewModel(servicio)
        registroLleno(vm)
        vm.elegirAvatar(3)
        vm.crearCuenta()
        advanceUntilIdle()
        assertTrue(vm.estado.value.registrado)
        assertEquals(3, servicio.ultimoRegistro?.avatar)
        assertTrue(servicio.ultimoRegistro!!.aceptoPrivacidad)
    }

    @Test
    fun `sin aceptar el aviso no se puede crear la cuenta`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = RegistroViewModel(servicio)
        registroLleno(vm)
        vm.cambiarAceptoPrivacidad(false)
        assertFalse(vm.estado.value.puedeCrear)
        vm.crearCuenta()
        advanceUntilIdle()
        assertNull(servicio.ultimoRegistro)
    }

    @Test
    fun `sin ser mayor de edad no se puede crear la cuenta`() {
        val vm = RegistroViewModel(ServicioCuentaFalso())
        registroLleno(vm)
        vm.cambiarMayorDeEdad(false)
        assertFalse(vm.estado.value.puedeCrear)
    }

    @Test
    fun `campos invalidos muestran su error y no llaman al servicio`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = RegistroViewModel(servicio)
        registroLleno(vm)
        vm.cambiarNombre("Al")
        vm.cambiarContrasena("corta")
        vm.crearCuenta()
        advanceUntilIdle()
        assertEquals("De 3 a 20 caracteres", vm.estado.value.errorNombre)
        assertEquals("Mínimo 8 caracteres", vm.estado.value.errorContrasena)
        assertEquals("Las contraseñas no coinciden", vm.estado.value.errorConfirmacion)
        assertNull(servicio.ultimoRegistro)
    }

    @Test
    fun `correo repetido se muestra bajo el correo (02b)`() = runTest(despachador) {
        val vm = RegistroViewModel(ServicioCuentaFalso(ResultadoCuenta.Error(ErrorCuenta.CORREO_EN_USO)))
        registroLleno(vm)
        vm.crearCuenta()
        advanceUntilIdle()
        assertEquals("Este correo ya está registrado", vm.estado.value.errorCorreo)
        assertFalse(vm.estado.value.registrado)
    }

    @Test
    fun `nombre repetido se muestra bajo el nombre`() = runTest(despachador) {
        val vm = RegistroViewModel(ServicioCuentaFalso(ResultadoCuenta.Error(ErrorCuenta.NOMBRE_EN_USO)))
        registroLleno(vm)
        vm.crearCuenta()
        advanceUntilIdle()
        assertEquals(ErrorCuenta.NOMBRE_EN_USO.mensaje, vm.estado.value.errorNombre)
    }

    // ---------- Recuperar (RF-04) ----------

    @Test
    fun `recuperar envia y bloquea el reenvio 60 s`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = RecuperarViewModel(servicio)
        vm.cambiarCorreo("jugador@correo.com")
        vm.enviar()
        runCurrent()
        assertTrue(vm.estado.value.enviado)
        assertEquals(60, vm.estado.value.segundosParaReenviar)
        assertFalse(vm.estado.value.puedeReenviar)

        vm.enviar() // reenvío bloqueado
        runCurrent()
        assertEquals(1, servicio.recuperaciones)

        advanceTimeBy(60_001)
        assertEquals(0, vm.estado.value.segundosParaReenviar)
        assertTrue(vm.estado.value.puedeReenviar)
        vm.enviar()
        runCurrent()
        assertEquals(2, servicio.recuperaciones)
    }

    @Test
    fun `correo no registrado muestra el mismo mensaje`() = runTest(despachador) {
        val vm = RecuperarViewModel(ServicioCuentaFalso(ResultadoCuenta.Error(ErrorCuenta.DESCONOCIDO)))
        vm.cambiarCorreo("nadie@correo.com")
        vm.enviar()
        runCurrent()
        assertTrue(vm.estado.value.enviado)
        assertNull(vm.estado.value.error)
    }

    @Test
    fun `correo mal escrito no se envia`() = runTest(despachador) {
        val servicio = ServicioCuentaFalso()
        val vm = RecuperarViewModel(servicio)
        vm.cambiarCorreo("jugador@")
        vm.enviar()
        runCurrent()
        assertEquals(0, servicio.recuperaciones)
        assertEquals(ErrorCuenta.CORREO_INVALIDO.mensaje, vm.estado.value.error)
    }
}
