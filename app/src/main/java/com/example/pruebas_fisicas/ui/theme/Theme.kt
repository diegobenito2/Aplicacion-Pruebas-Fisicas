package com.example.pruebas_fisicas.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9), // Azul claro
    secondary = Color(0xFF81D4FA), // Azul celeste
    tertiary = Color(0xFFB39DDB), // Lila sutil
    background = Color(0xFF121212), // Negro suave
    surface = Color(0xFF1E1E1E), // Gris oscuro
    onPrimary = Color(0xFF0D47A1), // Azul profundo
    onSecondary = Color(0xFF004D40), // Verde oscuro
    onTertiary = Color(0xFF4A148C), // Morado oscuro
    onBackground = Color(0xFFE0E0E0), // Gris claro
    onSurface = Color(0xFFEEEEEE), // Blanco humo
    surfaceTint = Color(0xFF90CAF9) // Azul claro
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1565C0), // Azul vibrante
    secondary = Color(0xFF00838F), // Verde azulado
    tertiary = Color(0xFF7E57C2), // Morado elegante
    background = Color(0xFFF5F5F5), // Gris muy claro
    surface = Color.White, // Blanco puro
    onPrimary = Color.White, // Texto en color primario
    onSecondary = Color.White, // Texto en color secundario
    onTertiary = Color.White, // Texto en color terciario
    onBackground = Color(0xFF1C1B1F), // Negro grisáceo
    onSurface = Color(0xFF1C1B1F) // Negro grisáceo
)


@Composable
fun Pruebas_FisicasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}