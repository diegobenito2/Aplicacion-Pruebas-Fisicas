package com.example.pruebas_fisicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pruebas_fisicas.ui.navigation.NavegationWrapper
import com.example.pruebas_fisicas.ui.recycler.ui.RecyclerScreen
import com.example.pruebas_fisicas.ui.theme.Pruebas_FisicasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pruebas_FisicasTheme {
//                ThemeSwitcherApp()
                RecyclerScreen(rememberNavController())
            }
        }
    }
}

@Composable
fun ThemeSwitcherApp() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()

    // Switch flotante arriba a la derecha
    Pruebas_FisicasTheme(darkTheme = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { isDarkTheme = !isDarkTheme }
            )
        }
        NavegationWrapper(navController)
    }

}


