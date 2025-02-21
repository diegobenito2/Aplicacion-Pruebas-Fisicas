package com.example.pruebas_fisicas.ui.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pruebas_fisicas.R
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(modifier: Modifier, viewModel: LoginViewModel) {
    Box(modifier.padding(16.dp)) {
        Login(modifier.align(Alignment.Center), viewModel)
    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel) {
    val email: String by viewModel.email.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)
    val couritineScope = rememberCoroutineScope()

        Column(modifier) {
            HeaderImage(Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.padding(16.dp))
            EmailField(email, { viewModel.onLoginChange(it, password) })
            Spacer(modifier = Modifier.padding(8.dp))
            PasswordField(password) { viewModel.onLoginChange(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            ForgotPassword(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(16.dp))
            LoginButton(loginEnable) {
                couritineScope.launch {
                    viewModel.onLoginSelected()
                }
            }
        }
    }


@Composable
fun LoginButton(loginEnable: Boolean, onLoginSelected: () -> Unit) {
    Button(
        onClick = { onLoginSelected },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE), // Color del fondo del botón
            contentColor = Color.White, // Color del texto/iconos
            disabledContainerColor = Color(0xFFBDBDBD), // Color de fondo cuando está deshabilitado
            disabledContentColor = Color(0xFF757575) // Color del texto cuando está deshabilitado
        ), enabled = loginEnable
    ) {
        Text("Iniciar Sesión")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        "Olvidaste la contraseña?",
        modifier.clickable { },
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun PasswordField(password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable  { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        placeholder = { Text("Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) R.drawable.ver else R.drawable.esconder
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(painter = painterResource(image), contentDescription = description, modifier = Modifier.size(20.dp))
            }
        }
    )
}



@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {

    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, start = 20.dp),
        placeholder = { Text("Email") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.DarkGray,
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(painter = painterResource(R.drawable.imagelogin), contentDescription = "Header", modifier)
}
