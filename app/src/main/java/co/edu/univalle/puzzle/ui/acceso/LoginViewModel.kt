package co.edu.univalle.puzzle.ui.acceso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.edu.univalle.puzzle.dominio.cuenta.ResultadoCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ServicioCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ValidacionCuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Estado de la pantalla 01 · Login (y 01b con error). */
data class EstadoLogin(
    val correo: String = "",
    val contrasena: String = "",
    val cargando: Boolean = false,
    val error: String? = null,
    val entro: Boolean = false
) {
    val puedeEntrar: Boolean get() = ValidacionCuenta.loginCompleto(correo, contrasena) && !cargando
}

class LoginViewModel(private val servicio: ServicioCuenta) : ViewModel() {

    private val _estado = MutableStateFlow(EstadoLogin(entro = servicio.haySesion()))
    val estado: StateFlow<EstadoLogin> = _estado.asStateFlow()

    fun cambiarCorreo(valor: String) = _estado.update { it.copy(correo = valor, error = null) }

    fun cambiarContrasena(valor: String) = _estado.update { it.copy(contrasena = valor, error = null) }

    fun entrar() {
        val actual = _estado.value
        if (!actual.puedeEntrar) return
        ejecutar { servicio.iniciarSesion(actual.correo.trim(), actual.contrasena) }
    }

    /** Lo llama la navegación cuando el selector de Google entrega el token (T-16). */
    fun entrarConGoogle(idToken: String) = ejecutar { servicio.iniciarConGoogle(idToken) }

    fun mostrarError(mensaje: String) = _estado.update { it.copy(error = mensaje, cargando = false) }

    private fun ejecutar(accion: suspend () -> ResultadoCuenta) {
        _estado.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            when (val resultado = accion()) {
                ResultadoCuenta.Exito -> _estado.update { it.copy(cargando = false, entro = true) }
                is ResultadoCuenta.Error ->
                    _estado.update { it.copy(cargando = false, error = resultado.tipo.mensaje) }
            }
        }
    }

    companion object {
        fun fabrica(servicio: ServicioCuenta): ViewModelProvider.Factory = viewModelFactory {
            initializer { LoginViewModel(servicio) }
        }
    }
}
