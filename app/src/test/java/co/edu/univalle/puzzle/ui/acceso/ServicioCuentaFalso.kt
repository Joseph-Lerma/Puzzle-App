package co.edu.univalle.puzzle.ui.acceso

import co.edu.univalle.puzzle.dominio.cuenta.DatosRegistro
import co.edu.univalle.puzzle.dominio.cuenta.ResultadoCuenta
import co.edu.univalle.puzzle.dominio.cuenta.ServicioCuenta

/** Servicio de cuenta en memoria para probar los ViewModels sin Firebase. */
class ServicioCuentaFalso(
    var respuesta: ResultadoCuenta = ResultadoCuenta.Exito,
    private val sesionIniciada: Boolean = false
) : ServicioCuenta {
    var ultimoRegistro: DatosRegistro? = null
    var ultimoLogin: Pair<String, String>? = null
    var recuperaciones = 0
    var ultimoTokenGoogle: String? = null

    override fun haySesion() = sesionIniciada

    override suspend fun registrar(datos: DatosRegistro): ResultadoCuenta {
        ultimoRegistro = datos
        return respuesta
    }

    override suspend fun iniciarSesion(correo: String, contrasena: String): ResultadoCuenta {
        ultimoLogin = correo to contrasena
        return respuesta
    }

    override suspend fun enviarRecuperacion(correo: String): ResultadoCuenta {
        recuperaciones++
        return respuesta
    }

    override suspend fun iniciarConGoogle(idToken: String): ResultadoCuenta {
        ultimoTokenGoogle = idToken
        return respuesta
    }
}
