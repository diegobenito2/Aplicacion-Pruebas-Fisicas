package com.example.pruebas_fisicas.ui.recycler.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import calcularNota
import com.example.pruebas_fisicas.BBDD.helpers.HelperdatosUsuario
import com.example.pruebas_fisicas.ui.info.textFields
import com.example.pruebas_fisicas.ui.recycler.data.NotaUsuarios
import com.example.pruebas_fisicas.ui.recycler.data.listPruebas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CalculadoraNotasScreen(nombrePrueba: String, userId: Int) {
    val context = LocalContext.current
    val helperDatos = remember { HelperdatosUsuario(context) }
    helperDatos.insertarDatosUsuario(1, 16, 65f, 182f, "Hombre")
    val datosUsuario = helperDatos.getDatosUsuarioPorId(userId)
    println("userId: $userId" + " " + datosUsuario?.edad + " " + datosUsuario?.sexo + " " + datosUsuario?.peso + " " + datosUsuario?.altura)
    if (datosUsuario != null) {
        Column(modifier = Modifier.fillMaxSize()) {
            myDropDownMenuPruebas(
                text = "Seleccione una prueba",
                options = listPruebas(datosUsuario.edad).map { it.Nombre },
                selected = nombrePrueba,
                onSelected = {
                    Spacer(Modifier.padding(top = 50.dp))
                    pedirnotas(it, datosUsuario.edad, datosUsuario.sexo, userId)
                }
            )
        }

    }
}

@Composable
fun pedirnotas(nombrePrueba: String, edad: Int, sexo: String, userId: Int): NotaUsuarios {
    return NotaUsuarios(nombrePrueba, ui(nombrePrueba, edad, sexo), userId)
}

@Composable
fun ui(nombrePrueba: String, edad: Int, sexo: String): String {
    var notaTexto by remember { mutableStateOf("") }
    var notaNumerica by remember { mutableStateOf(0f) }
    var notaFinal by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var showTextField by remember { mutableStateOf(true) }
    Spacer(Modifier.padding(top = 50.dp))
    Column {
        if (showTextField) {

            textFields("Introduce la nota", notaTexto, KeyboardType.Number) {
                notaTexto = it
                notaNumerica = it.toFloatOrNull() ?: 0f
            }
            Icon(
                Icons.Default.Check,
                contentDescription = "Calcular Nota",
                modifier = Modifier.clickable {
                    notaFinal = calcularNota(nombrePrueba, notaNumerica, edad, sexo)
                    coroutineScope.launch {
                        delay(2000)
                        showTextField = false
                    }
                },
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = "Nota: $notaFinal")
        }
    }

    return notaFinal
}

@Composable
fun myDropDownMenuPruebas(
    text: String,
    options: List<String>,
    selected: String,
    onSelected: @Composable (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selected) }
    var selectedIndex by remember { mutableStateOf(options.indexOf(selected)) }
    Box {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            placeholder = { Text(text) },
            label = { Text("Prueba") },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 4.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                disabledIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(370.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        selectedIndex =
                            options.indexOf(option) // Actualiza el índice de la opción seleccionada
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
            }
        }
        if (selectedIndex != -1) {
            onSelected(options[selectedIndex])// Llama a la función con la opción seleccionada
        }
        // Se realiza está llamada a la función fuera del clickable porque sino no tiene el contexto composable y da error.
    }
}
