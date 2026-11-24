package co.edu.univalle.puzzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.univalle.puzzle.ui.navegacion.NavegacionPuzzle
import co.edu.univalle.puzzle.ui.tema.PuzzleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PuzzleTheme {
                NavegacionPuzzle()
            }
        }
    }
}
