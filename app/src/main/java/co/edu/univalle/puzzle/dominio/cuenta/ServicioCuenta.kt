package co.edu.univalle.puzzle.dominio.cuenta

/**
 * Operaciones de cuenta que usan las pantallas de acceso (T-19).
 *
 * La implementación con Firebase la hace T-15 (correo), T-18 (recuperación) y T-16 (Google).
 * Las pantallas solo conocen esta interfaz, así se pueden probar sin Firebase.
 */
interface ServicioCuenta {

    /** Verdadero si ya hay una sesión iniciada (RF-02: la sesión se mantiene al reabrir). */
    fun haySesion(): Boolean

    /** RF-01: crea la cuenta con correo y contraseña. */
    suspend fun registrar(datos: DatosRegistro): ResultadoCuenta

    /** RF-02: inicia sesión con correo y contraseña. */
    suspend fun iniciarSesion(correo: String, contrasena: String): ResultadoCuenta

    /**
     * RF-04: envía el enlace de recuperación.
     * Debe responder [ResultadoCuenta.Exito] aunque el correo no esté registrado,
     * para no revelar si la cuenta existe.
     */
    suspend fun enviarRecuperacion(correo: String): ResultadoCuenta

    /** RF-01 y RF-02 con Google: recibe el token que entrega el selector de cuentas (T-16). */
    suspend fun iniciarConGoogle(idToken: String): ResultadoCuenta
}

/** Datos del formulario de registro (pantalla 02). */
data class DatosRegistro(
    val nombre: String,
    val correo: String,
    val contrasena: String,
    val avatar: Int,
    val mayorDeEdad: Boolean,
    val aceptoPrivacidad: Boolean
)

sealed interface ResultadoCuenta {
    data object Exito : ResultadoCuenta
    data class Error(val tipo: ErrorCuenta) : ResultadoCuenta
}

enum class ErrorCuenta(val mensaje: String) {
    CREDENCIALES_INCORRECTAS("Correo o contraseña incorrectos"),
    CORREO_EN_USO("Este correo ya está registrado"),
    NOMBRE_EN_USO("Ese nombre de jugador ya está en uso"),
    CONTRASENA_DEBIL("La contraseña debe tener al menos 8 caracteres"),
    CORREO_INVALIDO("Escribe un correo válido"),
    SIN_CONEXION("Sin conexión. Revisa tu internet e inténtalo de nuevo"),
    CANCELADO("Se canceló el inicio de sesión"),
    DESCONOCIDO("Ocurrió un error. Inténtalo de nuevo")
}
