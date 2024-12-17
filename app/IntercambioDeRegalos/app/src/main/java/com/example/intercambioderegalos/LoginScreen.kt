package com.example.intercambioderegalos

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.intercambioderegalos.Navigation.AppScreens
import com.example.intercambioderegalos.models.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf(false) }
    val passwordError = remember { mutableStateOf(false) }
    val context = LocalContext.current  // Obtener contexto
    val isLoginClicked = remember { mutableStateOf(false) }  // Controla cuando se hace clic en "Iniciar sesión"

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.snoppy),  // Asegúrate de que esta imagen exista
            contentDescription = "Snoopy",
            modifier = Modifier.size(200.dp, 200.dp)
        )

        Text(text = "Inicia sesión o regístrate")
        Spacer(modifier = Modifier.height(4.dp))

        // Validación del campo de correo electrónico
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = !isValidEmail(it)
            },
            label = { Text(text = "Correo electrónico") },
            isError = emailError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (emailError.value) Color.Red else Color.Black
            )
        )
        if (emailError.value) {
            Text(text = "El correo no es válido", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Validación del campo de contraseña
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = !isValidPassword(it)
            },
            label = { Text(text = "Contraseña") },
            isError = passwordError.value,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (passwordError.value) Color.Red else Color.Blue,
                unfocusedBorderColor = if (passwordError.value) Color.Red else Color.Black
            )
        )
        if (passwordError.value) {
            Text(text = "La contraseña debe tener al menos 6 caracteres", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Comprobamos si el email y la contraseña son válidos
            if (!emailError.value && !passwordError.value) {
                isLoginClicked.value = true
            }
        }) {
            Text(text = "Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Navegar a la pantalla de registro
            navController.navigate(AppScreens.RegisterScreen.route)
        }) {
            Text(text = "Registrarse")
        }
    }

    // Usar LaunchedEffect solo cuando se hace clic en "Iniciar sesión"
    if (isLoginClicked.value) {
        LaunchedEffect(Unit) {
            val success = viewModel.loginUser(email.value, password.value)
            if (success) {
                // Navegar al HomeScreen si es exitoso
                navController.navigate(AppScreens.HomeScreen.route) {
                    popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
                }
            } else {
                // Mostrar mensaje de error si las credenciales son incorrectas
                Toast.makeText(context, viewModel.messageState.value, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

