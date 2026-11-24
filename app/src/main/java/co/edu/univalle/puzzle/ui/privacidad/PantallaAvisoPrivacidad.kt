package co.edu.univalle.puzzle.ui.privacidad

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.univalle.puzzle.dominio.privacidad.AvisoPrivacidad
import co.edu.univalle.puzzle.ui.tema.AmarilloPista
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

/**
 * Pantalla 31 · Aviso de privacidad (RNF-17).
 * Se abre desde el registro («Ver») y, en el Sprint 2, desde Configuración.
 */
@Composable
fun PantallaAvisoPrivacidad(alEntendido: () -> Unit, alVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clickable(role = Role.Button, onClick = alVolver)
            ) { Text("←", style = MaterialTheme.typography.titleLarge) }
            Spacer(Modifier.width(12.dp))
            Column {
                Text("Aviso de privacidad", style = MaterialTheme.typography.titleLarge)
                Text(AvisoPrivacidad.LEY, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        AvisoPrivacidad.secciones.forEachIndexed { i, seccion ->
            TarjetaSeccion(numero = i + 1, seccion = seccion)
        }

        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(AmarilloPista.copy(alpha = 0.12f))
                .border(1.dp, AmarilloPista.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Text("Autorización", fontWeight = FontWeight.SemiBold)
            Text(AvisoPrivacidad.AUTORIZACION, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = alEntendido,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Entendido") }
        TextButton(onClick = alVolver, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Volver al registro")
        }
    }
}

@Composable
private fun TarjetaSeccion(numero: Int, seccion: AvisoPrivacidad.Seccion) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(24.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary)
            ) { Text("$numero", color = Color.White, style = MaterialTheme.typography.labelMedium) }
            Spacer(Modifier.width(12.dp))
            Text(seccion.titulo, fontWeight = FontWeight.SemiBold)
        }
        Text(seccion.texto, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800, name = "31 · Aviso de privacidad")
@Composable
private fun VistaAviso() = PuzzleTheme { PantallaAvisoPrivacidad({}, {}) }
