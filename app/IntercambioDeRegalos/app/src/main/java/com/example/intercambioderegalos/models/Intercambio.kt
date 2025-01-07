package com.example.intercambioderegalos.models

data class Intercambio(
    val id: Int? = null, // Opcional
// Ahora es opcional y tiene un valor por defecto de `null`
    val nombre: String,
    val fechaLimiteRegistro: String,
    val fechaIntercambio: String,
    val horaIntercambio: String,
    val lugar: String,
    val montoMaximo: Double,
    val comentarios: String?,
    val claveUnica: String
)
