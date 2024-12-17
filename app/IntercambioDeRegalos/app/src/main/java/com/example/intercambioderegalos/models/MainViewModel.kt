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

    suspend fun loginUser(correo: String, password: String): Boolean {
        val loginRequest = ApiService.LoginRequest(correo, password)
        try {
            val response = apiService.loginUser(loginRequest)
            if (response.isSuccessful) {
                val loginResponse = response.body()
                if (loginResponse != null) {
                    messageState.value = loginResponse.message // Mostrar mensaje de éxito
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

    // Método fetchData ahora completo
    fun fetchData() {
        viewModelScope.launch {
            try {
                val response: Response<ResponseData> = apiService.getData()

                if (response.isSuccessful) {
                    messageState.value = response.body()?.message ?: "Sin mensaje"
                } else {
                    messageState.value = "Error en la solicitud"
                }
            } catch (e: Exception) {
                messageState.value = "Error: ${e.message}"
            }
        }
    }
}
