package com.example.intercambioderegalos.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intercambioderegalos.api.RetrofitClient
import com.example.intercambioderegalos.ResponseData
import com.example.intercambioderegalos.api.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context


class MainViewModel : ViewModel() {
    val messageState = mutableStateOf("")
    val registerStatus = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()
    private val apiService = RetrofitClient.apiService

    // Función para registrar al usuario
    fun registerUser(user: User) {
        viewModelScope.launch {
            val call = apiService.registerUser(user)
            call.enqueue(object : Callback<Void> { // Cambié Void por ResponseData
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        registerStatus.postValue(response.code())  // Código HTTP 201 para éxito
                        messageState.value = "Usuario registrado con éxito"
                    } else {
                        errorMessage.postValue("Error al registrar usuario. Código: ${response.code()}")
                        messageState.value = "Error al registrar usuario"
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    errorMessage.postValue(t.message)
                    messageState.value = "Error: ${t.message}"
                }
            })
        }
    }

    suspend fun loginUser(correo: String, password: String, context: Context): Boolean {
        val loginRequest = ApiService.LoginRequest(correo, password)
        try {
            val response = apiService.loginUser(loginRequest)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {

                    val token = loginResponse.token
                    saveToken(context, token)

                    // Guarda también el mensaje de éxito para mostrarlo al usuario
                    messageState.value = loginResponse.message
                    return true // Usuario autenticado con éxito
                }
            } else {
                messageState.value = "Error de autenticación: ${response.message()}"
            }
        } catch (e: Exception) {
            messageState.value = "Error de conexión: ${e.message}"
        }
        return false
    }


    private fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("jwt_token", token)  // Guardamos el token bajo la clave "jwt_token"
            apply()  // Aplica los cambios
        }
    }

    // Función para obtener el token desde SharedPreferences
    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("jwt_token", null)  // Devuelve null si no se encuentra el token
    }


    // Método fetchData ahora completo
    fun fetchData(context: Context) {
        viewModelScope.launch {
            try {
                // Obtener el token desde SharedPreferences
                val token = getToken(context)

                if (token != null) {
                    // Llamar a la API pasando el token en el encabezado
                    val response: Response<ResponseData> = apiService.getData("Bearer $token")

                    if (response.isSuccessful) {
                        messageState.value = response.body()?.message ?: "Sin mensaje"
                    } else {
                        messageState.value = "Error en la solicitud"
                    }
                } else {
                    messageState.value = "Token no encontrado"
                }
            } catch (e: Exception) {
                messageState.value = "Error: ${e.message}"
            }
        }
    }

}
