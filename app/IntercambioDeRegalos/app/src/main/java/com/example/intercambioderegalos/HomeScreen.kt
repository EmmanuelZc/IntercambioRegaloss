package com.example.intercambioderegalos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intercambioderegalos.Navigation.AppScreens
import com.example.intercambioderegalos.models.MainViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val recentExchanges = listOf("Amigos de la oficina", "Familia Navidad", "Clase de Yoga") // Datos simulados

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Encabezado
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.snoppy),
                    contentDescription = "Bienvenida",
                    tint = Color(0xFF6200EE),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Bienvenido a Intercambios",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF6200EE)
                )
            }

            // Botones Principales
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeButton(
                    text = "Crear Intercambio",
                    icon = R.drawable.ic_create,
                    onClick = {
                        navController.navigate(AppScreens.NuevoScreen.route)
                    }
                )
                HomeButton(
                    text = "Gestionar Intercambios",
                    icon = R.drawable.ic_manage,
                    onClick = { /* Acción */ }
                )
                HomeButton(
                    text = "Unirme a Intercambio",
                    icon = R.drawable.ic_join,
                    onClick = { /* Acción */ }
                )
                HomeButton(
                    text = "Ver Mis Intercambios",
                    icon = R.drawable.ic_list,
                    onClick = { /* Acción */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Lista de Intercambios Recientes
            Text(
                text = "Intercambios Recientes",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                items(recentExchanges) { exchange ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
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
                                contentDescription = "Intercambio",
                                tint = Color(0xFF6200EE),
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = exchange,
                                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                                )
                                Text(
                                    text = "Fecha del intercambio: 20/12/2024",
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón Cerrar Sesión
            Button(
                onClick = { /* Acción de cerrar sesión */ },
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
