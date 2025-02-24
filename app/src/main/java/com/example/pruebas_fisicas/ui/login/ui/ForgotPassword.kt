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
import androidx.navigation.NavHostController
import com.example.pruebas_fisicas.BBDD.helpers.HelperUser
import com.example.pruebas_fisicas.ui.login.data.User
import com.example.pruebas_fisicas.ui.login.data.UserconID
import com.example.pruebas_fisicas.ui.navigation.InfoS
import kotlinx.coroutines.launch

@Composable
fun ForgotScreen(navController: NavHostController, email: String) {
    val modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    Box(modifier) {
        Column(
            modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ForgotPassword(navController, email)
        }
    }
}

@Composable
fun ForgotPassword(navController: NavHostController, email: String) {
    val context = LocalContext.current
    val helperUser = HelperUser(context)
    val user: UserconID? = helperUser.getUser(email)
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val loginEnable = isValidPassword(password)
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            HeaderText("Cambiar Contraseña", Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            PasswordField("Introduce la nueva contraseña", password) { password = it }
            Spacer(modifier = Modifier.padding(8.dp))
            buttons("Cambiar contraseña", loginEnable) {
                coroutineScope.launch {
                    isLoading = true
                    if (user != null) {
                        if (user.password != password) {
                            val result = helperUser.forgotPassword(context, email, password)
                            if (result == 0) {
                                Toast.makeText(
                                    context,
                                    "¡¡Contraseña cambiada con éxito!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: No se actualizó la contraseña",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "La contraseña no puede ser igual a la antigua",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }


                }
            }
        }
    }
}
