package com.example.intercambioderegalos.models

import android.service.autofill.UserData

data class User(
    val nombre: String = "",
    val alias: String = "",
    val correo: String = "",
    val contraseña: String = ""
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserData
)

