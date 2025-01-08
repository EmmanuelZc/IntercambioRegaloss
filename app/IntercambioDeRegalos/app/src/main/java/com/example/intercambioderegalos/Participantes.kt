package com.example.intercambioderegalos

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.intercambioderegalos.models.MainViewModel
import com.example.intercambioderegalos.models.Participante
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
@Composable
fun ParticipantesScreen(
    navController: NavController,
    intercambioId: Int,
    viewModel: MainViewModel = viewModel()
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
@Composable
fun AddParticipantScreen(
    navController: NavController,
    intercambioId: Int,
    viewModel: MainViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") } // Declarado como var
    var email by remember { mutableStateOf("") } // Declarado como var
    var phone by remember { mutableStateOf("") } // Declarado como var
    val context = LocalContext.current

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
                text = "Agregar Participante",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF6200EE),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo para el nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para el correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            // Campo para el teléfono
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botón para guardar el participante
            Button(
                onClick = {
                    if (name.isNotEmpty() && phone.isNotEmpty()) {
                        viewModel.addParticipante(
                            context = context,
                            intercambioId = intercambioId,
                            participante = Participante(
                                id = null,
                                nombre = name,
                                email = if (email.isNotEmpty()) email else null, // Asegurar que el email sea opcional
                                telefono = phone,
                                confirmado = 0,
                                id_intercambio = intercambioId
                            ),
                            onSuccess = {
                                navController.popBackStack()
                            },
                            onError = {
                                // Manejar errores (puedes mostrar un mensaje de error si es necesario)
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Guardar Participante", color = Color.White)
            }
        }
    }
}
@Composable
fun ImportContactsScreen(
    navController: NavController,
    intercambioId: Int,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    val contactState = remember { mutableStateOf<Pair<String, String>?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickContact()) { uri ->
        uri?.let {
            val contact = getContactDetails(context, it)
            contactState.value = contact
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (contactState.value != null) {
                val (name, phone) = contactState.value!!

                Text(
                    text = "Nombre: $name\nTeléfono: $phone",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = {
                        try {
                            viewModel.addParticipante(
                                context = context,
                                intercambioId = intercambioId,
                                participante = Participante(
                                    id = null,
                                    nombre = name,
                                    telefono = phone,
                                    email = null,
                                    confirmado = 0,
                                    id_intercambio = intercambioId
                                ),
                                onSuccess = {
                                    navController.popBackStack() // Regresar a la pantalla anterior
                                },
                                onError = { error ->
                                    // Mostrar error al usuario
                                    error?.let {
                                        println("Error al agregar participante: $error")
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            println("Error al procesar el contacto: ${e.message}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("Agregar Participante", color = Color.White)
                }
            } else {
                Button(
                    onClick = { launcher.launch(null) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("Seleccionar Contacto", color = Color.White)
                }
            }
        }
    }
}
fun getContactDetails(context: Context, uri: Uri): Pair<String, String>? {
    val projection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME
    )

    val cursor = context.contentResolver.query(uri, projection, null, null, null)

    cursor?.use {
        if (it.moveToFirst()) {
            val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)

            val contactId = it.getString(idIndex)
            val name = it.getString(nameIndex) ?: "Sin Nombre"

            // Ahora busca el número de teléfono en otra consulta
            val phoneCursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                arrayOf(contactId),
                null
            )

            phoneCursor?.use { pc ->
                if (pc.moveToFirst()) {
                    val phoneIndex = pc.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val phone = pc.getString(phoneIndex) ?: "Sin Teléfono"
                    return Pair(name, phone)
                }
            }
        }
    }

    return null // Si no se encuentran datos, devuelve null

}
