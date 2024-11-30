package com.example.partescnp.database

// Clase para definir el modelo de Persona
data class Persona(
    val id: Int = 0,
    val nombre: String,
    val apellido1: String,
    val apellido2: String?,
    val tipoDocumento: String?,
    val numeroDocumneto: String?,
    val fechaNacimiento: String?,
    val tipoVia: String?,
    val direccion: String?,
    val numeroDirecion: String?,
    val ciudadDirecion: String?,
    val comunidadDirecion: String?,
    val nacimientoCiudad: String?,
    val nacimientoComunidad: String?,
    val nacimientoPais: String?,
    val nombrePadre: String?,
    val nombreMadre: String?,
    val telefono: String?,
    val codigoAntecedentes: Int
)
