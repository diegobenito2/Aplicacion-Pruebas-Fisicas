package com.example.pruebas_fisicas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebas_fisicas.ui.info.InfoScreen
import com.example.pruebas_fisicas.ui.login.ui.LoginScreen
import com.example.pruebas_fisicas.ui.recycler.ui.RecyclerScreen

@Composable
fun NavegationWrapper(navController: NavHostController) {
    val navController = navController

    NavHost(navController = navController,startDestination = Login){
        composable<Login>{
            LoginScreen(navController)
        }
        composable<InfoS> {
            InfoScreen(navController)
        }
        composable<Recycler>{
            RecyclerScreen(navController)
        }

    }



}



