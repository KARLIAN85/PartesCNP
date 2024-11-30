package com.example.partescnp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.partescnp.Captura.CameraCaptureActivity

class IdentificadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identificado)

        // Botón para abrir la actividad de filiación completa
        val btnFiliacionCompleta: Button = findViewById(R.id.btn_filiacion_completa)
        btnFiliacionCompleta.setOnClickListener {
            val intent = Intent(this, FiliacionCompletaActivity::class.java)
            startActivity(intent)
        }

        // Botón para capturar DNI
        val btnDni: Button = findViewById(R.id.btn_dni)
        btnDni.setOnClickListener {
            // Abrir la actividad de captura de cámara
            val intent = Intent(this, CameraCaptureActivity::class.java)
            startActivity(intent)
        }
    }
}
