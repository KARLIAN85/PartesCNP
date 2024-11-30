package com.example.partescnp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Método principal que se ejecuta al iniciar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos el botón para crear un nuevo parte
        val nuevoParteButton: Button = findViewById(R.id.btn_nuevo_parte)

        // Configuramos el clic del botón para abrir la actividad NuevoParteActivity
        nuevoParteButton.setOnClickListener {
            try {
                val intent = Intent(this, NuevoParteActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                // Manejamos posibles errores al iniciar la actividad
                e.printStackTrace()
            }
        }


    }
}
