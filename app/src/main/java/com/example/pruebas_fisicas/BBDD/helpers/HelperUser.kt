// Clase HelperUser actualizada sin datosUsuario
package com.example.pruebas_fisicas.BBDD.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.login.data.User
import com.example.pruebas_fisicas.ui.login.data.UserconID

class HelperUser(contextapp: Context) : BBdd(context = contextapp) {

    fun getUser(email: String): UserconID? {
        val db: SQLiteDatabase = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(email))
        var user: UserconID? = null
        if (cursor.moveToFirst()) {
            user = UserconID(cursor.getInt(0), cursor.getString(1), cursor.getString(2))
        }
        cursor.close()
        return user
    }

    fun insertUser(user: User) {
        if (user.email.isEmpty() || user.password.isEmpty()) return
        val db: SQLiteDatabase = writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues().apply {
                put("email", user.email)
                put("password", user.password)
            }
            db.insertOrThrow("user", null, values)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            println("Error al insertar el usuario: ${e.message}")
        } finally {
            db.endTransaction()
        }
    }

    fun forgotPassword(contextapp: Context, email: String, newPassword: String): Int {
        val user = getUser(email)
        if (user != null) {
            val db: SQLiteDatabase = writableDatabase
            db.beginTransaction()
            try {
                val values = ContentValues().apply {
                    put("password", newPassword)
                }
                val rowsAffected = db.update("user", values, "email = ?", arrayOf(email))
                db.setTransactionSuccessful()
                return if (rowsAffected > 0) 0 else -3
            } catch (e: Exception) {
                println("Error al actualizar el usuario: ${e.message}")
                return -2
            } finally {
                db.endTransaction()
            }
        } else {
            Toast.makeText(contextapp, "El usuario no existe", Toast.LENGTH_SHORT).show()
            return -1
        }
    }

    fun userExists(email: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
}
