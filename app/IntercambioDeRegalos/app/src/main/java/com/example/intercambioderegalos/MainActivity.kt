package com.example.intercambioderegalos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.intercambioderegalos.Navigation.AppNavigation
import com.example.intercambioderegalos.models.MainViewModel
import com.example.intercambioderegalos.ui.theme.IntercambioDeRegalosTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IntercambioDeRegalosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White

                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun MainScreen(viewModel: MainViewModel = viewModel()) {
        // Observamos el estado de la respuesta
        val message = viewModel.messageState.value
        val context = LocalContext.current
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Mensaje de la API: $message")
            Button(onClick = { viewModel.fetchData(context) }) {
                Text("Obtener Datos")
            }
        }
    }
}

