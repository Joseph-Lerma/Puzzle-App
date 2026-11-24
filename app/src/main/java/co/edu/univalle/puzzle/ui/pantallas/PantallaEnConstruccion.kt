package co.edu.univalle.puzzle.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

/** Barra superior común con título y botón para volver. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(titulo: String, alVolver: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(titulo) },
        navigationIcon = {
            TextButton(onClick = alVolver) { Text("‹ Volver") }
        }
    )
}

/** Pantalla provisional para las secciones que aún no se implementan. */
@Composable
fun PantallaEnConstruccion(titulo: String, alVolver: () -> Unit) {
    Scaffold(topBar = { BarraSuperior(titulo, alVolver) }) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pantalla en construcción",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun VistaPreviaEnConstruccion() {
    PuzzleTheme { PantallaEnConstruccion("Ranking") {} }
}
