package com.example.pruebas_fisicas.ui.login.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pruebas_fisicas.BBDD.helpers.HelperUser
import com.example.pruebas_fisicas.R
import com.example.pruebas_fisicas.ui.login.data.User
import com.example.pruebas_fisicas.ui.login.data.UserconID
import com.example.pruebas_fisicas.ui.navigation.ForgotPass
import com.example.pruebas_fisicas.ui.navigation.InfoS
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavHostController) {
    Scaffold { innerpadding ->
        val modifier: Modifier = Modifier
            .fillMaxSize()
            .padding(innerpadding)

        Box(modifier) {

            Column(
                modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Login(navController)
            }
        }
    }
}


@Composable
fun Login(navController: NavHostController) {
    val context = LocalContext.current
    val helperUser = HelperUser(context)
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val loginEnable = isValidEmail(email) && isValidPassword(password)
    val coroutineScope = rememberCoroutineScope()

    Column {
        HeaderText("Iniciar Sesión", Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        EmailField(email) { email = it }
        Spacer(modifier = Modifier.padding(8.dp))
        PasswordField("Contraseña", password) { password = it }
        Spacer(modifier = Modifier.padding(8.dp))
        ForgotPasswordmethod(Modifier.align(Alignment.End), navController, email)
        Spacer(modifier = Modifier.padding(16.dp))
        val user = User(email, password)
        buttons("Iniciar Sesión", loginEnable) {
            coroutineScope.launch {
                isLoading = true
                val userconId = helperUser.getUser(email.trim())
                if (userconId != null) {
                    if (user.email == userconId.email && user.password == userconId.password) {
                        println("userId en LoginScreen: ${userconId.id}")
                        navController.navigate(InfoS(userconId.id))
                    } else {
                        Toast.makeText(
                            context,
                            "La contraseña es incorrecta. Inténtalo de nuevo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        context,
                        "El usuario no existe.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
        Spacer(modifier = Modifier.padding(120.dp))
    }

}

@Composable
fun buttons(
    mensaje: String,
    loginEnable: Boolean,
    onLoginSelected: () -> Unit
) { //Pasar funciones como parámetros se llama single source of true(La única fuente de la verdad.)

    Button(
        onClick = {
            onLoginSelected()
        },
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
        Text(mensaje)
    }
}

@Composable
fun ForgotPasswordmethod(modifier: Modifier, navController: NavHostController, email: String) {
    val context = LocalContext.current
    val helperUser = HelperUser(context)


    val user: UserconID? = helperUser.getUser(email)
    if (user != null) {// Verificar si el usuario existe antes de mostrar el mensaje de olvidaste la contraseña
        Text(
            "Olvidaste la contraseña?",
            modifier.clickable {
                navController.navigate(ForgotPass(user.email))
            },
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun PasswordField(mensaje: String, password: String, onTextFieldChanged: (String) -> Unit) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        placeholder = { Text(mensaje) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) R.drawable.ver else R.drawable.esconder
            val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = description,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
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
fun HeaderText(text: String, modifier: Modifier) {
    Text(
        text,
        modifier,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun isValidPassword(password: String): Boolean = password.length > 6 && password.length < 255




