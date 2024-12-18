package com.example.intercambioderegalos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intercambioderegalos.Navigation.AppScreens
import com.example.intercambioderegalos.models.MainViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Encabezado
        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de Acciones Principales
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            HomeButton(
                text = "Crear Intercambio",
                icon = R.drawable.ic_create,
                onClick = { }
            )
            HomeButton(
                text = "Gestionar Intercambios",
                icon = R.drawable.ic_manage,
                onClick = {  }
            )
            HomeButton(
                text = "Unirme a Intercambio",
                icon = R.drawable.ic_join,
                onClick = {  }
            )
            HomeButton(
                text = "Ver Mis Intercambios",
                icon = R.drawable.ic_list,
                onClick = {  }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista Rápida de Intercambios Recientes (Simulado)
        Text(text = "Intercambios Recientes", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.fillMaxHeight(0.4f)) {
            items(3) { index -> // Simulando 3 intercambios
                ListItem(
                    headlineContent = { Text("Intercambio #${index + 1}") }, // Título principal
                    supportingContent = { Text("Fecha límite: 20/12/2024") }, // Contenido secundario
                    trailingContent = { Text("Activo") } // Contenido al
                )
                Divider()
            }
        }

        // Botón de Cerrar Sesión
        Button(onClick = {

        }) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun HomeButton(text: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_create), contentDescription = text)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text)
        }
    }
}
