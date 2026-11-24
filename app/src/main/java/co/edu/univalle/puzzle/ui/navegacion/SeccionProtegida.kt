package co.edu.univalle.puzzle.ui.navegacion

import androidx.compose.runtime.Composable

/**
 * Envuelve las secciones que requieren cuenta (Amigos, Duelo VS, Ranking).
 * Provisional: muestra el contenido siempre. T-17 la reemplaza para bloquearlas
 * al invitado con la pantalla 26.
 */
@Composable
fun SeccionProtegida(
    titulo: String,
    alCrearCuenta: () -> Unit,
    alVolver: () -> Unit,
    contenido: @Composable () -> Unit
) {
    contenido()
}
