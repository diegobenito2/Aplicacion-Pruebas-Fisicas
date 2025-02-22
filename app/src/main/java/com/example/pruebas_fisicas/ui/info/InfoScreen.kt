package com.example.pruebas_fisicas.ui.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pruebas_fisicas.R

@Composable
fun InfoScreen(navController: NavHostController) {
    Box(
        Modifier.fillMaxSize()
    ) {
//        Icon(
//            painter = painterResource(R.drawable.atras),
//            "Atras",
//            modifier = Modifier
//                .align(Alignment.TopStart)
//                .size(20.dp)
//                .clickable { navController.popBackStack() })

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Información Personal",
                modifier = Modifier.padding(bottom = 40.dp),
                fontSize = 30.sp
            )
            textFields("Edad", "Introduce tu edad", KeyboardType.Number)
            textFields("Peso", "Introduce tu peso", KeyboardType.Number)
            textFields("Altura", "Introduce tu altura", KeyboardType.Number)
            myDropDownMenu("Genero")
            Row {
                buttons(icono = R.drawable.bmi, Modifier.size(20.dp)) { }
                buttons(icono = R.drawable.siguiente, Modifier.size(20.dp)) { }
            }


        }

    }

}

@Composable
fun textFields(texto: String, placeholder: String, keyboardType: KeyboardType) {

    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = texto, Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp),
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.padding(2.dp))
    var text by rememberSaveable { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp),
        enabled = true,
        readOnly = false,
        placeholder = { Text(placeholder) },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun myDropDownMenu(texto: String) {
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = texto, Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp),
        fontSize = 15.sp,
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.padding(2.dp))

    val options = listOf("Mujer", "Hombre")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = selectedText,
        onValueChange = {
            selectedText = it
        },
        placeholder = { Text("Seleccione un genero") },
        enabled = false,
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, start = 10.dp)
            .clickable {
                expanded = !expanded
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
                text = { Text(text = option) },
                onClick = {
                    selectedText = option
                    expanded = false
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp, start = 10.dp)
            )
        }
    }
}

@Composable
fun buttons(
    icono: Int,
    modifier: Modifier,
    function: () -> Unit
) {
    Button(onClick = { function() }, modifier = modifier, enabled = true) {
        Icon(painter = painterResource(icono), contentDescription = null)
    }
}






