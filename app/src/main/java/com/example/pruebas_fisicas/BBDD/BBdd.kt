package com.example.pruebas_fisicas.BBDD

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class BBdd(context: Context) : SQLiteOpenHelper(context, "PruebasFisicas", null, 1) {
    private val createTableUser =
        "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(255), password VARCHAR(255))"
    private val borrarTableUser = "DROP TABLE IF EXISTS user"

    private val createTableDatosUsuario =
        "CREATE TABLE datosUsuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "edad INTEGER, " +
                "peso FLOAT, " +
                "altura FLOAT, " +
                "sexo VARCHAR(10) CHECK(sexo IN ('Hombre', 'Mujer')), " +
                "userId INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES user(id)" +
                ")"
    private val borrarTableDatosUsuario = "DROP TABLE IF EXISTS datosUsuario"
    private val createTableNotaUsuarios =
        "CREATE TABLE notasUsuarios(id INTEGER PRIMARY KEY AUTOINCREMENT,nombrePrueba VARCHAR(255),nota FLOAT,userid Integer,FOREIGN KEY(userid) REFERENCES user(id))"
    private val borrarTableNotaUsuarios = "DROP TABLE IF EXISTS notasUsuarios"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableUser)
        db?.execSQL(createTableDatosUsuario)
        db?.execSQL(createTableNotaUsuarios)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(borrarTableUser)
        db?.execSQL(borrarTableDatosUsuario)
        db?.execSQL(borrarTableNotaUsuarios)
        onCreate(db)
    }
}