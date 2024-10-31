package edu.uvg.localsharedstorage

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home-screen", "Crear Post", Icons.Filled.AddCircle)
    object Blog : Screen("blog-screen", "Ver Posts", Icons.Filled.List)
    object Profile : Screen("profile-screen", "Perfil", Icons.Filled.Person)
}
