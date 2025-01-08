package com.example.intercambioderegalos.models

data class DetailsResponse(
    val intercambio: Intercambio,
    val temas: List<String>,
    val participantes: List<Participante> // Cambiado de String a Participante
)
