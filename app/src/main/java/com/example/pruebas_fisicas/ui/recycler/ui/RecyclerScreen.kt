package com.example.pruebas_fisicas.ui.recycler.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebas_fisicas.BBDD.helpers.HelperdatosUsuario
import com.example.pruebas_fisicas.ui.recycler.data.Prueba
import com.example.pruebas_fisicas.ui.recycler.data.listPruebas


@Composable
fun RecyclerScreen(
    navigationToInfoS: (Int) -> Unit,
    navigationToCalculoNotas: (String) -> Unit,
    userId:Int) {
    val context = LocalContext.current
    val helperDatos = remember { HelperdatosUsuario(context) }
    val user= helperDatos.getDatosUsuarioPorId(userId)
    val pruebasList = listPruebas(user?.edad ?: 16) //Coge la lista de edades con la edad del usuario y si la edad es nula usa 16 por defecto
    val pruebasListGrouped = pruebasList.groupBy { it.type } // Agrupamos por categorías
    val categories = pruebasListGrouped.keys.toList() // Obtenemos las categorías para las agrupaciones

    // Estado de búsqueda
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) } // Estado para almacenar la categoría seleccionada

    // Filtrar las pruebas según el nombre y la categoría seleccionada
    val filteredPruebas = pruebasList.filter {
        (it.Nombre.contains(
            searchQuery,
            ignoreCase = true
        )) && (selectedCategory == null || it.type == selectedCategory)
    }

    Column(Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = (Icons.Default.ArrowBack),
                "Atrás",
                modifier = Modifier
                    .size(45.dp)
                    .padding(start = 20.dp)
                    .clickable { navigationToInfoS(userId) }
            )
            // Campo de búsqueda
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar por nombre de prueba") },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp) // El campo de búsqueda ocupará el espacio restante
            )

            // Menú de categorías
            var expanded by remember { mutableStateOf(false) }

            Box {
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menú de categorías")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(text = category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("Mostrar todas") },
                        onClick = {
                            selectedCategory = null
                            expanded = false
                        }
                    )
                }
            }
        }

        // Mostrar la lista filtrada de pruebas
        LazyColumn(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            items(filteredPruebas) { pruebaItem ->
                itemPrueba(prueba = pruebaItem) {
                    navigationToCalculoNotas(pruebaItem.Nombre)
                }
            }
        }
    }

}
//Item de prueba en la lista
@Composable
fun itemPrueba(prueba: Prueba, onItemSelected: (Prueba) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemSelected(prueba) },
        elevation = CardDefaults.cardElevation(8.dp),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column {
            Image(
                painter = painterResource(prueba.imagen),
                contentDescription = prueba.Nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(12.dp)) {
                val context = LocalContext.current
                Text(
                    text = prueba.Nombre,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Descripción",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(prueba.enlace))
                            context.startActivity(intent)
                        }
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 14.sp
                )
            }
        }
    }
}
