package co.edu.univalle.puzzle.ui.acceso

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

/** Pantalla 02 · Registro (y 02b · Registro · Error). */
@Composable
fun PantallaRegistro(
    vm: RegistroViewModel,
    alRegistrado: () -> Unit,
    alVolver: () -> Unit,
    alVerPrivacidad: () -> Unit,
    alContinuarConGoogle: (() -> Unit)? = null
) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    LaunchedEffect(estado.registrado) { if (estado.registrado) alRegistrado() }
    ContenidoRegistro(
        estado = estado,
        acciones = AccionesRegistro(
            elegirAvatar = vm::elegirAvatar,
            cambiarNombre = vm::cambiarNombre,
            cambiarCorreo = vm::cambiarCorreo,
            cambiarContrasena = vm::cambiarContrasena,
            cambiarConfirmacion = vm::cambiarConfirmacion,
            cambiarMayorDeEdad = vm::cambiarMayorDeEdad,
            cambiarAceptoPrivacidad = vm::cambiarAceptoPrivacidad,
            crearCuenta = vm::crearCuenta,
            volver = alVolver,
            verPrivacidad = alVerPrivacidad,
            continuarConGoogle = alContinuarConGoogle
        )
    )
}

class AccionesRegistro(
    val elegirAvatar: (Int) -> Unit = {},
    val cambiarNombre: (String) -> Unit = {},
    val cambiarCorreo: (String) -> Unit = {},
    val cambiarContrasena: (String) -> Unit = {},
    val cambiarConfirmacion: (String) -> Unit = {},
    val cambiarMayorDeEdad: (Boolean) -> Unit = {},
    val cambiarAceptoPrivacidad: (Boolean) -> Unit = {},
    val crearCuenta: () -> Unit = {},
    val volver: () -> Unit = {},
    val verPrivacidad: () -> Unit = {},
    val continuarConGoogle: (() -> Unit)? = null
)

@Composable
fun ContenidoRegistro(estado: EstadoRegistro, acciones: AccionesRegistro) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        EncabezadoAcceso("Crear cuenta", "Necesaria para amigos, duelos y ranking", acciones.volver)

        SelectorAvatar(estado.avatar, estado.nombre.trim().firstOrNull() ?: 'J', acciones.elegirAvatar)

        CampoAcceso("Nombre de jugador", estado.nombre, acciones.cambiarNombre,
            ayuda = "De 3 a 20 caracteres", error = estado.errorNombre)
        CampoAcceso("Correo electrónico", estado.correo, acciones.cambiarCorreo, "tu@correo.com",
            error = estado.errorCorreo, teclado = KeyboardType.Email)
        CampoAcceso("Contraseña", estado.contrasena, acciones.cambiarContrasena,
            ayuda = "Mínimo 8 caracteres", error = estado.errorContrasena, esContrasena = true)
        CampoAcceso("Confirmar contraseña", estado.confirmacion, acciones.cambiarConfirmacion,
            "Repite tu contraseña", error = estado.errorConfirmacion, esContrasena = true)

        CasillaAcceso("Soy mayor de 18 años", estado.mayorDeEdad, acciones.cambiarMayorDeEdad)
        CasillaAcceso(
            "Acepto el aviso de privacidad", estado.aceptoPrivacidad, acciones.cambiarAceptoPrivacidad,
            enlace = "Ver" to acciones.verPrivacidad
        )

        estado.errorGeneral?.let { AvisoError(it) }

        BotonPrincipal("Crear cuenta", habilitado = estado.puedeCrear, cargando = estado.cargando,
            alPulsar = acciones.crearCuenta)
        acciones.continuarConGoogle?.let { BotonSecundario("Registrarse con Google", it) }

        EnlaceInferior("¿Ya tienes cuenta?", "Inicia sesión", acciones.volver)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 900, name = "02 · Registro")
@Composable
private fun VistaRegistro() = PuzzleTheme {
    ContenidoRegistro(EstadoRegistro(nombre = "Jugador", mayorDeEdad = true, aceptoPrivacidad = true), AccionesRegistro())
}

@Preview(showBackground = true, widthDp = 360, heightDp = 900, name = "02b · Registro · Error")
@Composable
private fun VistaRegistroError() = PuzzleTheme {
    ContenidoRegistro(
        EstadoRegistro(nombre = "Jugador", correo = "tu@correo.com", errorCorreo = "Este correo ya está registrado",
            mayorDeEdad = true, aceptoPrivacidad = true),
        AccionesRegistro()
    )
}
