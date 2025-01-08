package com.example.intercambioderegalos.models

data class Participante(
    val id: Int? = null,
    val nombre: String,
    val email: String? = null,
    val telefono: String,
    val confirmado: Int = 0, // Por defecto, no confirmado
    val asignado_a: Int? = null,
    val id_intercambio: Int
)
