package com.example.pruebas_fisicas.ui.navigation

import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebas_fisicas.R
import com.example.pruebas_fisicas.ui.info.InfoScreen
import com.example.pruebas_fisicas.ui.login.ui.LoginScreen
import com.example.pruebas_fisicas.ui.recycler.ui.RecyclerScreen
import java.lang.reflect.Modifier

@Composable
fun NavegationWrapper(){
 val navController = rememberNavController()

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



