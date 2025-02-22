package com.example.pruebas_fisicas.BBDD.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.login.data.User

class HelperUser(context: Context) : BBdd(context = context) {

    @Composable
    fun getUser(email: String): User? {
        val db: SQLiteDatabase = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = '$email'", null)
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
        }
        cursor.close()
        return user
    }

    @Composable
    fun insertUser(user: User) {
        if (user.email == "" || user.password == "") return
        val db: SQLiteDatabase = writableDatabase
        db.beginTransaction()
        try {
            val values: ContentValues = ContentValues()
            values.put("email", user.email)
            values.put("password", user.password)
            db.insertOrThrow("user", null, values)
        } catch (e: Exception) {
            println("Error al insertar el usuario: ${e.message}")
        } finally {
            db.endTransaction()
        }
    }

    @Composable
    fun updateUser(email: String, newPasword: String) {
        val user = getUser(email)
        if (user != null) {
            user.password = newPasword
            val db: SQLiteDatabase = writableDatabase
            db.beginTransaction()
            try {
                val values: ContentValues = ContentValues()
                values.put("email", user.email)
                values.put("password", user.password)
                db.update("user", values, "email = ?", arrayOf(email))
            } catch (e: Exception) {
                println("Error al actualizar el usuario: ${e.message}")
            } finally {
                db.endTransaction()
            }
        } else {
            Toast.makeText(LocalContext.current, "El usuario no existe", Toast.LENGTH_SHORT).show()
        }
    }

}