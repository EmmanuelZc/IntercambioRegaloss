package com.example.intercambioderegalos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.intercambioderegalos.models.MainViewModel
import com.example.intercambioderegalos.models.User

@Composable
fun RegisterScreen(navController: NavController){
        RegisterBodyContent(navController = navController)
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}\$")
    return emailRegex.matches(email)
}


fun isValidPassword(password: String): Boolean {
    return password.length >= 8
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterBodyContent(viewModel: MainViewModel = viewModel(), navController: NavController){
    val nombre = remember { mutableStateOf("") }
    val alias = remember { mutableStateOf("") }
    val correo = remember { mutableStateOf("") }
    val contraseña = remember { mutableStateOf("") }
    val nombreError = remember { mutableStateOf(false) }
    val aliasError = remember { mutableStateOf(false) }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Ingresa tus datos")
        OutlinedTextField(
            value = nombre.value,
            onValueChange ={
                nombre.value = it
                nombreError.value = it.isEmpty() },
            label = { Text(text = "Nombre") },
            isError = nombreError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (nombreError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (nombreError.value) Color.Red else Color.Black
                )
            )
        if (nombreError.value) {
            Text(text = "El nombre no puede estar vacío", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = alias.value,
            onValueChange ={ alias.value = it
                aliasError.value = it.isEmpty()},
            label = { Text(text = "Alias") },
            isError = aliasError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (aliasError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (aliasError.value) Color.Red else Color.DarkGray
            )
        )

        if (aliasError.value) {
            Text(text = "El alias no puede estar vacío", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = correo.value,
            onValueChange ={ correo.value = it
                emailError.value = !isValidEmail(correo.value.trim())},
            label = { Text(text = "email") },
            isError = emailError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (emailError.value) Color.Red else Color.DarkGray
            )
        )

        if (emailError.value) {
            Text(text = "El email no es válido", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = contraseña.value,
            onValueChange ={ contraseña.value = it
                passwordError.value = !isValidPassword(it)},
            label = { Text(text = "password") },
            isError = passwordError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (passwordError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (passwordError.value) Color.Red else Color.DarkGray
            )
        )
        if (passwordError.value) {
            Text(text = "La contraseña debe tener al menos 8 caracteres", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            nombreError.value = nombre.value.isEmpty()
            aliasError.value = alias.value.isEmpty()
            emailError.value = !isValidEmail(correo.value)
            passwordError.value = !isValidPassword(contraseña.value)

            if (!nombreError.value && !aliasError.value && !emailError.value && !passwordError.value) {
                val user = User(
                    nombre = nombre.value,
                    alias = alias.value,
                    correo = correo.value,
                    contraseña = contraseña.value
                )
                viewModel.registerUser(user)
            }
        }) {
            Text(text = "Registrate")
        }
        Text(text = viewModel.messageState.value)
        if(viewModel.messageState.value == "Usuario registrado con éxito"){
            navController.navigate("Login_screen")
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewRegisterViewer(){

}