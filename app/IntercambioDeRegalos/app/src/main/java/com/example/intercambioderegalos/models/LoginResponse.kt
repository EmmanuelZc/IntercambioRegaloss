package com.example.intercambioderegalos.models

data class LoginResponse(
    val token: String,        // Token JWT para autenticación
    val userId: Int,          // ID único del usuario
    val message: String       // Mensaje opcional de éxito o información adicional
)