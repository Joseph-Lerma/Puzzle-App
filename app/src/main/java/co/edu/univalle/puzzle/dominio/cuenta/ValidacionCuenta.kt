package co.edu.univalle.puzzle.dominio.cuenta

/**
 * Reglas de los formularios de acceso (RF-01, RF-02, RF-04).
 * Cada función devuelve el mensaje de error que se muestra bajo el campo, o `null` si está bien.
 */
object ValidacionCuenta {

    const val NOMBRE_MIN = 3
    const val NOMBRE_MAX = 20
    const val CONTRASENA_MIN = 8

    private val patronCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val patronNombre = Regex("^[A-Za-z0-9ÁÉÍÓÚÜÑáéíóúüñ_ ]+$")

    fun nombre(valor: String): String? {
        val limpio = valor.trim()
        return when {
            limpio.length < NOMBRE_MIN || limpio.length > NOMBRE_MAX ->
                "De $NOMBRE_MIN a $NOMBRE_MAX caracteres"
            !patronNombre.matches(limpio) -> "Usa solo letras, números, espacios o _"
            else -> null
        }
    }

    fun correo(valor: String): String? =
        if (patronCorreo.matches(valor.trim())) null else ErrorCuenta.CORREO_INVALIDO.mensaje

    fun contrasena(valor: String): String? =
        if (valor.length >= CONTRASENA_MIN) null else "Mínimo $CONTRASENA_MIN caracteres"

    fun confirmacion(contrasena: String, confirmacion: String): String? =
        if (contrasena == confirmacion) null else "Las contraseñas no coinciden"

    /** En el login solo se revisa que los campos no estén vacíos (RF-02 no dice cuál falló). */
    fun loginCompleto(correo: String, contrasena: String): Boolean =
        correo.isNotBlank() && contrasena.isNotEmpty()
}
