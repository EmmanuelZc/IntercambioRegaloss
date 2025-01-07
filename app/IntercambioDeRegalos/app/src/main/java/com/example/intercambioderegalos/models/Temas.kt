package com.example.intercambioderegalos.models

data class Temas(
    val id: Int? = null,         // ID único del tema (opcional)
    val id_intercambio: Int,     // ID del intercambio al que pertenece el tema
    val tema: String
)