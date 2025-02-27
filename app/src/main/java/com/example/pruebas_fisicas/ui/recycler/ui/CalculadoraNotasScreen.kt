package com.example.pruebas_fisicas.ui.recycler.ui


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calcularNota
import com.example.pruebas_fisicas.BBDD.helpers.HelperNotasUsuarios
import com.example.pruebas_fisicas.BBDD.helpers.HelperdatosUsuario
import com.example.pruebas_fisicas.ui.info.ui.textFields
import com.example.pruebas_fisicas.ui.login.ui.HeaderText
import com.example.pruebas_fisicas.ui.recycler.data.NotaUsuarios
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CalculadoraNotasScreen(nombrePrueba: String, userId: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    val helperDatos = remember { HelperdatosUsuario(context) }
    val helperNotas = remember { HelperNotasUsuarios(context) }
    val datosUsuario = helperDatos.getDatosUsuarioPorId(userId)

    if (datosUsuario != null) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.clickable { onBack() },
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            HeaderText(nombrePrueba, Modifier.align(Alignment.CenterHorizontally))

            val notaFinal = ui(nombrePrueba, datosUsuario.edad, datosUsuario.sexo, context)
            println("Nota final: $notaFinal")
            helperNotas.insertarOActualizarNota(NotaUsuarios(nombrePrueba, notaFinal, userId))
        }
    }
}

@Composable
fun ui(nombrePrueba: String, edad: Int, sexo: String, context: Context): Float {
    var notaTexto by remember { mutableStateOf("") }
    var notaNumerica by remember { mutableStateOf(0f) }
    var notaFinal by remember { mutableStateOf(0f) }

    Column(modifier = Modifier.padding(16.dp)) {
        textFields("Introduce la marca", notaTexto, KeyboardType.Number) {
            notaTexto = it
            notaNumerica = it.toFloatOrNull() ?: 0f
        }
        Row(modifier = Modifier.padding(16.dp)) {

            Icon(
                Icons.Default.Check,
                contentDescription = "Calcular Nota",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        notaFinal = calcularNota(nombrePrueba, notaNumerica, edad, sexo)
                        Toast.makeText(context, "Guardado con exito", Toast.LENGTH_SHORT).show()
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
    return notaFinal
}
