package com.example.pruebas_fisicas.ui.recycler.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun RecyclerScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text("Hola esto es recycler")
    }
}


