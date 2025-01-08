package com.example.intercambioderegalos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intercambioderegalos.Navigation.AppScreens
import com.example.intercambioderegalos.models.Intercambio
import com.example.intercambioderegalos.models.MainViewModel
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val exchanges = remember { mutableStateOf<List<Intercambio>>(emptyList()) }

    // Fetch data on first load
    LaunchedEffect(Unit) {
        viewModel.fetchIntercambios(
            context = context,
            onSuccess = { fetchedExchanges ->
                exchanges.value = fetchedExchanges
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.snoppy), // Reemplázalo si no existe
                    contentDescription = "Welcome Icon",
                    tint = Color(0xFF6200EE),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Bienvenido a Intercambios",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF6200EE)
                )
            }

            // Main Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeButton("Crear Intercambio", R.drawable.ic_create) {
                    navController.navigate(AppScreens.NuevoScreen.route)
                }
                HomeButton("Gestionar Intercambios", R.drawable.ic_manage) {
                    navController.navigate(AppScreens.GestionarIntercambio.route)
                }
                HomeButton("Unirme a Intercambio", R.drawable.ic_join) { /* Acción */ }

            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Exchanges
            Text(
                text = "Intercambios Recientes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (errorMessage.value != null) {
                Text(
                    text = "Error: ${errorMessage.value}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (exchanges.value.isEmpty()) {
                Text(
                    text = "No se encontraron intercambios recientes.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    items(exchanges.value) { exchange ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    navController.navigate(AppScreens.GestionarIntercambio.route + "/${exchange.id}")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_list),
                                    contentDescription = "Intercambio Icon",
                                    tint = Color(0xFF6200EE),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = exchange.nombre ?: "Sin Nombre",
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                                    )
                                    Text(
                                        text = "Fecha: ${exchange.fechaIntercambio ?: "Sin Fecha"}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                                    )
                                }
                            }
                        }
                    }
                }
            }

                Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            Button(
                onClick = { navController.navigate(AppScreens.LoginScreen.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Text("Cerrar Sesión", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun HomeButton(text: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White)
        }
    }
}