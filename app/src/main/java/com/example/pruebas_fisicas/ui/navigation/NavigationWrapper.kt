package com.example.pruebas_fisicas.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.pruebas_fisicas.ui.info.ui.InfoScreen
import com.example.pruebas_fisicas.ui.login.ui.ForgotScreen
import com.example.pruebas_fisicas.ui.login.ui.LoginScreen
import com.example.pruebas_fisicas.ui.recycler.ui.CalculadoraNotasScreen
import com.example.pruebas_fisicas.ui.recycler.ui.RecyclerScreen

@Composable
fun NavegationWrapper(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {

            LoginScreen(
                navigateToInfoS = { userId -> navController.navigate(InfoS(userId)) },
                navigateToForgotPass = { email -> navController.navigate(ForgotPass(email)) }
            )
        }

        composable<ForgotPass> {
            val email: ForgotPass = it.toRoute()
            ForgotScreen(
                { navController.navigate(Login) { popUpTo<Login> { inclusive = true } } },
                email.email
            )
        }

        composable<InfoS> {
            val infosArgs: InfoS = it.toRoute()
            InfoScreen({
                navController.navigate(Recycler(infosArgs.userid))
            }, { navController.popBackStack() }, userId = infosArgs.userid)
        }

        composable<Recycler> {
            val recyclerArgs: Recycler = it.toRoute()
            RecyclerScreen(
                navigationToInfoS = {
                    navController.navigate(InfoS(recyclerArgs.userid)) {
                        popUpTo<InfoS> {
                            inclusive = true
                        }
                    }
                },
                navigationToCalculoNotas = { prueba ->
                    navController.navigate(CalculoNotas(prueba, recyclerArgs.userid))
                },
                userId = recyclerArgs.userid
            )
        }


        composable<CalculoNotas> {
            val notas: CalculoNotas = it.toRoute()

            CalculadoraNotasScreen(notas.prueba, notas.userid, {
                navController.navigate(Recycler(notas.userid)) {
                    popUpTo<Recycler> {
                        inclusive = true
                    }
                }
            }, {
                navController.navigate(InfoS(notas.userid)) { popUpTo<InfoS> { inclusive = true } }
            })

        }
    }
}




