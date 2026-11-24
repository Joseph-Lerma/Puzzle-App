package co.edu.univalle.puzzle.ui.navegacion

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.univalle.puzzle.ui.pantallas.PantallaAccesoProvisional
import co.edu.univalle.puzzle.ui.pantallas.PantallaEnConstruccion
import co.edu.univalle.puzzle.ui.pantallas.PantallaInicio
import co.edu.univalle.puzzle.ui.pantallas.amigos.PantallaAgregarAmigo
import co.edu.univalle.puzzle.ui.pantallas.amigos.PantallaMisAmigos
import co.edu.univalle.puzzle.ui.pantallas.fotos.PantallaNuevaFoto
import co.edu.univalle.puzzle.ui.pantallas.fotos.PantallaPermisoFotos
import co.edu.univalle.puzzle.ui.pantallas.fotos.PantallaRecortar
import co.edu.univalle.puzzle.ui.pantallas.misfotos.PantallaMisFotos
import co.edu.univalle.puzzle.ui.pantallas.modos.PantallaModos
import co.edu.univalle.puzzle.ui.pantallas.partida.PantallaPartida

/** Lleva a Inicio y borra las pantallas de acceso del historial. */
fun NavHostController.irAInicio() = navigate(Rutas.INICIO) {
    popUpTo(Rutas.LOGIN) { inclusive = true }
}

@Composable
fun NavegacionPuzzle() {
    val nav = rememberNavController()
    val volver: () -> Unit = { nav.popBackStack() }
    val crearCuenta: () -> Unit = { nav.navigate(Rutas.REGISTRO) }

    NavHost(navController = nav, startDestination = Rutas.LOGIN) {

        // ---------- Acceso (T-15 conecta aquí las pantallas de T-19 y T-65) ----------
        composable(Rutas.LOGIN) {
            PantallaAccesoProvisional(alEntrar = { nav.irAInicio() })
        }
        composable(Rutas.REGISTRO) { PantallaEnConstruccion("Crear cuenta", volver) }
        composable(Rutas.RECUPERAR) { PantallaEnConstruccion("Recuperar contraseña", volver) }
        composable(Rutas.PRIVACIDAD) { PantallaEnConstruccion("Aviso de privacidad", volver) }

        // ---------- Juego ----------
        composable(Rutas.INICIO) {
            PantallaInicio(alNavegar = { ruta -> nav.navigate(ruta) })
        }
        composable(Rutas.MODOS) {
            PantallaModos(
                alVolver = volver,
                alJugar = { modo, dificultad, imagenId ->
                    nav.navigate(Rutas.partida(modo, dificultad, imagenId))
                }
            )
        }
        composable(
            Rutas.PARTIDA,
            arguments = listOf(
                navArgument("modo") { type = NavType.StringType },
                navArgument("dificultad") { type = NavType.IntType },
                navArgument("imagenId") { type = NavType.StringType }
            )
        ) { entrada ->
            val args = entrada.arguments
            PantallaPartida(
                modo = args?.getString("modo").orEmpty(),
                dificultad = args?.getInt("dificultad") ?: 3,
                imagenId = Uri.decode(args?.getString("imagenId").orEmpty()),
                alVolver = volver
            )
        }

        // ---------- Fotos ----------
        composable(Rutas.MIS_FOTOS) {
            PantallaMisFotos(
                alAgregarFoto = { nav.navigate(Rutas.PERMISO_FOTOS) },
                alVolver = volver
            )
        }
        composable(Rutas.PERMISO_FOTOS) {
            PantallaPermisoFotos(
                alConcedido = {
                    nav.navigate(Rutas.NUEVA_FOTO) {
                        popUpTo(Rutas.PERMISO_FOTOS) { inclusive = true }
                    }
                },
                alVolver = volver
            )
        }
        composable(Rutas.NUEVA_FOTO) {
            PantallaNuevaFoto(
                alFotoGuardada = { fotoId ->
                    nav.navigate(Rutas.recortar(fotoId)) {
                        popUpTo(Rutas.NUEVA_FOTO) { inclusive = true }
                    }
                },
                alVolver = volver
            )
        }
        composable(
            Rutas.RECORTAR,
            arguments = listOf(navArgument("fotoId") { type = NavType.LongType })
        ) { entrada ->
            PantallaRecortar(
                fotoId = entrada.arguments?.getLong("fotoId") ?: 0L,
                alListo = volver,
                alVolver = volver
            )
        }

        // ---------- Secciones que requieren cuenta ----------
        composable(Rutas.AMIGOS) {
            SeccionProtegida("Amigos", crearCuenta, volver) {
                PantallaAgregarAmigo(
                    alVerAmigos = { nav.navigate(Rutas.MIS_AMIGOS) },
                    alVolver = volver
                )
            }
        }
        composable(Rutas.MIS_AMIGOS) {
            SeccionProtegida("Amigos", crearCuenta, volver) {
                PantallaMisAmigos(alAgregar = { nav.navigate(Rutas.AMIGOS) }, alVolver = volver)
            }
        }
        composable(Rutas.DUELO) {
            SeccionProtegida("Duelo VS", crearCuenta, volver) {
                PantallaEnConstruccion("Duelo VS", volver)
            }
        }
        composable(Rutas.RANKING) {
            SeccionProtegida("Ranking", crearCuenta, volver) {
                PantallaEnConstruccion("Ranking", volver)
            }
        }
        composable(Rutas.PERFIL) { PantallaEnConstruccion("Perfil", volver) }
        composable(Rutas.CONFIGURACION) { PantallaEnConstruccion("Configuración", volver) }
    }
}
