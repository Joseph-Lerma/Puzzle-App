package co.edu.univalle.puzzle.ui.acceso

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

private val Redondeo = RoundedCornerShape(14.dp)

/** Campo con etiqueta arriba, ayuda o error abajo (diseño de 01, 02 y 21). */
@Composable
fun CampoAcceso(
    etiqueta: String,
    valor: String,
    alCambiar: (String) -> Unit,
    placeholder: String = "",
    ayuda: String? = null,
    error: String? = null,
    esContrasena: Boolean = false,
    teclado: KeyboardType = KeyboardType.Text
) {
    var visible by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth()) {
        Text(etiqueta, style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier.fillMaxWidth(),
            shape = Redondeo,
            singleLine = true,
            isError = error != null,
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else teclado
            ),
            visualTransformation = if (esContrasena && !visible) PasswordVisualTransformation()
            else VisualTransformation.None,
            trailingIcon = if (esContrasena) {
                { TextButton(onClick = { visible = !visible }) { Text(if (visible) "Ocultar" else "Ver") } }
            } else null
        )
        val nota = error ?: ayuda
        if (nota != null) {
            Text(
                nota,
                style = MaterialTheme.typography.bodySmall,
                color = if (error != null) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
            )
        }
    }
}

/** Botón morado de 52 dp de alto (RNF-20: mínimo 44). */
@Composable
fun BotonPrincipal(texto: String, habilitado: Boolean, cargando: Boolean = false, alPulsar: () -> Unit) {
    Button(
        onClick = alPulsar,
        enabled = habilitado && !cargando,
        shape = Redondeo,
        modifier = Modifier.fillMaxWidth().height(52.dp)
    ) {
        if (cargando) CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp, color = Color.White)
        else Text(texto)
    }
}

@Composable
fun BotonSecundario(texto: String, alPulsar: () -> Unit) {
    OutlinedButton(onClick = alPulsar, shape = Redondeo, modifier = Modifier.fillMaxWidth().height(52.dp)) {
        Text(texto)
    }
}

/** Casilla con texto; el texto también marca la casilla. */
@Composable
fun CasillaAcceso(texto: String, marcada: Boolean, alCambiar: (Boolean) -> Unit, enlace: Pair<String, () -> Unit>? = null) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Checkbox(checked = marcada, onCheckedChange = alCambiar)
        Text(
            texto,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable(role = Role.Checkbox) { alCambiar(!marcada) }
        )
        if (enlace != null) {
            TextButton(onClick = enlace.second) { Text(enlace.first) }
        }
    }
}

/** Mensaje de error general en un recuadro rojo (01b). */
@Composable
fun AvisoError(mensaje: String) {
    Text(
        mensaje,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .clip(Redondeo)
            .background(MaterialTheme.colorScheme.error.copy(alpha = 0.08f))
            .padding(12.dp)
    )
}

/** Texto + enlace en una línea («¿No tienes cuenta? Regístrate»). */
@Composable
fun EnlaceInferior(pregunta: String, accion: String, alPulsar: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(pregunta, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        TextButton(onClick = alPulsar) { Text(accion, fontWeight = FontWeight.SemiBold) }
    }
}

/** Colores de los avatares predefinidos (los dibujos llegan con el catálogo de T-71). */
val ColoresAvatar = listOf(
    Color(0xFF6C4CF1), Color(0xFFE0384C), Color(0xFFFFB020), Color(0xFF1FA67A),
    Color(0xFF2D8CFF), Color(0xFFEF6AA8), Color(0xFF7A5B3A), Color(0xFF3B3A4F)
)

/** Selector de avatar de la pantalla 02: círculos con la inicial del jugador. */
@Composable
fun SelectorAvatar(seleccionado: Int, inicial: Char, alElegir: (Int) -> Unit) {
    Column {
        Text("Elige un avatar", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            ColoresAvatar.forEachIndexed { i, color ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(color)
                        .then(
                            if (i == seleccionado) Modifier.border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                            else Modifier
                        )
                        .clickable(role = Role.RadioButton) { alElegir(i) }
                ) {
                    Text(inicial.uppercaseChar().toString(), color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/** Encabezado con botón «←» de 44 dp y título (02 y 21). */
@Composable
fun EncabezadoAcceso(titulo: String, subtitulo: String?, alVolver: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .clickable(role = Role.Button, onClick = alVolver)
        ) { Text("←", style = MaterialTheme.typography.titleLarge) }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(titulo, style = MaterialTheme.typography.titleLarge)
            if (subtitulo != null) {
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
