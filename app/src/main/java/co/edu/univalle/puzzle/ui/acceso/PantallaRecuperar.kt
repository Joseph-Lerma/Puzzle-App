package co.edu.univalle.puzzle.ui.acceso

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

/** Pantalla 21 · Recuperar contraseña (RF-04). */
@Composable
fun PantallaRecuperar(vm: RecuperarViewModel, alVolver: () -> Unit) {
    val estado by vm.estado.collectAsStateWithLifecycle()
    ContenidoRecuperar(estado, vm::cambiarCorreo, vm::enviar, alVolver)
}

@Composable
fun ContenidoRecuperar(
    estado: EstadoRecuperar,
    alCambiarCorreo: (String) -> Unit,
    alEnviar: () -> Unit,
    alVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoAcceso("¿Olvidaste tu contraseña?", null, alVolver)
        Text(
            "Escribe tu correo y te enviaremos un enlace para crear una nueva.",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        CampoAcceso("Correo electrónico", estado.correo, alCambiarCorreo, "jugador@correo.com",
            error = estado.error, teclado = KeyboardType.Email)

        if (!estado.enviado) {
            BotonPrincipal("Enviar enlace", habilitado = estado.correo.isNotBlank(), cargando = estado.cargando,
                alPulsar = alEnviar)
        } else {
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("✓ ¡Revisa tu correo!", fontWeight = FontWeight.SemiBold)
                Text(
                    "Enviamos el enlace a ${estado.correo.trim()}. Puede tardar unos minutos.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TextButton(onClick = alEnviar, enabled = estado.puedeReenviar, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                val s = estado.segundosParaReenviar
                Text(if (s > 0) "¿No te llegó? Reenviar en 0:${s.toString().padStart(2, '0')}" else "¿No te llegó? Reenviar")
            }
        }
        EnlaceInferior("¿Recordaste tu contraseña?", "Inicia sesión", alVolver)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "21 · Recuperar")
@Composable
private fun VistaRecuperar() = PuzzleTheme {
    ContenidoRecuperar(EstadoRecuperar(correo = "jugador@correo.com", enviado = true, segundosParaReenviar = 45), {}, {}, {})
}
