package com.example.intercambioderegalos.models

data class DetailsResponse(
    val clave_unica: String,
    val fecha_limite_registro: String,
    val fecha_intercambio: String,
    val hora_intercambio: String,
    val lugar_intercambio: String,
    val temasIntercambio: List<Temas>
)
