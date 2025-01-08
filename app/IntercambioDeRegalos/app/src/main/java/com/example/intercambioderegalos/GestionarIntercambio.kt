package com.example.intercambioderegalos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intercambioderegalos.models.Intercambio
import com.example.intercambioderegalos.models.MainViewModel
import com.example.intercambioderegalos.models.Participante

@Composable
fun GestionarIntercambioScreen(
    navController: NavController,
    intercambioId: Int,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val intercambioDetails = remember { mutableStateOf<Intercambio?>(null) }
    val temas = remember { mutableStateListOf<String>() }
    val participantes = remember { mutableStateListOf<Participante>() }
    var nuevoNombre by remember { mutableStateOf("") }
    var nuevoCorreo by remember { mutableStateOf("") }
    var nuevoTelefono by remember { mutableStateOf("") }
    var nuevoTemaPreferido by remember { mutableStateOf("") }

    // Fetch details on first load
    LaunchedEffect(Unit) {
        viewModel.fetchIntercambioDetails(
            context = context,
            intercambioId = intercambioId,
            onSuccess = { details ->
                intercambioDetails.value = details.intercambio
                temas.clear()
                temas.addAll(details.temas ?: emptyList())
                participantes.clear()
                participantes.addAll(details.participantes ?: emptyList()) // Participantes ya es del tipo correcto
                isLoading.value = false
            },
            onError = { error ->
                errorMessage.value = error
                isLoading.value = false
            }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            errorMessage.value != null -> {
                Text(
                    text = "Error: ${errorMessage.value}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Clave única del intercambio
                    intercambioDetails.value?.let { intercambio ->
                        Text(
                            text = "Clave Única: ${intercambio.claveUnica ?: "N/A"}",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Temas asignados
                    Text(
                        text = "Temas del Intercambio",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .padding(8.dp)
                    ) {
                        items(temas) { tema ->
                            Text(
                                text = tema,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Participantes
                    Text(
                        text = "Participantes",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF5F5F5))
                            .padding(8.dp)
                    ) {
                        items(participantes) { participante ->
                            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text("Nombre: ${participante.nombre}", style = MaterialTheme.typography.bodyLarge)
                                Text("Correo: ${participante.correo}", style = MaterialTheme.typography.bodyMedium)
                                participante.telefono?.let {
                                    Text("Teléfono: $it", style = MaterialTheme.typography.bodyMedium)
                                }
                                participante.temaPreferido?.let {
                                    Text("Tema Preferido: $it", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Agregar participante
                    OutlinedTextField(
                        value = nuevoNombre,
                        onValueChange = { nuevoNombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    OutlinedTextField(
                        value = nuevoCorreo,
                        onValueChange = { nuevoCorreo = it },
                        label = { Text("Correo") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    OutlinedTextField(
                        value = nuevoTelefono,
                        onValueChange = { nuevoTelefono = it },
                        label = { Text("Teléfono (Opcional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    OutlinedTextField(
                        value = nuevoTemaPreferido,
                        onValueChange = { nuevoTemaPreferido = it },
                        label = { Text("Tema Preferido (Opcional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )

                    Button(
                        onClick = {
                            if (nuevoNombre.isNotEmpty() && nuevoCorreo.isNotEmpty()) {
                                val nuevoParticipante = Participante(
                                    nombre = nuevoNombre,
                                    correo = nuevoCorreo,
                                    telefono = if (nuevoTelefono.isNotEmpty()) nuevoTelefono else null,
                                    temaPreferido = if (nuevoTemaPreferido.isNotEmpty()) nuevoTemaPreferido else null
                                )
                                viewModel.agregarParticipante(
                                    context = context,
                                    intercambioId = intercambioId,
                                    participante = nuevoParticipante,
                                    onSuccess = {
                                        participantes.add(nuevoParticipante)
                                        nuevoNombre = ""
                                        nuevoCorreo = ""
                                        nuevoTelefono = ""
                                        nuevoTemaPreferido = ""
                                    },
                                    onError = { error -> println("Error al agregar participante: $error") }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Agregar Participante")
                    }


                    Spacer(modifier = Modifier.weight(1f))

                    // Botón para regresar
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Text("Regresar")
                    }
                }
            }
        }
    }
}
