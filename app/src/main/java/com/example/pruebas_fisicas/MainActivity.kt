package com.example.pruebas_fisicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.theme.Pruebas_FisicasTheme
import com.example.pruebas_fisicas.ui.navigation.NavegationWrapper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        BBdd(context = this).writableDatabase


        setContent {
            Pruebas_FisicasTheme {
                NavegationWrapper()
            }
        }
    }

}

