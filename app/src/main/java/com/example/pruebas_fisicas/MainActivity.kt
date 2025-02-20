package com.example.pruebas_fisicas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pruebas_fisicas.ui.login.ui.LoginScreen
import com.example.pruebas_fisicas.ui.login.ui.LoginViewModel
import com.example.pruebas_fisicas.ui.theme.Pruebas_FisicasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pruebas_FisicasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        Modifier.padding(innerPadding),
                        viewModel = LoginViewModel(),
                    )
                }
            }
        }
    }
}

