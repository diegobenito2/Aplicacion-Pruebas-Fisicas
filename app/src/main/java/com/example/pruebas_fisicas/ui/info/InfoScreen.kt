// InfoScreen.kt ajustada para manejar datos desde SQLite
package com.example.pruebas_fisicas.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.pruebas_fisicas.BBDD.helpers.HelperdatosUsuario
import com.example.pruebas_fisicas.ui.login.ui.HeaderText
import com.example.pruebas_fisicas.ui.navigation.Recycler

@Composable
fun InfoScreen(navController: NavHostController, userId: Int) {

    val context = LocalContext.current
    val helperDatos = remember { HelperdatosUsuario(context) }
    var datos by remember {
        mutableStateOf(
            helperDatos.getDatosUsuarioPorId(userId) ?: DatosUsuario(
                0,
                0f,
                0f,
                "Hombre",
                userId
            )
        )
    }
    var enableSaveButton by rememberSaveable { mutableStateOf(false) }
    var enableimcbutton by rememberSaveable { mutableStateOf(false) }
    var showimc by rememberSaveable { mutableStateOf(false) }

    Box {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp)){
                Icon(
                    imageVector = (Icons.Default.ArrowBack),
                    "Atrás",
                    modifier = Modifier
                        .size(45.dp).align(Alignment.CenterVertically)
                        .padding(start = 20.dp)
                        .clickable { navController.popBackStack() }
                )
                HeaderText(
                    "Información Personal",
                    Modifier
                        .align(Alignment.CenterVertically).padding(start =30.dp)
                )
            }


            // Actualizamos solo el campo específico sin sobrescribir el objeto completo
            textFields("Edad", datos.edad.toString(), KeyboardType.Number) { edad ->
                datos = datos.copy(edad = edad.toIntOrNull() ?: 0)
                enableSaveButton = true
            }

            textFields("Peso(kg)", datos.peso.toString(), KeyboardType.Number) { peso ->
                datos = datos.copy(peso = peso.toFloatOrNull() ?: 0f)
                enableSaveButton = true
            }

            textFields("Altura(cm)", datos.altura.toString(), KeyboardType.Number) { altura ->
                datos = datos.copy(altura = altura.toFloatOrNull() ?: 0f)
                enableSaveButton = true
            }

            myDropDownMenu("Selecione el sexo", datos.sexo) { sexo ->
                datos = datos.copy(sexo = sexo) // Actualizamos solo el sexo
                enableSaveButton = true
            }
            datos = datos.copy(userId = userId)

            Spacer(Modifier.padding(top = 20.dp))

            Button(
                onClick = {
                    // Guardamos los datos completos (edad, peso, altura, sexo)
                    if (helperDatos.getDatosUsuarioPorId(userId) == null) {
                        helperDatos.insertarDatosUsuario(
                            datos.userId,
                            datos.edad,
                            datos.peso,
                            datos.altura,
                            datos.sexo
                        )
                    } else {
                        helperDatos.actualizarDatosUsuario(
                            datos.userId,
                            datos.edad,
                            datos.peso,
                            datos.altura,
                            datos.sexo
                        )
                    }
                    enableSaveButton = false
                },
                enabled = enableSaveButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                )
            ) {
                Text("Guardar Datos")
            }

            if (helperDatos.getDatosUsuarioPorId(userId) != null) {
                enableimcbutton = true
            }

            Button(
                onClick = {
                    showimc = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFBDBDBD),
                    disabledContentColor = Color(0xFF757575)
                ),
                enabled = enableimcbutton
            ) {
                Text("IMC")
            }

            if (showimc) {
                dialogIMC(imc = calcularIMC(helperDatos.getDatosUsuarioPorId(userId))) {
                    showimc = false
                }
            }

            Spacer(Modifier.padding(top = 20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 150.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f)) // Empuja el icono a la derecha

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Siguiente",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable {
                            navController.navigate(Recycler)
                        }
                )
            }

        }
    }
}

@Composable
fun textFields(
    label: String,
    value: String,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(value) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Done),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun myDropDownMenu(text: String, selected: String, onSelected: (String) -> Unit) {
    val options = listOf("Mujer", "Hombre")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selected) }
    Box {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            placeholder = { Text(text) },
            label = { Text("Sexo") },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // Bordes redondeados
                .background(MaterialTheme.colorScheme.surfaceVariant) // Fondo más elegante
                .clickable { expanded = true }
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ), // Espaciado interno para mejor apariencia
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary, // Borde resaltado al hacer focus
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
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(370.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedText = option
                        onSelected(option) // Llamamos a la función para actualizar el campo de sexo
                        expanded = false
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp, start = 10.dp)
                )
            }
        }
    }
}

// Cálculo del IMC
fun calcularIMC(datosUsuario: DatosUsuario?): Float {
    return if (datosUsuario!!.altura > 0) {
        datosUsuario.peso / (datosUsuario.altura / 100).let { it * it }
    } else {
        0f
    }
}

// Diálogo para mostrar el IMC
@Composable
fun dialogIMC(imc: Float, onDismiss: () -> Unit) {
    // Función que determina el estado del IMC
    fun estadoIMC(imc: Float): String {
        return when {
            imc < 18.5 -> "Bajo de peso"
            imc in 18.5..24.9 -> "Peso saludable"
            imc in 25.0..29.9 -> "Sobrepeso"
            imc >= 30.0 -> "Obesidad mórbida"
            else -> "Valor no válido"
        }
    }

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Column(
            Modifier
                .width(300.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(207, 159, 255))
        ) {
            Text(
                text = "Tu Índice de Masa Corporal es: %.2f".format(imc),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = estadoIMC(imc), // Se agrega el mensaje según el IMC
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cerrar")
            }
        }
    }
}




