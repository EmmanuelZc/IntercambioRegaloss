package com.example.intercambioderegalos.models

data class Participante(
    val nombre: String,
    val correo: String,
    val telefono: String? = null,
    val temaPreferido: String? = null
)
