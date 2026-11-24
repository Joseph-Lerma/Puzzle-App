package co.edu.univalle.puzzle.ui.pantallas.fotos

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-45. */
@Composable
fun PantallaPermisoFotos(alConcedido: () -> Unit, alVolver: () -> Unit) {
    PantallaEnConstruccion("Permiso de cámara y galería", alVolver)
}
