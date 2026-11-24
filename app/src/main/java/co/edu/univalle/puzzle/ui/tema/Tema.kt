package co.edu.univalle.puzzle.ui.tema

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Morado,
    onPrimary = Color.White,
    primaryContainer = MoradoClaro,
    onPrimaryContainer = Morado,
    secondary = AmarilloPista,
    error = RojoError,
    background = Fondo,
    onBackground = TextoPrincipal,
    surface = Color.White,
    onSurface = TextoPrincipal,
    onSurfaceVariant = TextoSecundario
)

@Composable
fun PuzzleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaClaro,
        typography = Tipografia,
        content = content
    )
}
