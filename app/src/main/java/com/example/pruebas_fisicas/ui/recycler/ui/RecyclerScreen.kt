package com.example.pruebas_fisicas.ui.recycler.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pruebas_fisicas.ui.recycler.data.Prueba
import com.example.pruebas_fisicas.ui.recycler.data.listPruebas

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecyclerScreen(navController: NavHostController) {
    val rvState = rememberLazyListState()
    val pruebasList = listPruebas().groupBy { it.type }

    Scaffold { innerpadding ->
        val modifier: Modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)
        Column {
            LazyColumn(
                state = rvState,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                pruebasList.forEach { (type, prueba) ->
                    stickyHeader {

                        Text(
                            text = type,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                    items(prueba) { pruebaItem ->
                        itemPrueba(prueba = pruebaItem)
                    }
                }
            }
        }
    }
}

@Composable
fun itemPrueba(prueba: Prueba) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
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
                    color = Color.Black,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
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
                    fontSize = 14.sp,
                    color = Color.Blue
                )
                Text(
                    text = prueba.type,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
