package com.example.intercambioderegalos.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intercambioderegalos.api.RetrofitClient
import com.example.intercambioderegalos.ResponseData
import com.example.intercambioderegalos.api.ApiService
import com.example.intercambioderegalos.utils.SessionManager
import kotlinx.coroutines.launch
import retrofit2.Response
import android.content.Context
import android.util.Log

class MainViewModel : ViewModel() {

    val messageState = MutableLiveData<String>()
    private val apiService = RetrofitClient.apiService

    // Función para registrar al usuario
    fun registerUser(user: User, context: Context, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(user)
                if (response.isSuccessful) {
                    messageState.value = "Usuario registrado con éxito"
                    onSuccess()
                } else {
                    val error = "Error al registrar usuario: ${response.code()}"
                    messageState.value = error
                    onError(error)
                }
            } catch (e: Exception) {
                val error = "Error de conexión: ${e.message}"
                messageState.value = error
                onError(error)
            }
        }
    }

    // Función para el login
    suspend fun loginUser(correo: String, password: String, context: Context): Boolean {
        val loginRequest = ApiService.LoginRequest(correo, password)
        return try {
            val response: Response<LoginResponse> = apiService.loginUser(loginRequest)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    val sessionManager = SessionManager(context)
                    sessionManager.saveToken(loginResponse.token)
                    messageState.value = loginResponse.message
                    true
                } else {
                    messageState.value = "Respuesta de inicio de sesión vacía"
                    false
                }
            } else {
                messageState.value = "Error de autenticación: ${response.message()}"
                false
            }
        } catch (e: Exception) {
            messageState.value = "Error de conexión: ${e.message}"
            false
        }
    }

    // Función para crear un nuevo intercambio con ID
    fun crearIntercambioConId(
        intercambio: Intercambio,
        context: Context,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response = apiService.nuevoIntercambio(
                        token = "Bearer $token",
                        intercambio = intercambio
                    )
                    if (response.isSuccessful) {
                        val intercambioCreado = response.body()
                        if (intercambioCreado != null) {
                            onSuccess(intercambioCreado.id ?: -1) // Usa -1 como valor por defecto si el id es null
                        } else {
                            onError("No se pudo obtener el ID del intercambio")
                        }
                    } else {
                        onError("Error en la respuesta: ${response.code()} ${response.message()}")
                    }
                } else {
                    onError("No se encontró un token válido. Por favor, inicia sesión nuevamente.")
                }
            } catch (e: Exception) {
                onError("Error al enviar la solicitud: ${e.message}")
            }
        }
    }
    fun fetchData(context: Context) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response: Response<ResponseData> = apiService.getData("Bearer $token")
                    if (response.isSuccessful) {
                        messageState.value = response.body()?.message ?: "Sin datos disponibles"
                    } else {
                        messageState.value = "Error al obtener datos: ${response.code()} ${response.message()}"
                    }
                } else {
                    messageState.value = "Token no encontrado, por favor inicia sesión nuevamente"
                }
            } catch (e: Exception) {
                messageState.value = "Error de conexión: ${e.message}"
            }
        }
    }
    // Función para agregar un tema
    fun nuevoTema(
        tema: Temas,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response = apiService.nuevoTema("Bearer $token", tema)
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Error al agregar el tema: ${response.code()} ${response.message()}")
                    }
                } else {
                    onError("No se encontró un token válido. Por favor, inicia sesión nuevamente.")
                }
            } catch (e: Exception) {
                onError("Error de conexión: ${e.message}")
            }
        }
    }
}
