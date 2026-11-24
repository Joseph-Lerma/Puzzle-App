package co.edu.univalle.puzzle.ui.acceso

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

/**
 * Pantalla 01 · Login (y 01b · Login · Error).
 * [alContinuarConGoogle] es opcional: el botón aparece cuando T-16 lo conecta.
 */
@Composable
fun PantallaLogin(
    vm: LoginViewModel,
    alEntrar: () -> Unit,
    alIrARegistro: () -> Unit,
    alIrARecuperar: () -> Unit,
    alJugarComoInvitado: () -> Unit,
    alContinuarConGoogle: (() -> Unit)? = null
) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    LaunchedEffect(estado.entro) { if (estado.entro) alEntrar() }
    ContenidoLogin(
        estado = estado,
        alCambiarCorreo = vm::cambiarCorreo,
        alCambiarContrasena = vm::cambiarContrasena,
        alEntrar = vm::entrar,
        alIrARegistro = alIrARegistro,
        alIrARecuperar = alIrARecuperar,
        alJugarComoInvitado = alJugarComoInvitado,
        alContinuarConGoogle = alContinuarConGoogle
    )
}

@Composable
fun ContenidoLogin(
    estado: EstadoLogin,
    alCambiarCorreo: (String) -> Unit,
    alCambiarContrasena: (String) -> Unit,
    alEntrar: () -> Unit,
    alIrARegistro: () -> Unit,
    alIrARecuperar: () -> Unit,
    alJugarComoInvitado: () -> Unit,
    alContinuarConGoogle: (() -> Unit)?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Puzzle", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)
            Text("Arma, compite y supera tus récords", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.height(8.dp))
        Text("Iniciar sesión", style = MaterialTheme.typography.titleLarge)

        CampoAcceso("Correo electrónico", estado.correo, alCambiarCorreo, "tu@correo.com", teclado = KeyboardType.Email)
        CampoAcceso("Contraseña", estado.contrasena, alCambiarContrasena, "••••••••", esContrasena = true)

        TextButton(onClick = alIrARecuperar, modifier = Modifier.align(Alignment.End)) {
            Text("¿Olvidaste tu contraseña?", fontWeight = FontWeight.SemiBold)
        }

        estado.error?.let { AvisoError(it) }

        BotonPrincipal("Entrar", habilitado = estado.puedeEntrar, cargando = estado.cargando, alPulsar = alEntrar)
        if (alContinuarConGoogle != null) {
            BotonSecundario("Continuar con Google", alContinuarConGoogle)
        }
        BotonSecundario("Jugar como invitado", alJugarComoInvitado)

        EnlaceInferior("¿No tienes cuenta?", "Regístrate", alIrARegistro)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "01 · Login")
@Composable
private fun VistaLogin() = PuzzleTheme {
    ContenidoLogin(EstadoLogin(correo = "tu@correo.com"), {}, {}, {}, {}, {}, {}, null)
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "01b · Login · Error")
@Composable
private fun VistaLoginError() = PuzzleTheme {
    ContenidoLogin(
        EstadoLogin(correo = "tu@correo.com", contrasena = "12345678", error = "Correo o contraseña incorrectos"),
        {}, {}, {}, {}, {}, {}, {}
    )
}
