package com.example.intercambioderegalos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    val clave = remember { mutableStateOf("") }
    val fechaLimite = remember { mutableStateOf("") }
    val fechaIntercambio = remember { mutableStateOf("") }
    val horaIntercambio = remember { mutableStateOf("") }
    val lugar = remember { mutableStateOf("") }
    val temas = remember { mutableStateListOf<String>() }
    val error = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        viewModel.fetchIntercambioDetails(
            context = context,
            intercambioId = intercambioId,
            onSuccess = { details ->
                clave.value = details.clave_unica
                fechaLimite.value = details.fecha_limite_registro
                fechaIntercambio.value = details.fecha_intercambio
                horaIntercambio.value = details.hora_intercambio
                lugar.value = details.lugar_intercambio
                temas.clear()
                temas.addAll(details.temasIntercambio.map { it.tema })
                isLoading.value = false
            },
            onError = { errorMessage ->
                error.value = errorMessage
                isLoading.value = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading.value -> {
                CircularProgressIndicator(color = Color(0xFF6200EE))
            }
            error.value != null -> {
                Text(
                    text = "Error: ${error.value}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    // Encabezado
                    item {
                        Text(
                            text = "Detalles del Intercambio",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color(0xFF6200EE),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    // Detalles
                    item { DetailCard(label = "Clave Única", value = clave.value) }
                    item { DetailCard(label = "Fecha Límite", value = fechaLimite.value) }
                    item { DetailCard(label = "Fecha del Intercambio", value = fechaIntercambio.value) }
                    item { DetailCard(label = "Hora del Intercambio", value = horaIntercambio.value) }
                    item { DetailCard(label = "Lugar", value = lugar.value) }

                    // Temas
                    item {
                        Text(
                            text = "Temas:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color(0xFF6200EE),
                            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                        )
                    }
                    items(temas) { tema ->
                        Text(
                            text = tema,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Black,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    // Botones
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    navController.navigate("participantes_screen/$intercambioId")
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                            ) {
                                Text("Participantes", color = Color.White)
                            }

                            Button(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                            ) {
                                Text("Regresar", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label:",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF6200EE),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.weight(2f)
            )
        }
    }
}


@Composable
fun ParticipantesScreen(
    navController: NavController,
    intercambioId: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Gestionar Participantes",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    // Navegar a la pantalla para agregar manualmente
                    navController.navigate("add_participant_screen/$intercambioId")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Agregar Manualmente", color = Color.White)
            }

            Button(
                onClick = {
                    // Navegar a la pantalla para importar contactos
                    navController.navigate("import_contacts_screen/$intercambioId")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Importar Contactos", color = Color.White)
            }
        }
    }
}
