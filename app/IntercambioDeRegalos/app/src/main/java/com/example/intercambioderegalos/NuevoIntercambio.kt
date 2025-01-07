package com.example.intercambioderegalos

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material3.AlertDialog

import com.example.intercambioderegalos.models.Intercambio
import com.example.intercambioderegalos.models.MainViewModel
import com.example.intercambioderegalos.models.Participante
import com.example.intercambioderegalos.models.Temas
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
@Composable
fun NuevoScreen(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    // Estados para los campos del formulario
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }
    var selectedTimeExchange by remember { mutableStateOf("") }
    var selectedDateLimit by remember { mutableStateOf("") }
    var selectedDateExchange by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // Estado para mostrar el diálogo de éxito
    var intercambioId by remember { mutableStateOf(0) }

    val calendar = Calendar.getInstance()

    // Función para mostrar el selector de fecha
    fun showDatePicker(onDateSelected: (String) -> Unit) {
        val datePicker = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                onDateSelected(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    // Función para mostrar el selector de hora
    fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val timePicker = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                onTimeSelected(formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // Usar formato de 24 horas
        )
        timePicker.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Crear Nuevo Intercambio", style = MaterialTheme.typography.titleLarge)

        // Campos del intercambio
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título del Intercambio") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Lugar donde se realizará") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Presupuesto") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = comments,
            onValueChange = { comments = it },
            label = { Text("Comentarios (opcional)") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Button(
            onClick = { showDatePicker { date -> selectedDateLimit = date } },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Seleccionar Fecha Límite de Registro")
        }

        Button(
            onClick = { showDatePicker { date -> selectedDateExchange = date } },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Seleccionar Fecha del Intercambio")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { showTimePicker { selectedTimeExchange = it } },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Seleccionar Hora del Intercambio")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (title.isNotEmpty() && location.isNotEmpty() && selectedDateLimit.isNotEmpty() &&
                    selectedDateExchange.isNotEmpty() && selectedTimeExchange.isNotEmpty()
                ) {
                    val intercambio = Intercambio(
                        nombre = title,
                        fechaLimiteRegistro = selectedDateLimit,
                        fechaIntercambio = selectedDateExchange,
                        horaIntercambio = selectedTimeExchange,
                        lugar = location,
                        montoMaximo = budget.toDoubleOrNull() ?: 0.0,
                        comentarios = comments,
                        claveUnica = generarClaveUnica()
                    )

                    viewModel.crearIntercambioConId(
                        intercambio,
                        context = context,
                        onSuccess = { id ->
                            if (id > 0) { // Asegúrate de que el ID es válido
                                intercambioId = id
                                showDialog = true
                            } else {
                                println("Error: ID de intercambio inválido.")
                            }
                        },
                        onError = { error ->
                            println("Error al crear el intercambio: $error")
                        }
                    )
                } else {
                    println("Por favor completa todos los campos requeridos")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "Crear Intercambio")
        }


        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Éxito") },
                text = { Text(text = "El intercambio se ha creado exitosamente.") },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        if (intercambioId > 0) { // Verifica que el ID es válido
                            navController.navigate("AgregarTemasScreen/$intercambioId")
                        } else {
                            println("Error: No se puede navegar con un ID de intercambio inválido.")
                        }
                    }) {
                        Text("Agregar Temas")
                    }
                }
            )
        }

    }
}

// Generador de claves únicas
fun generarClaveUnica(): String {
    val caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..5).map { caracteres.random() }.joinToString("")
}

@Composable
fun AgregarTemasScreen(navController: NavController, intercambioId: Int, viewModel: MainViewModel = viewModel()) {
    var tema by remember { mutableStateOf("") }
    val temas = remember { mutableListOf<String>() }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Agregar Temas al Intercambio", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = tema,
            onValueChange = { tema = it },
            label = { Text("Tema") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                if (tema.isNotEmpty()) {
                    temas.add(tema)
                    tema = "" // Limpia el campo de texto
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text("Agregar Tema")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (temas.isNotEmpty()) {
                    temas.forEach { temaName ->
                        val nuevoTema = Temas(id_intercambio = intercambioId, tema = temaName)
                        viewModel.nuevoTema(
                            tema = nuevoTema,
                            context = context,
                            onSuccess = { println("Tema agregado: $temaName") },
                            onError = { error -> println("Error al agregar tema: $error") }
                        )
                    }
                    navController.popBackStack() // Vuelve a la pantalla anterior
                } else {
                    println("Por favor, agrega al menos un tema antes de guardar.")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text("Guardar Temas")
        }
    }
}

