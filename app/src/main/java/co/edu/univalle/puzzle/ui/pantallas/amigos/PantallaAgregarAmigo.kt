package co.edu.univalle.puzzle.ui.pantallas.amigos

import androidx.compose.runtime.Composable
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion

/** Pantalla provisional: la reemplaza la tarjeta T-51 y T-52. */
@Composable
fun PantallaAgregarAmigo(alVerAmigos: () -> Unit, alVolver: () -> Unit) {
    PantallaEnConstruccion("Agregar amigo", alVolver)
}
