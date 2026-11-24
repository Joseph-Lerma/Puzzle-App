package co.edu.univalle.puzzle.ui.pantallas.amigos

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-53. */
@Composable
fun PantallaMisAmigos(alAgregar: () -> Unit, alVolver: () -> Unit) {
    PantallaEnConstruccion("Mis amigos", alVolver)
}
