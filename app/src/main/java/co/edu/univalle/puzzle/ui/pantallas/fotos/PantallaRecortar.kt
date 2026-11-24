package co.edu.univalle.puzzle.ui.pantallas.fotos

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-47. */
@Composable
fun PantallaRecortar(fotoId: Long, alListo: () -> Unit, alVolver: () -> Unit) {
    PantallaEnConstruccion("Recortar foto", alVolver)
}
