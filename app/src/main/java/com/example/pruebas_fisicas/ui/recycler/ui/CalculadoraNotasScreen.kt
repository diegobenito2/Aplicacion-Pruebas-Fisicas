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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calcularNota
import com.example.pruebas_fisicas.BBDD.helpers.HelperNotasUsuarios
import com.example.pruebas_fisicas.BBDD.helpers.HelperdatosUsuario
import com.example.pruebas_fisicas.ui.info.ui.activarBotonNotas
import com.example.pruebas_fisicas.ui.info.ui.textFields
import com.example.pruebas_fisicas.ui.login.ui.HeaderText
import com.example.pruebas_fisicas.ui.recycler.data.NotaUsuarios

@Composable
fun CalculadoraNotasScreen(
    nombrePrueba: String,
    userId: Int,
    onBack: () -> Unit,
    onResultadoNotas: () -> Unit
) {
    val context = LocalContext.current
    val helperDatos = remember { HelperdatosUsuario(context) }
    val helperNotas = remember { HelperNotasUsuarios(context) }
    val datosUsuario = helperDatos.getDatosUsuarioPorId(userId)

    // Estado para guardar la nota final y actualizarla dinámicamente
    var notaFinal by remember { mutableStateOf(0f) }

    if (datosUsuario != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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

            // Pasamos la función para actualizar la nota
            ui(nombrePrueba, datosUsuario.edad, datosUsuario.sexo, context,{ nuevaNota ->
                notaFinal = nuevaNota
                helperNotas.insertarOActualizarNota(NotaUsuarios(nombrePrueba, nuevaNota, userId))
            },userId,onResultadoNotas)

            // Mostrar la nota final en la pantalla
            Text(
                text = "Nota: $notaFinal",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun ui(
    nombrePrueba: String,
    edad: Int,
    sexo: String,
    context: Context,
    onNotaCalculada: (Float) -> Unit,
    userId: Int,
    onResultadoNotas: () -> Unit
) {
    var notaTexto by remember { mutableStateOf("") }
    var notaNumerica by remember { mutableStateOf(0f) }

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
                        val notaFinal = calcularNota(nombrePrueba, notaNumerica, edad, sexo)
                        onNotaCalculada(notaFinal) // Ahora actualiza la nota en la pantalla principal
                        Toast.makeText(context, "Guardado con éxito", Toast.LENGTH_SHORT).show()
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Button(
            onClick = {
                onResultadoNotas()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(end = 75.dp, start = 75.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EE),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFBDBDBD),
                disabledContentColor = Color(0xFF757575)
            ),
            enabled = activarBotonNotas(context, userId)
//
        ) {
            Text("Ver todas las notas")
        }
    }
}
