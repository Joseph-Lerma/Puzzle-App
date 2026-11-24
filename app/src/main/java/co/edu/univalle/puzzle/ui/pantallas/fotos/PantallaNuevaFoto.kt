package co.edu.univalle.puzzle.ui.pantallas.fotos

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-46. */
@Composable
fun PantallaNuevaFoto(alFotoGuardada: (fotoId: Long) -> Unit, alVolver: () -> Unit) {
    PantallaEnConstruccion("Nueva foto", alVolver)
}
