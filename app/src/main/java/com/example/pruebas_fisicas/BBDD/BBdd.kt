package com.example.pruebas_fisicas.BBDD

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class BBdd(context: Context) : SQLiteOpenHelper(context, "PruebasFisicas", null, 1) {
    private val createTableUser =
        "CREATE TABLE user(id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR(50), password VARCHAR(13))"
    private val borrarTableUser = "DROP TABLE IF EXISTS user"

    private val createTableUsuario =
        "CREATE TABLE usuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre VARCHAR(50), " +
                "userId INTEGER, " +
                "FOREIGN KEY(userId) REFERENCES user(id)" +
                ")"
    private val borrarTableUsuario = "DROP TABLE IF EXISTS usuario"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createTableUser)
        db?.execSQL(createTableUsuario)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(borrarTableUser)
        db?.execSQL(borrarTableUsuario)
        onCreate(db)
    }
}