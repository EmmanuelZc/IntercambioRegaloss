package com.example.intercambioderegalos.models

data class Participante(
    val nombre: String,       // Nombre del participante
    val correo: String,       // Correo electrónico del participante
    val telefono: String? = null, // Teléfono del participante (opcional)
    val temaPreferido: String? = null // Tema de regalo preferido (opcional)
)
