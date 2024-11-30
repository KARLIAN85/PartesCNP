package com.example.partescnp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Clase para manejar la base de datos SQLite
class DataBaseHelper(contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_BD, null, VERSION_BD) {

    companion object {
        private const val NOMBRE_BD = "partesCNP.db"
        private const val VERSION_BD = 1

        // Nombre de la tabla y sus columnas
        const val TABLA_PERSONAS = "personas"
        const val COLUMNA_ID = "id"
        const val COLUMNA_NOMBRE = "nombre"
        const val COLUMNA_APELLIDO1 = "apellido1"
        const val COLUMNA_APELLIDO2 = "apellido2"
        const val COLUMNA_TIPO_DOCUMENTO = "tipo_documento"
        const val COLUMNA_NUMERO_DOCUMENTO = "numero_documento"
        const val COLUMNA_FECHA_NACIMIENTO = "fecha_nacimiento"
        const val COLUMNA_TIPO_VIA = "tipo_via"
        const val COLUMNA_DIRECCION = "direccion"
        const val COLUMNA_NUMERO_DIRECION = "numero"
        const val COLUMNA_CIUDAD_DIRECION = "ciudad_direcion"
        const val COLUMNA_COMUNIDAD_DIRECION = "comunidad_direcion"
        const val COLUMNA_NACIMIENTO_CIUDAD = "nacimiento_ciudad"
        const val COLUMNA_NACIMIENTO_COMUNIDAD = "nacimiento_comunidad"
        const val COLUMNA_NACIMIENTO_PAIS = "nacimiento_pais"
        const val COLUMNA_NOMBRE_PADRE = "nombre_padre"
        const val COLUMNA_NOMBRE_MADRE = "nombre_madre"
        const val COLUMNA_TELEFONO = "telefono"
        const val COLUMNA_CODIGO_ANTECEDENTES = "codigo_antecedentes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Consulta para crear la tabla en la base de datos
        val crearTablaConsulta = """
            CREATE TABLE $TABLA_PERSONAS (
                $COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMNA_NOMBRE TEXT NOT NULL,
                $COLUMNA_APELLIDO1 TEXT NOT NULL,
                $COLUMNA_APELLIDO2 TEXT,
                $COLUMNA_TIPO_DOCUMENTO TEXT,
                $COLUMNA_NUMERO_DOCUMENTO TEXT,
                $COLUMNA_FECHA_NACIMIENTO TEXT,
                $COLUMNA_TIPO_VIA TEXT,
                $COLUMNA_DIRECCION TEXT,
                $COLUMNA_NUMERO_DIRECION TEXT,
                $COLUMNA_CIUDAD_DIRECION TEXT,
                $COLUMNA_COMUNIDAD_DIRECION TEXT,
        COLUMNA_$COLUMNA_NACIMIENTO_CIUDAD TEXT,
                $COLUMNA_NACIMIENTO_COMUNIDAD TEXT,
                $COLUMNA_NACIMIENTO_PAIS TEXT,
                $COLUMNA_NOMBRE_PADRE TEXT,
        COLUMNA_$COLUMNA_NOMBRE_MADRE TEXT,
                $COLUMNA_TELEFONO TEXT,
                $COLUMNA_CODIGO_ANTECEDENTES INTEGER
            )
        """
        db?.execSQL(crearTablaConsulta)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Elimina la tabla anterior si existe y crea una nueva
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAS")
        onCreate(db)
    }

    // Método para insertar una persona en la base de datos
    fun insertarPersona(persona: Persona): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(COLUMNA_NOMBRE, persona.nombre)
            put(COLUMNA_APELLIDO1, persona.apellido1)
            put(COLUMNA_APELLIDO2, persona.apellido2)
            put(COLUMNA_TIPO_DOCUMENTO, persona.tipoDocumento)
            put(COLUMNA_NUMERO_DOCUMENTO, persona.numeroDocumneto)
            put(COLUMNA_FECHA_NACIMIENTO, persona.fechaNacimiento)
            put(COLUMNA_TIPO_VIA, persona.tipoVia)
            put(COLUMNA_DIRECCION, persona.direccion)
            put(COLUMNA_NUMERO_DIRECION, persona.numeroDirecion)
            put(COLUMNA_CIUDAD_DIRECION, persona.ciudadDirecion)
            put(COLUMNA_COMUNIDAD_DIRECION, persona.comunidadDirecion)
            put(COLUMNA_NACIMIENTO_CIUDAD, persona.nacimientoCiudad)
            put(COLUMNA_NACIMIENTO_COMUNIDAD, persona.nacimientoComunidad)
            put(COLUMNA_NACIMIENTO_PAIS, persona.nacimientoPais)
            put(COLUMNA_NOMBRE_PADRE, persona.nombrePadre)
            put(COLUMNA_NOMBRE_MADRE, persona.nombreMadre)
            put(COLUMNA_TELEFONO, persona.telefono)
            put(COLUMNA_CODIGO_ANTECEDENTES, persona.codigoAntecedentes)
        }
        return db.insert(TABLA_PERSONAS, null, valores)
    }

    // Método para obtener todas las personas
    fun obtenerTodasLasPersonas(): Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLA_PERSONAS", null)
    }
}
