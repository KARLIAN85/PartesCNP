package com.example.partescnp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.partescnp.database.DataBaseHelper
import com.example.partescnp.database.Persona
import com.example.partescnp.databinding.ActivityFiliacionCompletaBinding

class FiliacionCompletaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFiliacionCompletaBinding // Variable para ViewBinding
    private lateinit var dbHelper: DataBaseHelper // Base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializamos el ViewBinding
        binding = ActivityFiliacionCompletaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos la base de datos
        dbHelper = DataBaseHelper(this)

        // Configuramos el campo desplegable para tipo de vía
        setupTipoViaDropdown()

        // Configuramos el botón "Guardar"
        binding.btnGuardar.setOnClickListener {
            guardarPersona()
        }
    }

    private fun setupTipoViaDropdown() {
        // Lista de opciones para el tipo de vía
        val tiposDeVia = listOf("Calle", "Plaza", "Avenida", "Carretera", "Pasaje", "Glorieta")

        // Configurar el adaptador con la lista de opciones
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tiposDeVia)

        // Asignar el adaptador al campo desplegable
        val autoCompleteTextView: AutoCompleteTextView = binding.etTipoVia
        autoCompleteTextView.setAdapter(adapter)

        // Habilitar la apertura automática al hacer clic en el campo
        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
        }

        // Escuchar las selecciones del usuario
        autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = tiposDeVia[position]
            Toast.makeText(this, "Seleccionaste: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }


    private fun guardarPersona() {
        // Capturamos los datos del formulario usando ViewBinding
        val persona = Persona(
            nombre = binding.etNombre.text.toString(),
            apellido1 = binding.etPrimerApellido.text.toString(),
            apellido2 = binding.etSegundoApellido.text.toString(),
            tipoDocumento = binding.etTipoDocumento.text.toString(),
            numeroDocumneto = binding.etNumeroDocumento.text.toString(),
            fechaNacimiento = binding.etFechaNacimiento.text.toString(),
            tipoVia = binding.etTipoVia.text.toString(),
            direccion = binding.etDireccion.text.toString(),
            numeroDirecion = binding.etNumeroDirecion.text.toString(),
            ciudadDirecion = binding.etCiudadDirecion.text.toString(),
            comunidadDirecion = binding.etComunidadDirecion.text.toString(),
            nacimientoCiudad = binding.etLugarNacimientoCiudad.text.toString(),
            nacimientoComunidad = binding.etLugarNacimientoComunidad.text.toString(),
            nacimientoPais = binding.etLugarNacimientoPais.text.toString(),
            nombrePadre = binding.etNombrePadre.text.toString(),
            nombreMadre = binding.etNombreMadre.text.toString(),
            telefono = binding.etTelefono.text.toString(),
            codigoAntecedentes = binding.etCodigoAntecedentes.text.toString().toIntOrNull() ?: 0
        )

        // Validamos que los campos obligatorios no estén vacíos
        if (persona.nombre.isBlank() || persona.apellido1.isBlank()) {
            Toast.makeText(this, "Nombre y Primer Apellido son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardamos en la base de datos
        val resultado = dbHelper.insertarPersona(persona)

        if (resultado != -1L) {
            Toast.makeText(this, "Persona guardada correctamente", Toast.LENGTH_SHORT).show()
            limpiarFormulario()
        } else {
            Toast.makeText(this, "Error al guardar la persona", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarFormulario() {
        // Limpiamos todos los campos del formulario
        binding.etNombre.text.clear()
        binding.etPrimerApellido.text.clear()
        binding.etSegundoApellido.text.clear()
        binding.etTipoDocumento.text.clear()
        binding.etNumeroDocumento.text.clear()
        binding.etFechaNacimiento.text.clear()
        binding.etTipoVia.text.clear()
        binding.etDireccion.text.clear()
        binding.etNumeroDirecion.text.clear()
        binding.etCiudadDirecion.text.clear()
        binding.etComunidadDirecion.text.clear()
        binding.etLugarNacimientoCiudad.text.clear()
        binding.etLugarNacimientoComunidad.text.clear()
        binding.etLugarNacimientoPais.text.clear()
        binding.etNombrePadre.text.clear()
        binding.etNombreMadre.text.clear()
        binding.etTelefono.text.clear()
        binding.etCodigoAntecedentes.text.clear()
    }


}

