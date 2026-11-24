package co.edu.univalle.puzzle.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.univalle.puzzle.ui.navegacion.Rutas
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

@Composable
fun PantallaInicio(alNavegar: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Puzzle",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Rompecabezas deslizante",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { alNavegar(Rutas.MODOS) },
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Jugar") }

        val secciones = listOf(
            "Duelo VS" to Rutas.DUELO,
            "Mis fotos" to Rutas.MIS_FOTOS,
            "Ranking" to Rutas.RANKING,
            "Amigos" to Rutas.AMIGOS,
            "Perfil" to Rutas.PERFIL,
            "Configuración" to Rutas.CONFIGURACION
        )
        secciones.forEach { (texto, ruta) ->
            OutlinedButton(
                onClick = { alNavegar(ruta) },
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) { Text(texto) }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun VistaPreviaInicio() {
    PuzzleTheme { PantallaInicio {} }
}
