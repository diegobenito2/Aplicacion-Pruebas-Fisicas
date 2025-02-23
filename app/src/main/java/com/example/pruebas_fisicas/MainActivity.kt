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
import androidx.navigation.compose.*
import com.example.pruebas_fisicas.ui.navigation.NavegationWrapper
import com.example.pruebas_fisicas.ui.theme.Pruebas_FisicasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegationWrapper()
        }
    }
}

@Composable
fun ThemeSwitcherApp() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }

    Pruebas_FisicasTheme(darkTheme = isDarkTheme) {

            // Switch flotante arriba a la derecha
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { isDarkTheme = !isDarkTheme }
                )
            }

    }
}


