package com.example.pruebas_fisicas.ui.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun ForgotScreen(navController: NavController, email: String) {
    val modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    Box(modifier) {
        Column(
            modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForgotPassword(navController,email)
        }
    }
}

@Composable
fun ForgotPassword(navController: NavController,email: String) {
    val helperUser = helperUser()
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val loginEnable = isValidPassword(password)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column {
        HeaderText("Iniciar Sesión", Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        PasswordField("Introduce la nueva contraseña", password) { password = it }
        Spacer(modifier = Modifier.padding(8.dp))
        buttons("Iniciar Sesión", loginEnable) {
            coroutineScope.launch {
                isLoading = true
//                helperUser.UdateUser(email, password)
                Toast.makeText(context, "¡¡Contraseña cambiada con éxito!!", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

}
