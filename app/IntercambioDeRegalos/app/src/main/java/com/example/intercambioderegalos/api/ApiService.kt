package com.example.intercambioderegalos.api


import com.example.intercambioderegalos.ResponseData
import com.example.intercambioderegalos.models.Intercambio
import com.example.intercambioderegalos.models.LoginResponse
import com.example.intercambioderegalos.models.Temas
import com.example.intercambioderegalos.models.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {

    @POST("/api/auth/registro")
    suspend fun registerUser(@Body user: User): Response<Void>

    @GET("/api/data")
    suspend fun getData(@Header("Authorization") token: String): Response<ResponseData>  // Añadir token al header

    @POST("/api/auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): Response<LoginResponse>

    data class LoginRequest(val correo: String, val contraseña: String)

    @POST("/api/auth/intercambio")
    suspend fun nuevoIntercambio(
        @Header("Authorization") token: String,
        @Body intercambio: Intercambio
    ): Response<Intercambio> // Debe devolver un Intercambio que incluya el ID


    @POST("/api/auth/temas")
    suspend fun nuevoTema(
        @Header("Authorization") token: String,
        @Body tema: Temas
    ): Response<Void>



}