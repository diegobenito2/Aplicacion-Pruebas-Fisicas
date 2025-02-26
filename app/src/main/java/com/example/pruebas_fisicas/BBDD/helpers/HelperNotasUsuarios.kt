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



}