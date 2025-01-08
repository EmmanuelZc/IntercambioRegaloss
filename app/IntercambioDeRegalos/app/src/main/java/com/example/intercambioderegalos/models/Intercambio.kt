package com.example.intercambioderegalos.models
data class Intercambio(
    val id: Int? = null,
    val nombre_intercambio: String,
    val fecha_limite_registro: String,
    val fecha_intercambio: String,
    val hora_intercambio: String,
    val lugar_intercambio: String,
    val monto: Double,
    val comentarios: String?,
    val clave_unica: String
)
