package com.example.pruebas_fisicas.BBDD.helpers

import android.content.ContentValues
import android.content.Context
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.info.DatosUsuario

class HelperdatosUsuario(context: Context) : BBdd(context) {

    // Insertar datos de usuario
    fun insertarDatosUsuario(
        userId: Int,
        edad: Int,
        peso: Float,
        altura: Float,
        sexo: String
    ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("edad", edad)
            put("peso", peso)
            put("altura", altura)
            put("sexo", sexo)
            put("userId", userId)
        }
        return db.insert("datosUsuario", null, values)
    }

    // Obtener datos de usuario por ID
    fun getDatosUsuarioPorId(userId: Int): DatosUsuario? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM datosUsuario WHERE userId = ?",
            arrayOf(userId.toString())
        )

        var datosUsuario: DatosUsuario? = null
        if (cursor.moveToFirst()) {
            val edad = cursor.getInt(cursor.getColumnIndexOrThrow("edad"))
            val peso = cursor.getFloat(cursor.getColumnIndexOrThrow("peso"))
            val altura = cursor.getFloat(cursor.getColumnIndexOrThrow("altura"))
            val sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"))
            datosUsuario = DatosUsuario(edad, peso, altura, sexo,userId)
        }
        cursor.close()
        return datosUsuario
    }

    // Actualizar datos de usuario
    fun actualizarDatosUsuario(
        userId: Int,
        edad: Int,
        peso: Float,
        altura: Float,
        sexo: String
    ): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("edad", edad)
            put("peso", peso)
            put("altura", altura)
            put("sexo", sexo)
        }
        return db.update("datosUsuario", values, "userId = ?", arrayOf(userId.toString()))
    }
}
