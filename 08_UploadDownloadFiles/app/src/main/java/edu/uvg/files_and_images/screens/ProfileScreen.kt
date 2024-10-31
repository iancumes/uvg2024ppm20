package edu.uvg.files_and_images.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.uvg.files_and_images.data.UserPreferences
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val coroutineScope = rememberCoroutineScope()

    // Estados para los campos de texto
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    // Cargar datos almacenados
    val savedName by userPreferences.nameFlow.collectAsState(initial = "")
    val savedSurname by userPreferences.surnameFlow.collectAsState(initial = "")
    val savedEmail by userPreferences.emailFlow.collectAsState(initial = "")
    val savedDob by userPreferences.dobFlow.collectAsState(initial = "")

    // Actualizar los estados cuando los datos cambien
    LaunchedEffect(savedName) { if (savedName?.isNotEmpty() == true) name = savedName as String }
    LaunchedEffect(savedSurname) { if (savedSurname?.isNotEmpty() == true) surname = savedSurname as String }
    LaunchedEffect(savedEmail) { if (savedEmail?.isNotEmpty() == true) email = savedEmail as String }
    LaunchedEffect(savedDob) { if (savedDob?.isNotEmpty() == true) dob = savedDob as String }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Información Personal", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = surname,
            onValueChange = { surname = it },
            label = { Text("Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("DD/MM/YYYY") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Guardar datos en DataStore
                coroutineScope.launch {
                    userPreferences.saveName(name)
                    userPreferences.saveSurname(surname)
                    userPreferences.saveEmail(email)
                    userPreferences.saveDob(dob)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Información")
        }
    }
}
