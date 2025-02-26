package com.example.pruebas_fisicas.BBDD.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.pruebas_fisicas.BBDD.BBdd
import com.example.pruebas_fisicas.ui.recycler.data.NotaUsuarios

class HelperNotasUsuarios(contextapp: Context) : BBdd(context = contextapp) {
    fun insertarOActualizarNota(notaUsuario: NotaUsuarios): Long {
        val db: SQLiteDatabase = this.writableDatabase

        // Primero, intentamos actualizar
        val valoresActualizacion = ContentValues().apply {
            put("nota", notaUsuario.nota.toFloat())
        }

        val whereClause = "userid = ? AND nombrePrueba = ?"
        val whereArgs = arrayOf(notaUsuario.userid.toString(), notaUsuario.nombrePrueba)

        val filasAfectadas = db.update("notasUsuarios", valoresActualizacion, whereClause, whereArgs)

        return if (filasAfectadas == 0) {
            // Si no se actualizó ninguna fila, insertamos una nueva nota
            val valoresInsercion = ContentValues().apply {
                put("nombrePrueba", notaUsuario.nombrePrueba)
                put("nota", notaUsuario.nota.toFloat())
                put("userid", notaUsuario.userid)
            }
            db.insert("notasUsuarios", null, valoresInsercion)
        } else {
            filasAfectadas.toLong() // Devuelve el número de filas actualizadas
        }.also { db.close() }
    }

    fun obtenerNotasUsuario(userId: Int): List<NotaUsuarios> {
        val db: SQLiteDatabase = this.readableDatabase
        val listaNotas = mutableListOf<NotaUsuarios>()

        val cursor = db.rawQuery("SELECT nombrePrueba, nota FROM notasUsuarios WHERE userid = ?", arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val nombrePrueba = cursor.getString(0)
                val nota = cursor.getFloat(1)
                listaNotas.add(NotaUsuarios(nombrePrueba,nota, userId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return listaNotas
    }

    fun calcularNotaMedia(notas: List<NotaUsuarios>): Float {
        var suma = 0f
        for (nota in notas) {
            suma += nota.nota
        }
        return if (notas.isNotEmpty()) suma / notas.size else 0f
    }
}
