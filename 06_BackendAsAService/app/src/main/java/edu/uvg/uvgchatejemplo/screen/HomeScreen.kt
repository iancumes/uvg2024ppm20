// HomeScreen.kt
package edu.uvg.uvgchatejemplo.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.uvg.uvgchatejemplo.data.User
import edu.uvg.uvgchatejemplo.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    userViewModel: UserViewModel = viewModel(),
    onUserSelected: (User, Int) -> Unit
) {
    val users by userViewModel.users.observeAsState(emptyList())
    val currentUser by userViewModel.currentUser.observeAsState()
    var showPinDialog by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var pinInput by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        userViewModel.loadUsers()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Users", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(users.filter { it.email != currentUser?.email }) { user ->
                UserItem(user = user, onClick = {
                    selectedUser = user
                    showPinDialog = true
                })
            }
        }
    }

    if (showPinDialog && selectedUser != null) {
        AlertDialog(
            onDismissRequest = { showPinDialog = false },
            title = { Text("Enter PIN") },
            text = {
                OutlinedTextField(
                    value = pinInput,
                    onValueChange = { pinInput = it },
                    label = { Text("PIN (Shift for Caesar Cipher)") },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    val shift = pinInput.toIntOrNull()
                    if (shift != null && shift in 1..25) {
                        onUserSelected(selectedUser!!, shift)
                        showPinDialog = false
                        pinInput = ""
                    } else {
                        // Handle invalid PIN input
                        // You can show an error message to the user
                    }
                }) {
                    Text("Start Chat")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showPinDialog = false
                    pinInput = ""
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Text(user.firstName + " " + user.lastName)
    }
}
