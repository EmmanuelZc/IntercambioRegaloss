package com.example.intercambioderegalos

import com.example.intercambioderegalos.models.User

data class ResponseData(
    val message: String,
    val user: User?
)
