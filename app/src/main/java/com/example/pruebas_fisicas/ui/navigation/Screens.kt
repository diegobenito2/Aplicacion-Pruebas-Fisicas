package com.example.pruebas_fisicas.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login
@Serializable
data class ForgotPass(val email: String)
@Serializable
data class InfoS(val userid: Int)
@Serializable
data class Recycler(val userid: Int)
@Serializable
data class CalculoNotas(val prueba:String,val userid: Int)
