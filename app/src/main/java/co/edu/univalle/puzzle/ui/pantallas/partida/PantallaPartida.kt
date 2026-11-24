package co.edu.univalle.puzzle.ui.pantallas.partida

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-30. */
@Composable
fun PantallaPartida(modo: String, dificultad: Int, imagenId: String, alVolver: () -> Unit) {
    PantallaEnConstruccion("Partida", alVolver)
}
