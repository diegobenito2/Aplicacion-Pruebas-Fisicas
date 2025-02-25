package com.example.pruebas_fisicas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pruebas_fisicas.ui.info.InfoScreen
import com.example.pruebas_fisicas.ui.login.ui.ForgotScreen
import com.example.pruebas_fisicas.ui.login.ui.LoginScreen
import com.example.pruebas_fisicas.ui.recycler.ui.RecyclerScreen

@Composable
fun NavegationWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(navController)
        }
        composable<ForgotPass> {
            val email = it.arguments?.getString("email") ?: ""
            ForgotScreen(navController, email)
        }
        composable<InfoS> {
            val userId = it.arguments?.getString("userId")?.toIntOrNull() ?: -1
            println("userId: $userId")
            InfoScreen(navController, userId)
        }
        composable<Recycler> {
            val userId = it.arguments?.getString("userId")?.toIntOrNull() ?: -1
            RecyclerScreen(navController,userId)
        }


    }
}




