package co.edu.univalle.puzzle.ui.acceso

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import co.edu.univalle.puzzle.dominio.cuenta.DatosRegistro
import co.edu.univalle.puzzle.dominio.cuenta.ErrorCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ResultadoCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ServicioCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ValidacionCuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** Estado de la pantalla 02 · Registro (y 02b con error). */
data class EstadoRegistro(
    val avatar: Int = 0,
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmacion: String = "",
    val mayorDeEdad: Boolean = false,
    val aceptoPrivacidad: Boolean = false,
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmacion: String? = null,
    val errorGeneral: String? = null,
    val cargando: Boolean = false,
    val registrado: Boolean = false
) {
    /** El botón solo se activa con ambas casillas marcadas (RF-01, RNF-17). */
    val puedeCrear: Boolean get() = mayorDeEdad && aceptoPrivacidad && !cargando
}

class RegistroViewModel(private val servicio: ServicioCuenta) : ViewModel() {

    private val _estado = MutableStateFlow(EstadoRegistro())
    val estado: StateFlow<EstadoRegistro> = _estado.asStateFlow()

    fun elegirAvatar(indice: Int) = _estado.update { it.copy(avatar = indice) }
    fun cambiarNombre(v: String) = _estado.update { it.copy(nombre = v, errorNombre = null, errorGeneral = null) }
    fun cambiarCorreo(v: String) = _estado.update { it.copy(correo = v, errorCorreo = null, errorGeneral = null) }
    fun cambiarContrasena(v: String) = _estado.update { it.copy(contrasena = v, errorContrasena = null) }
    fun cambiarConfirmacion(v: String) = _estado.update { it.copy(confirmacion = v, errorConfirmacion = null) }
    fun cambiarMayorDeEdad(v: Boolean) = _estado.update { it.copy(mayorDeEdad = v) }
    fun cambiarAceptoPrivacidad(v: Boolean) = _estado.update { it.copy(aceptoPrivacidad = v) }

    fun crearCuenta() {
        val e = _estado.value
        if (!e.puedeCrear) return
        val conErrores = e.copy(
            errorNombre = ValidacionCuenta.nombre(e.nombre),
            errorCorreo = ValidacionCuenta.correo(e.correo),
            errorContrasena = ValidacionCuenta.contrasena(e.contrasena),
            errorConfirmacion = ValidacionCuenta.confirmacion(e.contrasena, e.confirmacion),
            errorGeneral = null
        )
        if (listOf(conErrores.errorNombre, conErrores.errorCorreo, conErrores.errorContrasena,
                conErrores.errorConfirmacion).any { it != null }
        ) {
            _estado.value = conErrores
            return
        }
        _estado.value = conErrores.copy(cargando = true)
        viewModelScope.launch {
            val resultado = servicio.registrar(
                DatosRegistro(
                    nombre = e.nombre.trim(),
                    correo = e.correo.trim(),
                    contrasena = e.contrasena,
                    avatar = e.avatar,
                    mayorDeEdad = e.mayorDeEdad,
                    aceptoPrivacidad = e.aceptoPrivacidad
                )
            )
            _estado.update { actual ->
                when (resultado) {
                    ResultadoCuenta.Exito -> actual.copy(cargando = false, registrado = true)
                    is ResultadoCuenta.Error -> conError(actual.copy(cargando = false), resultado.tipo)
                }
            }
        }
    }

    /** Coloca el error bajo el campo que corresponde (pantalla 02b). */
    private fun conError(e: EstadoRegistro, tipo: ErrorCuenta): EstadoRegistro = when (tipo) {
        ErrorCuenta.CORREO_EN_USO, ErrorCuenta.CORREO_INVALIDO -> e.copy(errorCorreo = tipo.mensaje)
        ErrorCuenta.NOMBRE_EN_USO -> e.copy(errorNombre = tipo.mensaje)
        ErrorCuenta.CONTRASENA_DEBIL -> e.copy(errorContrasena = tipo.mensaje)
        else -> e.copy(errorGeneral = tipo.mensaje)
    }

    companion object {
        fun fabrica(servicio: ServicioCuenta): ViewModelProvider.Factory = viewModelFactory {
            initializer { RegistroViewModel(servicio) }
        }
    }
}
