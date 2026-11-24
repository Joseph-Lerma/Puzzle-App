package co.edu.univalle.puzzle.ui.acceso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.edu.univalle.puzzle.dominio.cuenta.ErrorCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ResultadoCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ServicioCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ValidacionCuenta
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Estado de la pantalla 21 · Recuperar contraseña. */
data class EstadoRecuperar(
    val correo: String = "",
    val error: String? = null,
    val cargando: Boolean = false,
    val enviado: Boolean = false,
    /** Segundos que faltan para poder reenviar (RF-04: 60 s). */
    val segundosParaReenviar: Int = 0
) {
    val puedeReenviar: Boolean get() = enviado && segundosParaReenviar == 0 && !cargando
}

class RecuperarViewModel(private val servicio: ServicioCuenta) : ViewModel() {

    private val _estado = MutableStateFlow(EstadoRecuperar())
    val estado: StateFlow<EstadoRecuperar> = _estado.asStateFlow()
    private var cuentaRegresiva: Job? = null

    fun cambiarCorreo(v: String) = _estado.update { it.copy(correo = v, error = null) }

    fun enviar() {
        val e = _estado.value
        if (e.cargando || (e.enviado && !e.puedeReenviar)) return
        ValidacionCuenta.correo(e.correo)?.let { mensaje ->
            _estado.update { it.copy(error = mensaje) }
            return
        }
        _estado.update { it.copy(cargando = true, error = null) }
        viewModelScope.launch {
            val resultado = servicio.enviarRecuperacion(e.correo.trim())
            // Solo la falta de conexión se muestra como error: cualquier otra respuesta
            // muestra el mismo mensaje para no revelar si la cuenta existe (RF-04).
            if (resultado is ResultadoCuenta.Error && resultado.tipo == ErrorCuenta.SIN_CONEXION) {
                _estado.update { it.copy(cargando = false, error = resultado.tipo.mensaje) }
            } else {
                _estado.update { it.copy(cargando = false, enviado = true) }
                iniciarCuentaRegresiva()
            }
        }
    }

    private fun iniciarCuentaRegresiva() {
        cuentaRegresiva?.cancel()
        cuentaRegresiva = viewModelScope.launch {
            for (s in ESPERA_REENVIO downTo 1) {
                _estado.update { it.copy(segundosParaReenviar = s) }
                delay(1_000)
            }
            _estado.update { it.copy(segundosParaReenviar = 0) }
        }
    }

    companion object {
        const val ESPERA_REENVIO = 60

        fun fabrica(servicio: ServicioCuenta): ViewModelProvider.Factory = viewModelFactory {
            initializer { RecuperarViewModel(servicio) }
        }
    }
}
