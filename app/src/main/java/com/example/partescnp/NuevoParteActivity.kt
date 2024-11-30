package com.example.partescnp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class NuevoParteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_parte)

        val identificadosButton: Button = findViewById(R.id.btn_nueva_identificacion)
        identificadosButton.setOnClickListener {
            val intent = Intent(this, IdentificadoActivity::class.java)
            startActivity(intent)
        }
    }
}
