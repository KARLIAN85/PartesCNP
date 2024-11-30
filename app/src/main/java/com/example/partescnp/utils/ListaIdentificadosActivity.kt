package com.example.partescnp.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.partescnp.R
import com.example.partescnp.database.DataBaseHelper

class ListaIdentificadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_identificados)

        // Configuración del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPersons)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialización de la base de datos
        val dbHelper = DataBaseHelper(this)

        // Obtener todas las personas registradas
        val cursor = dbHelper.obtenerTodasLasPersonas()

        // Configurar adaptador con los datos
        val adapter = PersonAdapter(cursor)
        recyclerView.adapter = adapter
    }
}
