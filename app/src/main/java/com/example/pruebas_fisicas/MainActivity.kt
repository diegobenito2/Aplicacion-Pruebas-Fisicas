package com.example.pruebas_fisicas

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.navigation.NavegationWrapper
import com.example.pruebas_fisicas.ui.recycler.ui.CalculadoraNotasScreen
import com.example.pruebas_fisicas.ui.theme.Pruebas_FisicasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Pruebas_FisicasTheme {
                ThemeSwitcherApp()
            }
        }
    }
}

@Composable
fun ThemeSwitcherApp() {
    var isDarkTheme by rememberSaveable { mutableStateOf(false) }
    val navController = rememberNavController()
    val bBdd = BBdd(LocalContext.current)
    val db= bBdd.writableDatabase
    bBdd.onUpgrade(db, 1, 2) //Para borrar la base datos y volver a crearla
    cargarDatos(db) // cargar los usuarios a la base de datos.
    Pruebas_FisicasTheme(darkTheme = isDarkTheme) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { isDarkTheme = !isDarkTheme }, modifier = Modifier.size(56.dp)
                ) {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = !isDarkTheme }, Modifier.scale(0.8f)
                    )
                }
            }, floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavegationWrapper(navController)
            }
        }
    }
}

fun cargarDatos(db: SQLiteDatabase?) {
    db?.execSQL("INSERT INTO user (email, password) VALUES ('diego@example.com', 'password123')")
    db?.execSQL("INSERT INTO user (email, password) VALUES ('lucia@example.com', 'securepass')")
    db?.execSQL("INSERT INTO user (email, password) VALUES ('ash.ketchum@pokedex.com', 'pikachu123')")
}


