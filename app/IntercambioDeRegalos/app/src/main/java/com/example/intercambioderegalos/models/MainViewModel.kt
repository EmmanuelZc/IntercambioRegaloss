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
    fun registerUser(
        user: User,
        context: Context,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
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
                        val id =
                            intercambioCreado?.id ?: -1 // Usa el campo correcto de la respuesta
                        if (id > 0) {
                            onSuccess(id)
                            Log.d("API Response", "Intercambio creado: $intercambioCreado")

                        } else {
                            onError("ID no válido en la respuesta del servidor")
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
                        messageState.value =
                            "Error al obtener datos: ${response.code()} ${response.message()}"
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

    fun fetchIntercambios(
        context: Context,
        onSuccess: (List<Intercambio>) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response = apiService.getIntercambios("Bearer $token")
                    if (response.isSuccessful) {
                        val intercambios = response.body() ?: emptyList()
                        Log.d(
                            "API Response",
                            "Intercambios recibidos: $intercambios"
                        ) // Verificar datos
                        onSuccess(intercambios)
                    } else {
                        onError("Error: ${response.code()} ${response.message()}")
                    }
                } else {
                    onError("Token no encontrado, por favor inicia sesión nuevamente.")
                }
            } catch (e: Exception) {
                onError("Error de conexión: ${e.message}")
            }
        }
    }


    fun fetchIntercambioDetails(
        context: Context,
        intercambioId: Int,
        onSuccess: (DetailsResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response = apiService.getIntercambioDetails("Bearer $token", intercambioId)
                    if (response.isSuccessful) {
                        response.body()?.let { details ->
                            onSuccess(details)
                        } ?: onError("La respuesta del servidor está vacía")
                    } else {
                        onError("Error: ${response.code()} ${response.message()}")
                    }
                } else {
                    onError("Token no encontrado, por favor inicia sesión nuevamente.")
                }
            } catch (e: Exception) {
                onError("Error de conexión: ${e.message}")
            }
        }
    }


    fun addParticipante(
        context: Context,
        intercambioId: Int,
        participante: Participante,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val token = SessionManager(context).getToken()
                if (token != null) {
                    val response =
                        apiService.addParticipante("Bearer $token", intercambioId, participante)
                    if (response.isSuccessful) {
                        onSuccess()
                    } else {
                        onError("Error: ${response.errorBody()?.string() ?: response.message()}")
                    }
                } else {
                    onError("Token no encontrado")
                }
            } catch (e: Exception) {
                onError("Error de conexión: ${e.message}")
            }
        }
    }
}